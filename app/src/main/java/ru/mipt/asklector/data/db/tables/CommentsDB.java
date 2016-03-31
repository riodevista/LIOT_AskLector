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
import ru.mipt.asklector.domain.Comment;

/**
 * Created by Dmitry Bochkov on 09.12.2015.
 */
public class CommentsDB implements AskLectorDB{

    public static final Uri URI = Uri.parse("content://comments_list");

    public static final String DB_TABLE = "comments";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COMMENT_ID = "comment_backend_id";
    public static final String COLUMN_TEXT = "comment_text";
    public static final String COLUMN_VOTED = "comment_voted";
    public static final String COLUMN_RATING = "comment_rating";
    public static final String COLUMN_DATE = "comment_date";
    public static final String COLUMN_ASKER_ID = "asker_id";
    public static final String COLUMN_QUESTION_ID = "question_id";


    public static final String CREATE_TABLE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_COMMENT_ID + " integer UNIQUE, " +
                    COLUMN_TEXT + " text, " +
                    COLUMN_VOTED + " text, " +
                    COLUMN_RATING + " integer, " +
                    COLUMN_DATE + " text, " +
                    COLUMN_ASKER_ID + " integer, " +
                    COLUMN_QUESTION_ID + " integer" +
                    ");";

    private SQLiteDatabase db;

    public CommentsDB(){
        db = AskLectorDBHelper.getInstance().getWritableDatabase();

    }

    @Override
    public Cursor getAll() {
        db.beginTransactionNonExclusive();
        try {
            Cursor cursor = db.rawQuery( // TODO: 10.12.2015 LEFT OUTER JOIN
                    "SELECT * FROM " + CommentsDB.DB_TABLE +
                            " INNER JOIN " + AskerDB.DB_TABLE + " ON " + CommentsDB.DB_TABLE + "." + CommentsDB.COLUMN_ASKER_ID +
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

    public Cursor getAll(long questionId) {
        db.beginTransactionNonExclusive();
        try {
            Cursor cursor = db.rawQuery( // TODO: 10.12.2015 LEFT OUTER JOIN
                    "SELECT * FROM " + CommentsDB.DB_TABLE +
                            " INNER JOIN " + AskerDB.DB_TABLE + " ON " + CommentsDB.DB_TABLE + "." + CommentsDB.COLUMN_ASKER_ID +
                            " = " + AskerDB.DB_TABLE + "." + AskerDB.COLUMN_ASKER_ID +
                            " WHERE " + CommentsDB.COLUMN_QUESTION_ID + " = " + String.valueOf(questionId),
                    null
            );
            cursor.setNotificationUri(AskLector.provideContext().getContentResolver(), URI);
            db.setTransactionSuccessful();
            return cursor;
        } finally {
            db.endTransaction();
        }
    }

    public Cursor get(long id){
        return db.rawQuery(
                "SELECT * FROM " + CommentsDB.DB_TABLE +
                        " INNER JOIN " + AskerDB.DB_TABLE + " ON " + CommentsDB.DB_TABLE + "." + CommentsDB.COLUMN_ASKER_ID +
                        " = " + AskerDB.DB_TABLE + "." + AskerDB.COLUMN_ASKER_ID +
                        " WHERE " + CommentsDB.COLUMN_COMMENT_ID + " = " + String.valueOf(id),
                null
        );

    }

    @Override
    public long insert(AskLectorObject askLectorObject) {
        try{
            Comment comment = (Comment) askLectorObject;
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TEXT, comment.getText());
            cv.put(COLUMN_VOTED, String.valueOf(comment.isVoted()));
            cv.put(COLUMN_RATING, comment.getRating());
            cv.put(COLUMN_COMMENT_ID, comment.getId());
            cv.put(COLUMN_ASKER_ID, comment.getAsker().getId());
            cv.put(COLUMN_QUESTION_ID, comment.getQuestionId());

            Calendar c = comment.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cv.put(COLUMN_DATE, sdf.format(c.getTime()));

            new AskerDB().insert(comment.getAsker());

            return db.insert(DB_TABLE, null, cv);
        } catch (ClassCastException e){
            e.printStackTrace();
            return -1;
        }
    }

    public void insert(List<Comment> comments) {
        db.beginTransaction();
        try {
            clear();

            for (Comment comment : comments) {
                insert(comment);
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

    public void updateVotedAndRating(long commentId, boolean voted, int rating) {
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_VOTED, String.valueOf(voted));
            cv.put(COLUMN_RATING, rating);

            db.update(DB_TABLE, cv, COLUMN_COMMENT_ID + " = " + commentId, null);

            db.setTransactionSuccessful();
            AskLector.provideContext().getContentResolver().notifyChange(URI, null);
        } finally {
            db.endTransaction();
        }
    }

}
