package com.example.hasu.playerbuddy.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hasu.playerbuddy.core.GameSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DBAccessor {
    private static final String LogTag = DBAccessor.class.getSimpleName();

    private static final String[] GameSessionColumns = {DBHelper.Col_Id, DBHelper.Col_TextSummary,
            DBHelper.Col_PointSize, DBHelper.Col_CreationDT, DBHelper.Col_Status};

    private SQLiteDatabase theDB;
    private DBHelper mDBHelper;

    private static final SimpleDateFormat DTF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public DBAccessor(Context context) {
        Log.d(LogTag, "Creating DBHelper.");
        mDBHelper = new DBHelper(context);
    }

    public void open() {
        Log.d(LogTag, "Opening DB connection.");
        theDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        Log.d(LogTag, "Closing DB connection.");
        theDB.close();
    }

    public SQLiteDatabase getDB() { return theDB; }

    public List<GameSession> getAllSessions() {
        List<GameSession> l = new ArrayList<>();
        Cursor c = theDB.query(DBHelper.Table_GameSessions, GameSessionColumns, null, null, null, null, null);
        c.moveToFirst();

        while(!c.isAfterLast()) {
            GameSession s = new GameSession();
            try {
                s.loadFromCursor(c);
                l.add(s);
            }
            catch(Exception e) {
                Log.e(LogTag, "Unable to load Session.", e);
            }
            c.moveToNext();
        }
        c.close();
        return l;
    }
}

