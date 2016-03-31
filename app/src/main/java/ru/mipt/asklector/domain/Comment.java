package ru.mipt.asklector.domain;

import java.util.Calendar;

/**
 * Created by Dmitry Bochkov on 09.12.2015.
 */
public class Comment implements AskLectorObject{
    public static final String OBJECT = "comment_object";

    private int id;
    private String text;
    private boolean voted;
    private int rating;
    private Calendar date;
    private Asker asker;
    private int questionId;

    public Comment(Asker asker, Calendar date, int id, int rating, String text, boolean voted) {
        this.asker = asker;
        this.date = date;
        this.id = id;
        this.rating = rating;
        this.text = text;
        this.voted = voted;
    }

    public Comment(Asker asker, Calendar date, int id, int rating, String text, boolean voted, int questionId) {
        this.asker = asker;
        this.date = date;
        this.id = id;
        this.rating = rating;
        this.text = text;
        this.voted = voted;
        this.questionId = questionId;
    }

    public Asker getAsker() {
        return asker;
    }

    public void setAsker(Asker asker) {
        this.asker = asker;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
