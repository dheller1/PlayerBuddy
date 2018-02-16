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

        GameSession s;
        while(!c.isAfterLast()) {
            s = getGameSessionFromCursor(c);
            l.add(s);
            c.moveToNext();
        }
        c.close();
        return l;
    }

    private GameSession getGameSessionFromCursor(Cursor c) {
        int colId = c.getColumnIndex(DBHelper.Col_Id);
        int colSummary = c.getColumnIndex(DBHelper.Col_TextSummary);
        int colPoints = c.getColumnIndex(DBHelper.Col_PointSize);
        int colStatus = c.getColumnIndex(DBHelper.Col_Status);
        int colCreationDT = c.getColumnIndex(DBHelper.Col_CreationDT);

        long id = c.getLong(colId);
        String summary = c.getString(colSummary);
        int points = c.getInt(colPoints);
        int statusId = c.getInt(colStatus);
        Date creationDT = null;
        try {
            creationDT = DTF.parse(c.getString(colCreationDT));
        }
        catch(ParseException e) {
            Log.e(LogTag, "Error parsing date", e);
        }

        GameSession session = new GameSession(summary, points);
        session.setStatus(session.statusForId(statusId));
        session.setDBId(id);

        if(creationDT != null) {
            session.setCreationDT(creationDT);
        }
        return session;
    }
//
//    private SpendingItem getSpendingItemFromCursor(Cursor c) {
//        int cId = c.getColumnIndex(DBHelper.COL_ID);
//        int cAmount = c.getColumnIndex(DBHelper.COL_AMOUNT);
//        int cDate = c.getColumnIndex(DBHelper.COL_DATE);
//        int cDesc = c.getColumnIndex(DBHelper.COL_DESCRIPTION);
//
//        long id = c.getLong(cId);
//        Amount amount = new Amount(c.getString(cAmount));
//        LocalDate date = LocalDate.parse(c.getString(cDate), DTF);
//        String desc = c.getString(cDesc);
//
//        return new SpendingItem(amount, date, desc);
//    }

}

