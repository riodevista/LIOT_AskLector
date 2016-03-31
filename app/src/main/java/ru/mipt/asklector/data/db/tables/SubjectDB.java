package ru.mipt.asklector.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.mipt.asklector.data.db.AskLectorDBHelper;
import ru.mipt.asklector.domain.Subject;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class SubjectDB {
    public static final String DB_TABLE = "subjects";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GUID = "subject_guid";
    public static final String COLUMN_TITLE = "subject_title";
    public static final String COLUMN_LECTURE = "lecture_id";


    public static final String CREATE_TABLE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_GUID + " text, " +
                    COLUMN_TITLE + " text, " +
                    COLUMN_LECTURE + " integer, " +
                    "FOREIGN KEY(" + COLUMN_LECTURE + ")REFERENCES " +
                    LectureDB.DB_TABLE + "(" + LectureDB.COLUMN_ID + ")" +
                    ");";

    private SQLiteDatabase db;


    public SubjectDB(){
        db = AskLectorDBHelper.getInstance().getWritableDatabase();
    }

    public Cursor getAllData() {
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public long insert(long lectureRowId, Subject subject) {

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GUID, subject.getGuid().string());
        cv.put(COLUMN_TITLE, subject.getTitle());
        cv.put(COLUMN_LECTURE, lectureRowId);

        return db.insert(DB_TABLE, null, cv);
    }

    public void clear(){
        db.execSQL("delete from "+ DB_TABLE);
    }

}
