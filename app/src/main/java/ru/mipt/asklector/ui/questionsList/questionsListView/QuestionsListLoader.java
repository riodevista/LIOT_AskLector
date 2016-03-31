package ru.mipt.asklector.ui.questionsList.questionsListView;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import ru.mipt.asklector.data.db.tables.QuestionsDB;

/**
 * Created by Dmitry Bochkov on 15.10.2015.
 */
public class QuestionsListLoader extends CursorLoader {

    private final ForceLoadContentObserver observer;

    public QuestionsListLoader(Context context) {
        super(context);
        setUri(QuestionsDB.URI);
        observer = new ForceLoadContentObserver();
    }

    @Override
    public Cursor loadInBackground(){
        Log.d("QuestionsListLoader", "loadInBackground started");
        Cursor cursor = new QuestionsDB().getAll();

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
