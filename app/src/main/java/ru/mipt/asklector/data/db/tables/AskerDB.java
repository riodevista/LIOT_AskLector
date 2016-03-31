package ru.mipt.asklector.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.mipt.asklector.data.db.AskLectorDBHelper;
import ru.mipt.asklector.domain.AskLectorObject;
import ru.mipt.asklector.domain.Asker;

/**
 * Created by Dmitry Bochkov on 24.10.2015.
 */
public class AskerDB implements AskLectorDB{
    public static final String DB_TABLE = "askers";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ASKER_ID = "asker_backend_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_MID_NAME = "mid_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_QUESTION = "question_id";


    public static final String CREATE_TABLE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_ASKER_ID + " integer UNIQUE, " +
                    COLUMN_USERNAME + " text, " +
                    COLUMN_FIRST_NAME + " text, " +
                    COLUMN_MID_NAME + " text, " +
                    COLUMN_LAST_NAME + " text" +
                    ");";

    private SQLiteDatabase db;

    public AskerDB(){
        db = AskLectorDBHelper.getInstance().getWritableDatabase();
    }

    @Override
    public Cursor getAll() {
        return db.query(DB_TABLE, null, null, null, null, null, null);
    }

    @Override
    public Cursor get(long id) {
        return db.rawQuery(
                "SELECT * FROM " + AskerDB.DB_TABLE +
                        " WHERE " + AskerDB.COLUMN_ASKER_ID + " = " + String.valueOf(id),
                null
        );
    }

    @Override
    public long insert(AskLectorObject askLectorObject) {
        try{
            Asker asker = (Asker) askLectorObject;

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ASKER_ID, asker.getId());
            cv.put(COLUMN_USERNAME, asker.getUsername());
            cv.put(COLUMN_FIRST_NAME, asker.getFirstName());
            cv.put(COLUMN_MID_NAME, asker.getMidName());
            cv.put(COLUMN_LAST_NAME, asker.getLastName());

            return db.insert(DB_TABLE, null, cv);
        } catch (ClassCastException e){
            e.printStackTrace();
            return -1;
        }

    }

    public void clear(){
        db.execSQL("delete from "+ DB_TABLE);
    }

}
