package com.example.hasu.playerbuddy.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBAccessor {
    private static final String LogTag = DBAccessor.class.getSimpleName();

    private static final String[] GameSessionColumns = {DBHelper.Col_Id, DBHelper.Col_TextSummary,
            DBHelper.Col_PointSize, DBHelper.Col_CreationDT, DBHelper.Col_Status};

    private SQLiteDatabase theDB;
    private DBHelper mDBHelper;

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

    /** factory method creating object and storing it in the DB */
//    public SpendingItem makeSpendingItem(Amount amount, LocalDate date, String description) {
//        ContentValues cv = new ContentValues();
//        cv.put(DBHelper.COL_AMOUNT, amount.toString());
//        cv.put(DBHelper.COL_DATE, DTF.format(date));
//        cv.put(DBHelper.COL_DESCRIPTION, description);
//
//        long insertID = theDB.insert(DBHelper.TABLE_SPENDINGITEMS, null, cv);
//        Cursor cursor = theDB.query(DBHelper.TABLE_SPENDINGITEMS, SpendingItemColumns,
//                DBHelper.COL_ID + "=" + insertID, null,
//                null, null, null);
//
//        cursor.moveToFirst();
//        SpendingItem s = getSpendingItemFromCursor(cursor);
//        cursor.close();
//        return s;
//    }
//
//    public List<SpendingItem> getAllSpendingItems() {
//        List<SpendingItem> l = new ArrayList<>();
//        Cursor c = theDB.query(DBHelper.TABLE_SPENDINGITEMS, SpendingItemColumns,
//                null, null, null, null, null);
//        c.moveToFirst();
//        SpendingItem itm;
//
//        while(!c.isAfterLast()) {
//            itm = getSpendingItemFromCursor(c);
//            l.add(itm);
//            c.moveToNext();
//        }
//        c.close();
//        return l;
//    }
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

