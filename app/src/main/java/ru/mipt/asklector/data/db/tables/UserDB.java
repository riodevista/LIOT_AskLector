package ru.mipt.asklector.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.mipt.asklector.data.db.AskLectorDBHelper;
import ru.mipt.asklector.domain.User;

/**
 * Created by Dmitry Bochkov on 20.10.2015.
 */
public class UserDB{
    public static final String DB_TABLE = "users";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_MIDNAME = "midname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_TOKEN = "token";

    public static final String CREATE_TABLE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_USERNAME + " text, " +
                    COLUMN_FIRSTNAME + " text, " +
                    COLUMN_MIDNAME + " text, " +
                    COLUMN_LASTNAME + " text, " +
                    COLUMN_TOKEN + " text" +
                    ");";

    private SQLiteDatabase db;


    public UserDB(){
        db = AskLectorDBHelper.getInstance().getWritableDatabase();
    }

    public Cursor getAllData() {
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public void insert(User user) {

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_FIRSTNAME, user.getFirstName());
        cv.put(COLUMN_MIDNAME, user.getMidName());
        cv.put(COLUMN_LASTNAME, user.getLastName());
        cv.put(COLUMN_TOKEN, user.getToken().toString());

        db.insert(DB_TABLE, null, cv);
    }

    public void clear(){
        db.execSQL("delete from "+ DB_TABLE);
    }
}
