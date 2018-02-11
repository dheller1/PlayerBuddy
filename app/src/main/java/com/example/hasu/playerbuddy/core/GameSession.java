package com.example.hasu.playerbuddy.core;

import java.util.Date;

public class GameSession {
    public enum Status { Created, Started, Finished };

    private Date mCreationDT, mStartGameDT, mFinishGameDT;
    private int mPointSize;
    private String mGameType;
    private Status mStatus;

    public GameSession(String type, int points) {
        mCreationDT = new Date();
        mPointSize = points;
        mGameType = type;
        mStatus = Status.Created;
    }

    public int getPoints() { return mPointSize; }
    public String getGameType() { return mGameType; }
    public Date getCreationDT() { return mCreationDT; }
    public Status getStatus() { return mStatus; }
}
