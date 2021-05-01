package model;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable {
    private final String name;
    private final Date date;
    private final int score;

    public Result(String name, Date date, boolean[] answers) {
        this.name = name;
        this.date = date;

        int rightAnswers = 0;
        for (boolean answer : answers) {
            if (answer) {
                rightAnswers++;
            }
        }

        this.score = rightAnswers;
    }

    public Result(String name, Date date, int score) {
        this.name = name;
        this.date = date;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }
}
