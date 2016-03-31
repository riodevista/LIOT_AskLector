package ru.mipt.asklector.domain;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class Subject {
    private GUID guid;
    private String title;
    private Department department;

    public Subject(GUID guid, String title, Department department) {
        this.guid = guid;
        this.title = title;
        this.department = department;
    }

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
