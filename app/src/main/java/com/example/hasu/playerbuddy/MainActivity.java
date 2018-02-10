package com.example.hasu.playerbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

class RoundCounter {
    private String[] labels = {"Top of ", "Bottom of "};
    private int counter;
    RoundCounter() { counter = 0; }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(labels[counter % labels.length]);
        sb.append(counter / labels.length + 1);
        return sb.toString();
    }
    public int getValue() { return counter; }
    public void setValue(int i) { counter = i; }
    public RoundCounter inc() { ++counter; return this; }
    public RoundCounter dec() { --counter; return this; }

};

public class MainActivity extends AppCompatActivity {
    static final String KEY_OWNVP = "KEY_OWNVP";
    static final String KEY_OPPONENTVP = "KEY_OPPONENTVP";
    static final String KEY_ROUNDCOUNTER = "KEY_ROUNDCOUNTER";

    private int mOwnVP, mOpponentVP;
    private RoundCounter mRoundCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOwnVP = 0;
        mOpponentVP = 0;
        mRoundCnt = new RoundCounter();
        updateControls();
        //Toast.makeText(this, "In onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ROUNDCOUNTER, mRoundCnt.getValue());
        outState.putInt(KEY_OWNVP, mOwnVP);
        outState.putInt(KEY_OPPONENTVP, mOpponentVP);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRoundCnt.setValue(savedInstanceState.getInt(KEY_ROUNDCOUNTER));
        mOwnVP = savedInstanceState.getInt(KEY_OWNVP);
        mOpponentVP = savedInstanceState.getInt(KEY_OPPONENTVP);
        updateControls();
    }

    public void onSwitchToDeploymentOverviewActivity(View view) {
        Intent intent = new Intent(this, DeploymentOverviewActivity.class);
        startActivity(intent);
    }

    protected void onDecreaseRoundNumber(View v) {
        if(mRoundCnt.getValue() > 0) {
            mRoundCnt.dec();
            updateControls();
        }
    }

    protected void onIncreaseRoundNumber(View v) {
        mRoundCnt.inc();
        updateControls();
    }

    protected void onDecreaseOwnVP(View v) {
        if(mOwnVP > 0) {
            --mOwnVP;
            updateControls();
        }
    }

    protected void onIncreaseOwnVP(View v) {
        ++mOwnVP;
        updateControls();
    }


    protected void onDecreaseOpponentVP(View v) {
        if(mOpponentVP > 0) {
            --mOpponentVP;
            updateControls();
        }
    }

    protected void onIncreaseOpponentVP(View v) {
        ++mOpponentVP;
        updateControls();
    }

    private void updateActivations() {
        findViewById(R.id.roundNumberMinus).setEnabled(mRoundCnt.getValue() > 0);
        findViewById(R.id.ownVpMinus).setEnabled(mOwnVP > 0);
        findViewById(R.id.opponentVpMinus).setEnabled(mOpponentVP> 0);
    }

    private void updateControls() {
        updateLabels();
        updateActivations();
    }

    private void updateLabels() {
        TextView rnd = (TextView) findViewById(R.id.roundNumberLabel);
        if(rnd != null) {
            rnd.setText(mRoundCnt.toString());
        }
        TextView tOwn = (TextView) findViewById(R.id.ownVpLabel);
        if(tOwn != null) {
            tOwn.setText(Integer.toString(mOwnVP));
        }
        TextView tOpp = (TextView) findViewById(R.id.opponentVpLabel);
        if(tOpp != null) {
            tOpp.setText(Integer.toString(mOpponentVP));
        }
    }
}
