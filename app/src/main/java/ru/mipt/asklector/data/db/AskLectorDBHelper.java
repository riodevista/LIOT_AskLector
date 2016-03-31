package ru.mipt.asklector.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.db.tables.AskerDB;
import ru.mipt.asklector.data.db.tables.CommentsDB;
import ru.mipt.asklector.data.db.tables.DepartmentDB;
import ru.mipt.asklector.data.db.tables.GroupDB;
import ru.mipt.asklector.data.db.tables.LectureDB;
import ru.mipt.asklector.data.db.tables.LecturerDB;
import ru.mipt.asklector.data.db.tables.QuestionsDB;
import ru.mipt.asklector.data.db.tables.SubjectDB;
import ru.mipt.asklector.data.db.tables.UserDB;

/**
 * Created by Dmitry Bochkov on 17.10.2015.
 */

public class AskLectorDBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "AskLectorDB";
    private static final int DB_VERSION = 15;


    private static final AskLectorDBHelper instance = new AskLectorDBHelper(AskLector.provideContext());

    private AskLectorDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //TODO: ленивую ли авторизацию юзать?
    public static AskLectorDBHelper getInstance(){
        /*if(instance == null){
            instance = new AskLectorDBHelper(context.getApplicationContext());
        }*/
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QuestionsDB.CREATE_TABLE);
        db.execSQL(UserDB.CREATE_TABLE);
        db.execSQL(DepartmentDB.CREATE_TABLE);
        db.execSQL(GroupDB.CREATE_TABLE);
        db.execSQL(LectureDB.CREATE_TABLE);
        db.execSQL(LecturerDB.CREATE_TABLE);
        db.execSQL(SubjectDB.CREATE_TABLE);
        db.execSQL(AskerDB.CREATE_TABLE);
        db.execSQL(CommentsDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AskerDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UserDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DepartmentDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GroupDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LectureDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LecturerDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SubjectDB.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CommentsDB.DB_TABLE);

        onCreate(db);
    }
}
