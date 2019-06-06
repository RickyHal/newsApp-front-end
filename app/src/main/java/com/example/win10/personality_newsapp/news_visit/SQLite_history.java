package com.example.win10.personality_newsapp.news_visit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite_history extends SQLiteOpenHelper {
    public static final String TB_name="SearchHistory";
    public SQLite_history(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+
                TB_name+" ( history_id integer primary key autoincrement,"+
                "content varchar"+
                ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TB_name);
        onCreate(db);
    }
}
