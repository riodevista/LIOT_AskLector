package ru.mipt.asklector.domain;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class Asker implements AskLectorObject{

    public Asker(int id, String username, String firstName, String midName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

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

    private int id;
    private String username;
    private String firstName;
    private String midName;
    private String lastName;

}
