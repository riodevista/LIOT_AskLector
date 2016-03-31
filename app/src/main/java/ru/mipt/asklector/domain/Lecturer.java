package ru.mipt.asklector.domain;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class Lecturer implements AskLectorObject {

    private GUID guid;
    private String title;

    public GUID getGuid() {
        return guid;
    }

    public void setGuid(GUID guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Lecturer(GUID guid, String title) {

        this.guid = guid;
        this.title = title;
    }
}
