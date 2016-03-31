package ru.mipt.asklector.data.db;

import ru.mipt.asklector.data.db.tables.DepartmentDB;
import ru.mipt.asklector.data.db.tables.GroupDB;
import ru.mipt.asklector.data.db.tables.LectureDB;
import ru.mipt.asklector.data.db.tables.LecturerDB;
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.data.db.tables.SubjectDB;
import ru.mipt.asklector.domain.Group;
import ru.mipt.asklector.domain.Lecture;
import ru.mipt.asklector.domain.Question;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class InsertIntoDB {

    public static void lecture(Lecture lecture){
        LectureDB lectureDB = new LectureDB();
        lectureDB.clear();
        long lectureRowId = lectureDB.insert(lecture);

        GroupDB groupDB = new GroupDB();
        groupDB.clear();
        for (Group group: lecture.getGroups()) {
            groupDB.insert(lectureRowId, group);
        }

        SubjectDB subjectDB = new SubjectDB();
        subjectDB.clear();
        long subjectRowId = subjectDB.insert(lectureRowId, lecture.getSubject());

        DepartmentDB departmentDB = new DepartmentDB();
        departmentDB.clear();
        departmentDB.insert(subjectRowId, lecture.getSubject().getDepartment());

        LecturerDB lecturerDB = new LecturerDB();
        lecturerDB.clear();
        lecturerDB.insert(lectureRowId, lecture.getLecturer());

    }

    public static void question(Question question){
        new QuestionsDB().insert(question);
    }
}
