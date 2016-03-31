package ru.mipt.asklector.data.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ru.mipt.asklector.AskLector;
import ru.mipt.asklector.data.db.AskLectorDBHelper;
import ru.mipt.asklector.domain.AskLectorObject;
import ru.mipt.asklector.domain.Question;

/**
 * Created by Dmitry Bochkov on 17.10.2015.
 */
public class QuestionsDB implements AskLectorDB{
    public static final Uri URI = Uri.parse("content://questions_list");

    public static final String DB_TABLE = "questions";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION_ID = "question_backend_id";
    public static final String COLUMN_TEXT = "question_text";
    public static final String COLUMN_VOTED = "question_voted";
    public static final String COLUMN_RATING = "question_rating";
    public static final String COLUMN_DATE = "question_date";
    public static final String COLUMN_ASKER_ID = "asker_id";
    public static final String COLUMN_COMMENTS_COUNT = "comments_count";


    public static final String CREATE_TABLE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_QUESTION_ID + " integer UNIQUE, " +
                    COLUMN_TEXT + " text, " +
                    COLUMN_VOTED + " text, " +
                    COLUMN_RATING + " integer, " +
                    COLUMN_DATE + " text, " +
                    COLUMN_ASKER_ID + " integer, " +
                    COLUMN_COMMENTS_COUNT + " integer " +
                    ");";

    private SQLiteDatabase db;

    public QuestionsDB(){
        db = AskLectorDBHelper.getInstance().getWritableDatabase();

    }

    @Override
    public Cursor getAll(){
        db.beginTransactionNonExclusive();
        try {
            Cursor cursor = db.rawQuery( // TODO: 10.12.2015 Нужен LEFT OUTER JOIN, но необходимо защититься от NullPointExc
                    "SELECT * FROM " + QuestionsDB.DB_TABLE +
                            " INNER JOIN " + AskerDB.DB_TABLE + " ON " + QuestionsDB.DB_TABLE + "." + QuestionsDB.COLUMN_ASKER_ID +
                            " = " + AskerDB.DB_TABLE + "." + AskerDB.COLUMN_ASKER_ID,
                    null
            );
            cursor.setNotificationUri(AskLector.provideContext().getContentResolver(), URI);
            db.setTransactionSuccessful();
            return cursor;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Cursor get(long id){
            return db.rawQuery(
                    "SELECT * FROM " + QuestionsDB.DB_TABLE +
                            " INNER JOIN " + AskerDB.DB_TABLE + " ON " + QuestionsDB.DB_TABLE + "." + QuestionsDB.COLUMN_ASKER_ID +
                            " WHERE " + QuestionsDB.DB_TABLE + "." + QuestionsDB.COLUMN_QUESTION_ID + " = " + String.valueOf(id),
                    null
            );

    }

    @Override
    public long insert(AskLectorObject askLectorObject) {
        try{
            Question question = (Question) askLectorObject;
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_QUESTION_ID, question.getId());
            cv.put(COLUMN_TEXT, question.getText());
            cv.put(COLUMN_VOTED, String.valueOf(question.isVoted()));
            cv.put(COLUMN_RATING, question.getRating());
            cv.put(COLUMN_ASKER_ID, question.getAsker().getId());
            cv.put(COLUMN_COMMENTS_COUNT, question.getCommentsCount());

            Calendar c = question.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cv.put(COLUMN_DATE, sdf.format(c.getTime()));

            new AskerDB().insert(question.getAsker());

            return db.insert(DB_TABLE, null, cv);
        } catch (ClassCastException e){
            e.printStackTrace();
            return -1;
        }
    }

    public void insert(List<Question> questions) {
        db.beginTransaction();
        try {
            clear();
            new AskerDB().clear();

            for (Question question : questions) {
                insert(question);
            }

            db.setTransactionSuccessful();
            AskLector.provideContext().getContentResolver().notifyChange(URI, null);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void clear(){
        db.execSQL("delete from " + DB_TABLE);
    }

    public void updateVotedAndRating(long questionId, boolean voted, int rating) {
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_VOTED, String.valueOf(voted));
            cv.put(COLUMN_RATING, rating);

            db.update(DB_TABLE, cv, COLUMN_QUESTION_ID + " = " + questionId, null);

            db.setTransactionSuccessful();
            AskLector.provideContext().getContentResolver().notifyChange(URI, null);
        } finally {
            db.endTransaction();
        }
    }

}
