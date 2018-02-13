package com.example.hasu.playerbuddy.core;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class GameSession {
    public class Serializer {
        private GameSession mSession;
        private DateFormat mDateFormat;
        private String mFilename; // actual filename under which the GameSession object is stored
        public static final String FILE_EXTENSION = ".pbs";
        private static final String JSON_GAMETYPE = "game_type";
        private static final String JSON_STATUS = "status";
        private static final String JSON_POINTSIZE = "point_size";
        private static final String JSON_CREATIONDT = "created";
        private static final String JSON_ROUNDCOUNTER = "round_counter";
        Serializer(GameSession session) {
            mSession = session;
            mDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        }
        public String getFilename() { return mFilename; }
        public void setFilename(String fn) { mFilename = fn; }

        public void loadFromFile(String filename, Context ctx)
                throws IOException, JSONException, ParseException {
            JSONSerializer js = new JSONSerializer(ctx);
            readFromJSON(js.readFile(filename));
            setFilename(mFilename = filename);
        }

        public void writeToFile(String filename, Context ctx)
                throws IOException, JSONException {
            JSONSerializer js = new JSONSerializer(ctx);
            mFilename = js.writeToFile(toJSON(), filename, FILE_EXTENSION);
        }

        public JSONObject toJSON() throws JSONException {
            JSONObject jo = new JSONObject();
            jo.put(JSON_GAMETYPE, mSession.getGameType());
            jo.put(JSON_STATUS, mSession.getStatus().getId());
            jo.put(JSON_POINTSIZE, mSession.getPoints());
            jo.put(JSON_CREATIONDT, mDateFormat.format(mSession.getCreationDT()));
            jo.put(JSON_ROUNDCOUNTER, mSession.mRoundCounter.getValue());
            return jo;
        }

        public void readFromJSON(JSONObject jo) throws JSONException, ParseException {
            mSession.mGameType = jo.getString(JSON_GAMETYPE);
            final int statusCode = jo.getInt(JSON_STATUS);
            if(statusCode == Status.Created.getId()) {
                mSession.mStatus = Status.Created;
            }
            else if(statusCode == Status.Started.getId()) {
                mSession.mStatus = Status.Started;
            }
            else if(statusCode == Status.Finished.getId()) {
                mSession.mStatus = Status.Finished;
            }
            mSession.mStatus = Status.Created;
            mSession.mPointSize = jo.getInt(JSON_POINTSIZE);
            mSession.mCreationDT = mDateFormat.parse(jo.getString(JSON_CREATIONDT));
            mSession.mRoundCounter.setValue(jo.getInt(JSON_ROUNDCOUNTER));

        }

        public String suggestFilename() {
            StringBuilder sb = new StringBuilder();
            sb.append(mSession.getGameType());
            sb.append("_");
            sb.append(mSession.getPoints());
            sb.append("_");
            sb.append(mDateFormat.format(mSession.getCreationDT()));
            String fn = sb.toString();
            fn = fn.replaceAll("[\\\\/:*?\"<>| ,.]", "_")
                   .replaceAll("_+", "_"); // no multiple underscores
            return fn;
        }
    }

    public final Serializer SERIALIZER = new Serializer(this);
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
}
