package com.example.hasu.playerbuddy.core;

import android.util.Log;

import com.example.hasu.playerbuddy.core.db.DBSerializable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class GameSession extends DBSerializable {
    // DB serialization constants and accessors/setters
    @Override protected String getTableName() { return "game_sessions"; }

    private static final String Col_TextSummary = "text_summary";
    private static final String Col_PointSize = "point_size";
    private static final String Col_CreationDT = "creation_dt";
    private static final String Col_Status = "status";
    private static final String Col_PlayerVP = "player_vp";
    private static final String Col_OpponentVP = "opponent_vp";
    // add column names for all fields that must be serialized here
    @Override protected String[] getAllColumns() { return new String[]{Col_TextSummary, Col_Status, Col_PointSize, Col_CreationDT,
        Col_PlayerVP, Col_OpponentVP}; }

    @Override protected String getColumnValueForDB(String columnName) {
        switch(columnName) {
            case Col_TextSummary:
                return mGameType;
            case Col_PointSize:
                return Integer.toString(mPointSize);
            case Col_CreationDT:
                return DTF.format(mCreationDT);
            case Col_Status:
                return Integer.toString(mStatus.getId());
            case Col_PlayerVP:
                return Integer.toString(mOwnVP);
            case Col_OpponentVP:
                return Integer.toString(mOpponentVP);
            default:
                return super.getColumnValueForDB(columnName);
        }
    }

    @Override protected void setColumnValue(String column, double value) {} // not used
    @Override protected void setColumnValue(String column, int value) {
        switch(column) {
            case Col_PointSize:
                mPointSize = value;
                break;
            case Col_Status:
                mStatus = statusForId(value);
                break;
            case Col_PlayerVP:
                mOwnVP = value;
                break;
            case Col_OpponentVP:
                mOpponentVP = value;
                break;
        }
    }

    @Override protected void setColumnValue(String column, String value) {
        switch(column) {
            case Col_TextSummary:
                mGameType = value;
                break;
            case Col_CreationDT:
                try {
                    mCreationDT = DTF.parse(value);
                }
                catch(ParseException e) {
                    Log.e("ParseError", "Unable to parse date: " + value, e);
                }
                break;
        }
    }

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
    private int mOwnVP=0;
    private int mOpponentVP=0;
    private String mGameType;
    private Status mStatus;
    private RoundCounter mRoundCounter;

    // constructors
    public GameSession() {
        mCreationDT = new Date();
        mRoundCounter = new RoundCounter();
        mStatus = Status.Created;
    }
    public GameSession(String type, int points) {
        mCreationDT = new Date();
        mPointSize = points;
        mGameType = type;
        mStatus = Status.Created;

        mRoundCounter = new RoundCounter();
    }
    // data accessors
    public int getPoints() { return mPointSize; }
    public String getGameType() { return mGameType; }
    public Date getCreationDT() { return mCreationDT; }
    public Status getStatus() { return mStatus; }

    public int getOwnVP() { return mOwnVP; }
    public int getOpponentVP() { return mOpponentVP; }
    public RoundCounter getRoundCounter() { return mRoundCounter; }

    public void setOwnVP(int vp) {
        if(vp != mOwnVP) {
            markChange(new ColumnChange(Col_PlayerVP, Integer.toString(vp)));
        }
        mOwnVP = vp;
    }
    public void setOpponentVP(int vp) {
        if(vp != mOpponentVP) {
            markChange(new ColumnChange(Col_OpponentVP, Integer.toString(vp)));
        }
        mOpponentVP = vp;
    }

    // FIXME: Are these really needed? Find a better solution to create an instance from DB !!
    public void setPoints(int points) { mPointSize = points; }
    public void setGameType(String type) { mGameType = type; }
    public void setCreationDT(Date d) { mCreationDT = d; }
    public void setStatus(Status s) { mStatus = s; }
}
