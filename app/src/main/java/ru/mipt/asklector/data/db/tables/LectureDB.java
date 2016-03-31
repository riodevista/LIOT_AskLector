package ru.mipt.asklector.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.mipt.asklector.data.db.AskLectorDBHelper;
import ru.mipt.asklector.domain.Lecture;
import ru.mipt.asklector.domain.enums.ModerationType;

/**
 * Created by Dmitry Bochkov on 21.10.2015.
 */
public class LectureDB{
    public static final String DB_TABLE = "lectures";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GUID = "lecture_guid";
    public static final String COLUMN_LOCATION = "lecture_location";
    public static final String COLUMN_DATE_START = "lecture_date_start";
    public static final String COLUMN_DATE_END = "lecture_date_end";
    public static final String COLUMN_MODERATION_TYPE = "moderation_type";
    public static final String COLUMN_ARE_COMMENTS_AVAILABLE = "are_comments_available";


    public static final String CREATE_TABLE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_GUID + " text, " +
                    COLUMN_LOCATION + " text, " +
                    COLUMN_DATE_START + " text, " +
                    COLUMN_DATE_END + " text, " +
                    COLUMN_MODERATION_TYPE + " integer, " +
                    COLUMN_ARE_COMMENTS_AVAILABLE + " integer " +
                    ");";

    private SQLiteDatabase db;

    public LectureDB(){
        db = AskLectorDBHelper.getInstance().getWritableDatabase();
    }

    public Cursor getAllData() {
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public long insert(Lecture lecture) {

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GUID, lecture.getGuid().string());
        cv.put(COLUMN_LOCATION, lecture.getLocation());

        Calendar start = lecture.getDateStart();
        Calendar end = lecture.getDateEnd();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv.put(COLUMN_DATE_START, sdf.format(start.getTime()));
        cv.put(COLUMN_DATE_END, sdf.format(end.getTime()));

        // TODO: 21.12.2015 Нормально хранить
        cv.put(COLUMN_MODERATION_TYPE, lecture.getSettings().getModerationType() == ModerationType.POST ? 1 : 0);
        cv.put(COLUMN_ARE_COMMENTS_AVAILABLE, lecture.getSettings().areCommentsAvailable() ? 1 : 0);

        return db.insert(DB_TABLE, null, cv);
    }

    public void clear(){
        db.execSQL("delete from "+ DB_TABLE);
    }
}

