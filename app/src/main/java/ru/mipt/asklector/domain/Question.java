package ru.mipt.asklector.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class Question implements AskLectorObject, Parcelable{

    public static final String OBJECT = "question_object";

    private int id;
    private String text;
    private Asker asker;
    private boolean voted;
    private int rating;
    private Calendar date;
    private int commentsCount;


    protected Question(Parcel in) {
        id = in.readInt();
        text = in.readString();
        voted = in.readByte() != 0;
        rating = in.readInt();
        commentsCount = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Asker getAsker() {
        return asker;
    }

    public void setAsker(Asker asker) {
        this.asker = asker;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Question(int id, String text, Asker asker, boolean voted, int rating, Calendar date, int commentsCount) {
        this.id = id;
        this.text = text;
        this.asker = asker;
        this.voted = voted;
        this.rating = rating;
        this.date = date;
        this.commentsCount = commentsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeByte((byte) (voted ? 1 : 0));
        dest.writeInt(rating);
        dest.writeInt(commentsCount);
    }
}
