package ru.mipt.asklector.ui.lectureConnection;

/**
 * Created by Dmitry Bochkov on 13.10.2015.
 */
public interface LectureConnection {

    boolean checkLectureId();

    void connectToLectureWithId();

    void findLecture();

}
