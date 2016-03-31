package ru.mipt.asklector.domain;

import java.util.Calendar;
import java.util.Collection;

import ru.mipt.asklector.domain.enums.ModerationType;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class Lecture implements AskLectorObject{

    private final static String Object = "Lecture";


    /**
     * Constructor
     */

    public Lecture(GUID guid, Subject subject, Lecturer lecturer, String location,
                   Calendar dateStart, Calendar dateEnd,
                   Collection<Group> groups,
                   LectureSettings settings) {
        this.guid = guid;
        this.subject = subject;
        this.lecturer = lecturer;
        this.location = location;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.groups = groups;
        this.settings = settings;
    }

    /**
     * Getters and Setters
     */

    public static String getObject() {
        return Object;
    }

    public GUID getGuid() {
        return guid;
    }

    public void setGuid(GUID guid) {
        this.guid = guid;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public void setGroups(Collection<Group> groups) {
        this.groups = groups;
    }

    public LectureSettings getSettings() {
        return settings;
    }

    public void setSettings(LectureSettings settings) {
        this.settings = settings;
    }


    /**
     * Fields
     */

    private GUID guid;
    private Subject subject;
    private Lecturer lecturer;
    private String location;
    private Calendar dateStart;
    private Calendar dateEnd;
    private Collection<Group> groups;
    private LectureSettings settings;

}
