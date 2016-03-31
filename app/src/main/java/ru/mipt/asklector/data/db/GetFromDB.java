package ru.mipt.asklector.data.db;

import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ru.mipt.asklector.data.db.tables.AskerDB;
import ru.mipt.asklector.data.db.tables.CommentsDB;
import ru.mipt.asklector.data.db.tables.DepartmentDB;
import ru.mipt.asklector.data.db.tables.GroupDB;
import ru.mipt.asklector.data.db.tables.LectureDB;
import ru.mipt.asklector.data.db.tables.LecturerDB;
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.data.db.tables.SubjectDB;
import ru.mipt.asklector.data.db.tables.UserDB;
import ru.mipt.asklector.domain.Asker;
import ru.mipt.asklector.domain.Comment;
import ru.mipt.asklector.domain.Department;
import ru.mipt.asklector.domain.GUID;
import ru.mipt.asklector.domain.Group;
import ru.mipt.asklector.domain.Lecture;
import ru.mipt.asklector.domain.LectureSettings;
import ru.mipt.asklector.domain.Lecturer;
import ru.mipt.asklector.domain.Question;
import ru.mipt.asklector.domain.Subject;
import ru.mipt.asklector.domain.User;
import ru.mipt.asklector.domain.enums.ModerationType;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class GetFromDB {
    
    public static User user(){
        Cursor cursor = new UserDB().getAllData();

        if(cursor == null || !cursor.moveToFirst())
            return null;

        return new User(
                cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_FIRSTNAME)),
                cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_MIDNAME)),
                cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_LASTNAME)),
                cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_TOKEN))
        );
    }

    public static Lecture lecture(){
        Cursor cursor = new LectureDB().getAllData();

        Cursor cursorSubject = new SubjectDB().getAllData();
        Cursor cursorDepartment = new DepartmentDB().getAllData();
        Cursor cursorLecturer = new LecturerDB().getAllData();

        ArrayList<Group> groups = new ArrayList<>();
        Cursor cursorGroup = new GroupDB().getAllData();

        if(
                !cursor.moveToFirst() ||
                !cursorDepartment.moveToFirst() ||
                !cursorGroup.moveToFirst() ||
                !cursorLecturer.moveToFirst() ||
                !cursorSubject.moveToFirst())
            return null;

        while (!cursorGroup.isAfterLast()) {
            groups.add(new Group(
                    new GUID(cursorGroup.getString(cursorGroup.getColumnIndex(GroupDB.COLUMN_GUID))),
                    cursorGroup.getString(cursorGroup.getColumnIndex(GroupDB.COLUMN_TITLE))
            ));
            cursorGroup.moveToNext();
        }

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            start.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex(LectureDB.COLUMN_DATE_START))));
            end.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex(LectureDB.COLUMN_DATE_END))));
        } catch (ParseException e){
            e.printStackTrace();
        }

        return new Lecture(
                new GUID(cursor.getString(cursor.getColumnIndex(LectureDB.COLUMN_GUID))),
                new Subject(
                        new GUID(cursorSubject.getString(cursorSubject.getColumnIndex(SubjectDB.COLUMN_GUID))),
                        cursorSubject.getString(cursorSubject.getColumnIndex(SubjectDB.COLUMN_TITLE)),
                        new Department(
                                new GUID(cursorDepartment.getString(cursorDepartment.getColumnIndex(DepartmentDB.COLUMN_GUID))),
                                cursorDepartment.getString(cursorDepartment.getColumnIndex(DepartmentDB.COLUMN_NAME)),
                                cursorDepartment.getString(cursorDepartment.getColumnIndex(DepartmentDB.COLUMN_DESCRIPTION))
                        )
                ),
                new Lecturer(
                        new GUID(cursorLecturer.getString(cursorLecturer.getColumnIndex(LecturerDB.COLUMN_GUID))),
                        cursorLecturer.getString(cursorLecturer.getColumnIndex(LecturerDB.COLUMN_TITLE))
                ),
                cursor.getString(cursor.getColumnIndex(LectureDB.COLUMN_LOCATION)),
                start,
                end,
                groups,
                new LectureSettings(
                        (cursor.getInt(cursor.getColumnIndex(LectureDB.COLUMN_MODERATION_TYPE)) == 1) ?
                                ModerationType.POST : ModerationType.PRE,
                        cursor.getInt(cursor.getColumnIndex(LectureDB.COLUMN_ARE_COMMENTS_AVAILABLE)) == 1
                )
        );
    }


    //TODO: Сделать, чтобы массив вопросов возвращал. Зачем? Можно один вопрос по ID.
    public static Question question(long questionId){
        Cursor cursor = new QuestionsDB().get(questionId);

        if(cursor == null || !cursor.moveToFirst())
            return null;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex(QuestionsDB.COLUMN_DATE))));
        } catch (ParseException e){
            e.printStackTrace();
        }

        return new Question(
                cursor.getInt(cursor.getColumnIndex(QuestionsDB.COLUMN_QUESTION_ID)),
                cursor.getString(cursor.getColumnIndex(QuestionsDB.COLUMN_TEXT)),
                new Asker(
                        cursor.getInt(cursor.getColumnIndex(AskerDB.COLUMN_ASKER_ID)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_MID_NAME)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_LAST_NAME))
                ),
                Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(QuestionsDB.COLUMN_VOTED))),
                cursor.getInt(cursor.getColumnIndex(QuestionsDB.COLUMN_RATING)),
                calendar,
                cursor.getInt(cursor.getColumnIndex(QuestionsDB.COLUMN_COMMENTS_COUNT))
                );
    }

    public static Comment comment(long commentId){
        Cursor cursor = new CommentsDB().get(commentId);

        if(cursor == null || !cursor.moveToFirst())
            return null;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex(CommentsDB.COLUMN_DATE))));
        } catch (ParseException e){
            e.printStackTrace();
        }

        return new Comment(
                new Asker(
                        cursor.getInt(cursor.getColumnIndex(AskerDB.COLUMN_ASKER_ID)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_MID_NAME)),
                        cursor.getString(cursor.getColumnIndex(AskerDB.COLUMN_LAST_NAME))
                ),
                calendar,
                cursor.getInt(cursor.getColumnIndex(CommentsDB.COLUMN_COMMENT_ID)),
                cursor.getInt(cursor.getColumnIndex(CommentsDB.COLUMN_RATING)),
                cursor.getString(cursor.getColumnIndex(CommentsDB.COLUMN_TEXT)),
                Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(CommentsDB.COLUMN_VOTED))),
                cursor.getInt(cursor.getColumnIndex(CommentsDB.COLUMN_QUESTION_ID))
        );
    }
}
