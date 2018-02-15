package com.example.hasu.playerbuddy.core.db;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class DBSerializable {
    protected static final String Col_Id = "_id";
    private long mID;
    private final String mTableName;

    protected static final SimpleDateFormat DTF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    // List of column names which must be updated when saving
    //private List<String> mChangedColumns; // rather use a kind of Set?
    protected long getID() { return mID; }
    protected void setID(long id) { mID = id; }

    private class ColumnChange {
        String mColName;
        String mNewValue;
        ColumnChange(String colName, String value) { mColName = colName; mNewValue = value; }
    };

    private ConcurrentLinkedQueue<ColumnChange> mPendingChanges;

    protected DBSerializable(String tableName) {
        //mChangedColumns = new ArrayList<>();
        mTableName = tableName;
        mPendingChanges = new ConcurrentLinkedQueue<>();
    }

    protected void updateDB(List<ColumnChange> changesToProcess) {
        if(changesToProcess.isEmpty()) {
            return;
        }
        StringBuilder querySB = new StringBuilder();
        // TODO: Generate query string based on mID
        querySB.append("UPDATE " + mTableName);
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

    // Subclasses should implemented their own values and finally call the base class method
    protected String getValueForDB(String columnName) throws Exception {
        switch(columnName) {
            case Col_Id:
                return Long.toString(getID());
        }
        throw new Exception("Column not found."); // TODO: Find better type
    }

}
