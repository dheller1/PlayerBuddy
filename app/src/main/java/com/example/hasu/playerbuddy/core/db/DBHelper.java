package com.example.hasu.playerbuddy.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hasu.playerbuddy.core.GameSession;


public class DBHelper extends SQLiteOpenHelper {
    private static final String LogTag = DBHelper.class.getSimpleName();

    public static final String DB_Name = "playerbuddy.db";
    public static final int DB_Version = 4;

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
        Log.d(LogTag, "DBHelper created database " + getDatabaseName() + ".");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String query = new GameSession().getSQLCreateQuery();
            Log.d(LogTag, "Creating GameSessions table with SQL-Query:" + query);
            db.execSQL(query);
        }
        catch(Exception e) {
            Log.e(LogTag, "SQL Query not successful: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + new GameSession().getTableName());
            onCreate(db);
        }
        catch(Exception e) {
            Log.e(LogTag, "SQL Upgrade of table not successful: " + e.getMessage());
        }

    }
}
