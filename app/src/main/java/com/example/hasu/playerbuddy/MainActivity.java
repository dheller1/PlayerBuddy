package com.example.hasu.playerbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hasu.playerbuddy.core.GameSession;
import com.example.hasu.playerbuddy.core.RoundCounter;
import com.example.hasu.playerbuddy.core.db.DBAccessor;


public class MainActivity extends AppCompatActivity {
    static final String KEY_OWNVP = "KEY_OWNVP";
    static final String KEY_OPPONENTVP = "KEY_OPPONENTVP";
    static final String KEY_ROUNDCOUNTER = "KEY_ROUNDCOUNTER";
    static final String KEY_SESSION_ID = "KEY_SESSION_ID";

    private GameSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        long sessionID = intent.getLongExtra(KEY_SESSION_ID, -1);

        mSession = new GameSession();
        DBAccessor dba = new DBAccessor(getApplicationContext());
        dba.open();
        try {
            mSession.loadFromDB(dba.getDB(), sessionID);
        }
        catch(Exception e) {
            Log.e("MainActivity::onCreate", "Unable to load session from DB.", e);
        }
        finally {
            dba.close();
        }
        updateControls();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ROUNDCOUNTER, mSession.getRoundCounter().getValue());
        outState.putInt(KEY_OWNVP, mSession.getOwnVP());
        outState.putInt(KEY_OPPONENTVP, mSession.getOpponentVP());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSession.getRoundCounter().setValue(savedInstanceState.getInt(KEY_ROUNDCOUNTER));
        mSession.setOwnVP(savedInstanceState.getInt(KEY_OWNVP));
        mSession.setOpponentVP(savedInstanceState.getInt(KEY_OPPONENTVP));
        updateControls();
    }

    public void onSwitchToDeploymentOverviewActivity(View view) {
        Intent intent = new Intent(this, DeploymentOverviewActivity.class);
        startActivity(intent);
    }

    protected void onDecreaseRoundNumber(View v) {
        if(mSession.getRoundCounter().getValue() > 0) {
            mSession.getRoundCounter().dec();
            updateControls();
        }
    }

    protected void onIncreaseRoundNumber(View v) {
        mSession.getRoundCounter().inc();
        updateControls();
    }

    protected void onDecreaseOwnVP(View v) {
        if(mSession.getOwnVP() > 0) {
            mSession.setOwnVP(mSession.getOwnVP()-1);
            updateControls();
        }
    }

    protected void onIncreaseOwnVP(View v) {
        mSession.setOwnVP(mSession.getOwnVP()+1);
        updateControls();
    }


    protected void onDecreaseOpponentVP(View v) {
        if(mSession.getOpponentVP() > 0) {
            mSession.setOpponentVP(mSession.getOpponentVP()-1);
            updateControls();
        }
    }

    protected void onIncreaseOpponentVP(View v) {
        mSession.setOpponentVP(mSession.getOpponentVP()+1);
        updateControls();
    }

    public void onSaveChanges(View view) {

    }

    private void updateActivations() {
        findViewById(R.id.roundNumberMinus).setEnabled(mSession.getRoundCounter().getValue() > 0);
        findViewById(R.id.ownVpMinus).setEnabled(mSession.getOwnVP() > 0);
        findViewById(R.id.opponentVpMinus).setEnabled(mSession.getOpponentVP() > 0);
    }

    private void updateControls() {
        updateLabels();
        updateActivations();
    }

    private void updateLabels() {
        TextView rnd = (TextView) findViewById(R.id.roundNumberLabel);
        if(rnd != null) {
            rnd.setText(mSession.getRoundCounter().toString());
        }
        TextView tOwn = (TextView) findViewById(R.id.ownVpLabel);
        if(tOwn != null) {
            tOwn.setText(Integer.toString(mSession.getOwnVP()));
        }
        TextView tOpp = (TextView) findViewById(R.id.opponentVpLabel);
        if(tOpp != null) {
            tOpp.setText(Integer.toString(mSession.getOpponentVP()));
        }
    }
}
