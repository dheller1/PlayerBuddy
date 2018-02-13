package com.example.hasu.playerbuddy.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    private static final String LogTag = DBHelper.class.getSimpleName();

    public static final String DB_Name = "playerbuddy.db";
    public static final int DB_Version = 1;

    public static final String Table_GameSessions = "game_sessions";
    public static final String Col_Id = "_id";
    public static final String Col_TextSummary = "text_summary";
    public static final String Col_PointSize = "point_size";
    public static final String Col_CreationDT = "creation_dt";
    public static final String Col_Status = "status";

    public static final String SQL_Create_GameSessions =
            "CREATE TABLE " + Table_GameSessions + "("
                    + Col_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Col_TextSummary + " TEXT, "
                    + Col_PointSize + " INTEGER, "
                    + Col_CreationDT + " TEXT NOT NULL, "
                    + Col_Status + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
        Log.d(LogTag, "DBHelper created database " + getDatabaseName() + ".");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LogTag, "Creating GameSessions table with SQL-Query:" + SQL_Create_GameSessions);
            db.execSQL(SQL_Create_GameSessions);
        }
        catch (Exception e) {
            Log.e(LogTag, "SQL Query not successful: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
