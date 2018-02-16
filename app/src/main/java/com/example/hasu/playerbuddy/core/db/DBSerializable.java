package com.example.hasu.playerbuddy.core.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hasu.playerbuddy.core.GameSession;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

import static android.database.Cursor.FIELD_TYPE_BLOB;
import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_NULL;
import static android.database.Cursor.FIELD_TYPE_STRING;

public abstract class DBSerializable {
    protected abstract String[] getAllColumns();
    protected abstract String getTableName();
    protected abstract void setColumnValue(String column, int value);
    protected abstract void setColumnValue(String column, double value);
    protected abstract void setColumnValue(String column, String value);

    private static final String Col_Id = "_id";
    protected static final SimpleDateFormat DTF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    private long mID;
    private boolean mAlreadyInDB = false;

    // List of column names which must be updated when saving
    //private List<String> mChangedColumns; // rather use a kind of Set?
    public long getDBId() { return mID; }
    protected void setDBId(long id) { mID = id; }

    protected class ColumnChange {
        String mColName;
        String mNewValue;
        public ColumnChange(String colName, String value) { mColName = colName; mNewValue = value; }
    };

    private ConcurrentLinkedQueue<ColumnChange> mPendingChanges;

    protected DBSerializable() {
        //mChangedColumns = new ArrayList<>();
        mPendingChanges = new ConcurrentLinkedQueue<>();
    }

    // Subclasses should implemented their own values and finally call the base class method
    protected String getColumnValueForDB(String columnName) {
        switch(columnName) {
            case Col_Id: return Long.toString(getDBId());
        }
        return null;
    }

    public boolean addToDB(SQLiteDatabase db) throws Exception {
        if(mAlreadyInDB) {
            return false;
        }
        ContentValues cv = new ContentValues();
        for(String col : getAllColumns()) {
            String val = getColumnValueForDB(col);
            if(val == null) {
                throw new Exception("No value received for column " + col);
            }
            cv.put(col, getColumnValueForDB(col));
        }

        mID = db.insert(getTableName(), null, cv);
        mAlreadyInDB = true;
        return true;
    }

    public void loadFromDB(SQLiteDatabase db, long id) throws Exception {
        Cursor c = db.query(getTableName(), getAllColumns(),
                Col_Id + "=" + id, null, null, null, null);
        c.moveToFirst();
        if(c.isAfterLast()) {
            c.close();
            throw new Exception("Entry with given ID not found.");
        }
        try {
            loadFromCursor(c);
        }
        finally {
            c.close();
        }
    }

    public void loadFromCursor(Cursor cursor) throws Exception {
        for (String col : getAllColumns()) {
            int colIndex = cursor.getColumnIndexOrThrow(col);
            switch (cursor.getType(colIndex)) {
                case FIELD_TYPE_INTEGER:
                    setColumnValue(col, cursor.getInt(colIndex));
                    break;
                case FIELD_TYPE_FLOAT:
                    setColumnValue(col, cursor.getDouble(colIndex));
                    break;
                case FIELD_TYPE_STRING:
                    setColumnValue(col, cursor.getString(colIndex));
                    break;
                case FIELD_TYPE_NULL:
                    break;
                case FIELD_TYPE_BLOB:
                default:
                    throw new Exception("datatype not supported.");
            }
        }
    }

    protected void markChange(ColumnChange change) {
        mPendingChanges.add(change);
    }

    protected void updateDB(List<ColumnChange> changesToProcess) {
        if(changesToProcess.isEmpty()) {
            return;
        }
        StringBuilder querySB = new StringBuilder();
        // TODO: Generate query string based on mID
        querySB.append("UPDATE ");
        querySB.append(getTableName());
        querySB.append(" WHERE _id=");
        querySB.append(mID);
        querySB.append(" SET ");
        for(ColumnChange change : changesToProcess) {
            querySB.append(change.mColName);
            querySB.append("=");
            querySB.append(change.mNewValue);
        }
        //theDB.query(querySB.toString());
    }

    protected void startUpdateDB() {
        // TODO: Eat entries of mPendingChanges and store them in a local list, emptying mPendingChanges.
        // Then call updateDB, passing the local list of changes to be processed in that update.
        // In principle we could also 'tidy' the local list first, i.e. merge changes if they refer to the same column.

        // TODO2: Start thread which does all this and return immediately. Set a member flag indicating that the DB is currently being
        // updated, this flag must be checked when entering this method (or other methods accessing the DB), they must wait until the
        // operation has finished.
        // List<ColumnChange> changes = mPendingChanges.eat();
        // makeUnique(changes);
        // updateDB(changes);
    }


}
