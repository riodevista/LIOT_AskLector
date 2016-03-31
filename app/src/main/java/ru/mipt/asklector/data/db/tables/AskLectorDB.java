package ru.mipt.asklector.data.db.tables;

import android.database.Cursor;

import ru.mipt.asklector.domain.AskLectorObject;

/**
 * Created by Dmitry Bochkov on 18.10.2015.
 */
public interface AskLectorDB {

    Cursor getAll();
    Cursor get(long id);
    long insert(AskLectorObject askLectorObject);
    void clear();
}
