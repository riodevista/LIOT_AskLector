package ru.mipt.asklector.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dmitry Bochkov on 14.10.2015.
 */
public class User implements AskLectorObject, Parcelable{

    public static final String OBJECT = "User";

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String username;
    private String firstName;
    private String midName;
    private String lastName;
    private Token token = null;

    public User(String username, String firstName, String midName, String lastName){
        this(username, firstName, midName, lastName, null);
    }

    public User(String username, String firstName, String midName, String lastName, String token){
        this.username = username;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.token = new Token(token);

    }

    protected User(Parcel in) {
        username = in.readString();
        firstName = in.readString();
        midName = in.readString();
        lastName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername(){
        return username;
    }

    public void setToken(Token token){
        this.token = token;
    }

    public Token getToken(){
        return token;
    }

    public boolean hasToken(){
        if(token == null)
            return false;
        else
            return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(midName);
        dest.writeString(lastName);
    }
}
