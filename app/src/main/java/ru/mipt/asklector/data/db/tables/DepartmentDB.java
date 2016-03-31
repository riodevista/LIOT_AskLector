package ru.mipt.asklector.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.mipt.asklector.data.db.AskLectorDBHelper;
import ru.mipt.asklector.domain.Department;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class DepartmentDB {
    public static final String DB_TABLE = "departments";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GUID = "department_guid";
    public static final String COLUMN_NAME = "department_name";
    public static final String COLUMN_DESCRIPTION = "department_description";
    public static final String COLUMN_SUBJECT = "subject_id";


    public static final String CREATE_TABLE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_GUID + " text, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_DESCRIPTION + " text, " +
                    COLUMN_SUBJECT + " integer, " +
                    "FOREIGN KEY(" + COLUMN_SUBJECT + ")REFERENCES " +
                    SubjectDB.DB_TABLE + "(" + SubjectDB.COLUMN_ID + ")" +
                    ");";

    private SQLiteDatabase db;


    public DepartmentDB(){
        db = AskLectorDBHelper.getInstance().getWritableDatabase();
    }

    public Cursor getAllData() {
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public void insert(long subjectRowId, Department department) {

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GUID, department.getGuid().string());
        cv.put(COLUMN_NAME, department.getName());
        cv.put(COLUMN_DESCRIPTION, department.getDescription());
        cv.put(COLUMN_SUBJECT, subjectRowId);

        db.insert(DB_TABLE, null, cv);
    }

    public void clear(){
        db.execSQL("delete from "+ DB_TABLE);
    }

}
