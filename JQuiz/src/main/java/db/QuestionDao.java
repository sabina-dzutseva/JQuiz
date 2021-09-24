package db;

import model.Question;
import org.mapdb.*;
import org.mapdb.serializer.SerializerArray;

import java.io.IOException;
import java.io.Serializable;

public class QuestionDao extends BaseDao<Question> {
    private static final String DB_NAME = "/questions.db";

    public QuestionDao(String pathToDb) {
        super(pathToDb, DB_NAME, "questionsList", new QuestionSerializer());
    }

    public void addQuestion(Question newQuestion) {
        open();
        entities.add(newQuestion);
        close();
    }

    public Question findQuestion(String question) {
        open();
        Question q = null;
        for (Question question1 : entities) {
            if (question1.getQuestion().equals(question)) {
                q = question1;
                break;
            }
        }
        close();
        return q;
    }

    public void updateQuestion(String question, Question updatedQuestion) {
        int i = findQuestionIndex(findQuestion(question));
        open();
        entities.set(i, updatedQuestion);
        close();
    }

    public void deleteQuestion(String question) {
        int i = findQuestionIndex(findQuestion(question));
        open();
        entities.remove(i);
        close();
    }

    public Question[] getAll() {
        open();
        Question[] result = new Question[0];
        result = entities.toArray(result);
        close();
        return result;
    }

    public boolean contains(String question) {
        open();
        boolean contains = false;
        for (Question q : entities) {
            if (q.getQuestion().equals(question)) {
                contains = true;
                break;
            }
        }
        close();
        return contains;
    }

    private int findQuestionIndex(Question question) {
        open();
        int index = -1;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getQuestion().equals(question.getQuestion())) {
                index = i;
                break;
            }
        }
        close();
        return index;
    }

    private static class QuestionSerializer implements Serializer<Question>, Serializable {
        @Override
        public void serialize(DataOutput2 out, Question question) throws IOException {
            out.writeUTF(question.getQuestion());
            SerializerArray<String> stringSerializerArray = new SerializerArray<>(Serializer.STRING, String.class);
            stringSerializerArray.serialize(out, question.getAnswers());
            out.writeInt(question.getCorrectAnswer());
        }

        @Override
        public Question deserialize(DataInput2 in, int i) throws IOException {
            String question = in.readUTF();
            SerializerArray<String> stringSerializerArray = new SerializerArray<>(Serializer.STRING, String.class);
            String[] variants = stringSerializerArray.deserialize(in, i);
            int index = in.readInt();

            return new Question(question, variants, index);
        }
    }
}
