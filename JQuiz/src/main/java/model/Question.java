package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Question implements Serializable {
    private final String question;
    private final String[] answers;
    private final int correctAnswer;

    public Question(String question, String[] variants, int correctAnswerIndex) {
        this.question = question;
        this.answers = variants;
        this.correctAnswer = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return correctAnswer == question1.correctAnswer && Objects.equals(question, question1.question) && Arrays.equals(answers, question1.answers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(question, correctAnswer);
        result = 31 * result + Arrays.hashCode(answers);
        return result;
    }


 /*   public boolean equals(Question q) {
        if (this == q) return true;
        if (q == null) return false;
        return correctAnswer == q.correctAnswer && q.getQuestion().equals(q.question) && Arrays.equals(answers, q.answers);
    }*/

}
