package ru.mipt.asklector.ui.question.commentsListView;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import ru.mipt.asklector.data.db.tables.CommentsDB;

/**
 * Created by Dmitry Bochkov on 09.12.2015.
 */
public class CommentsListLoader extends CursorLoader{

    private final ForceLoadContentObserver observer;


    public CommentsListLoader(Context context) {
        super(context);
        setUri(CommentsDB.URI);
        observer = new ForceLoadContentObserver();
    }

    @Override
    public Cursor loadInBackground(){
        Log.d("CommentsListLoader", "loadInBackground started");
        Cursor cursor = new CommentsDB().getAll();

        if (cursor != null) {
            try {
                //Ensure the cursor window is filled.
                cursor.getCount();
                cursor.registerContentObserver(observer);
            } catch (RuntimeException e) {
                e.printStackTrace();
                cursor.close();
            }
        }
        return cursor;
    }
}