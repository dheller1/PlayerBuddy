package com.example.hasu.playerbuddy.core;

import com.example.hasu.playerbuddy.core.db.DBSerializable;

import java.util.Date;

public class GameSession extends DBSerializable {
    // DB serialization constants
    private static final String TableName = "game_sessions";
    private static final String Col_TextSummary = "text_summary";
    private static final String Col_PointSize = "point_size";
    private static final String Col_CreationDT = "creation_dt";
    private static final String Col_Status = "status";

    public Status statusForId(int id) {
        switch(id) {
            case 0:
                return Status.Created;
            case 1:
                return Status.Started;
            case 2:
                return Status.Finished;
        }
        return Status.Created;
    }

    public enum Status {
        Created(0), Started(1), Finished(2);
        private int id;
        Status(int id) { this.id = id; }
        public int getId() { return id; }
    }

    // data fields
    private Date mCreationDT, mStartGameDT, mFinishGameDT;
    private int mPointSize;
    private String mGameType;
    private Status mStatus;
    private RoundCounter mRoundCounter;

    // for serialization
    private long mDBId;
    public void setDBId(long id) { mDBId = id; }
    public long getDBId() { return mDBId; }

    // constructors
    public GameSession() {
        super(TableName);
        mCreationDT = new Date();
        mRoundCounter = new RoundCounter();
        mStatus = Status.Created;
    }
    public GameSession(String type, int points) {
        super(TableName);
        mCreationDT = new Date();
        mPointSize = points;
        mGameType = type;
        mStatus = Status.Created;

        mRoundCounter = new RoundCounter();
    }

    @Override
    protected String getValueForDB(String columnName) throws Exception {
        switch(columnName) {
            case Col_TextSummary:
                return mGameType;
            case Col_PointSize:
                return Integer.toString(mPointSize);
            case Col_CreationDT:
                return DTF.format(mCreationDT);
            case Col_Status:
                return Integer.toString(mStatus.getId());
            default:
                return super.getValueForDB(columnName);
        }
    }

    // data accessors
    public int getPoints() { return mPointSize; }
    public String getGameType() { return mGameType; }
    public Date getCreationDT() { return mCreationDT; }
    public Status getStatus() { return mStatus; }

    // FIXME: Are these really needed? Find a better solution to create an instance from DB !!
    public void setPoints(int points) { mPointSize = points; }
    public void setGameType(String type) { mGameType = type; }
    public void setCreationDT(Date d) { mCreationDT = d; }
    public void setStatus(Status s) { mStatus = s; }
}
