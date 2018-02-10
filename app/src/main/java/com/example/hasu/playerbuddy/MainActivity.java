package com.example.hasu.playerbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    public RoundCounter inc() { ++counter; return this; }
    public RoundCounter dec() { --counter; return this; }

};

public class MainActivity extends AppCompatActivity {

    private int ownVP, opponentVP;
    private RoundCounter roundCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ownVP = 0;
        opponentVP = 0;
        roundCnt = new RoundCounter();
        updateLabels();
        updateActivations();
    }

    public void onSwitchToDeploymentOverviewActivity(View view) {
        Intent intent = new Intent(this, DeploymentOverviewActivity.class);
        startActivity(intent);
    }

    protected void onDecreaseRoundNumber(View v) {
        if(roundCnt.getValue() > 0) {
            roundCnt.dec();
            updateLabels();
            updateActivations();
        }
    }

    protected void onIncreaseRoundNumber(View v) {
        roundCnt.inc();
        updateLabels();
        updateActivations();
    }

    protected void onDecreaseOwnVP(View v) {
        if(ownVP > 0) {
            --ownVP;
            updateLabels();
            updateActivations();
        }
    }

    protected void onIncreaseOwnVP(View v) {
        ++ownVP;
        updateLabels();
        updateActivations();
    }


    protected void onDecreaseOpponentVP(View v) {
        if(opponentVP > 0) {
            --opponentVP;
            updateLabels();
            updateActivations();
        }
    }

    protected void onIncreaseOpponentVP(View v) {
        ++opponentVP;
        updateLabels();
        updateActivations();
    }

    private void updateActivations() {
        findViewById(R.id.roundNumberMinus).setEnabled(roundCnt.getValue() > 0);
        findViewById(R.id.ownVpMinus).setEnabled(ownVP > 0);
        findViewById(R.id.opponentVpMinus).setEnabled(opponentVP> 0);
    }

    private void updateLabels() {
        TextView rnd = (TextView) findViewById(R.id.roundNumberLabel);
        if(rnd != null) {
            rnd.setText(roundCnt.toString());
        }
        TextView tOwn = (TextView) findViewById(R.id.ownVpLabel);
        if(tOwn != null) {
            tOwn.setText(Integer.toString(ownVP));
        }
        TextView tOpp = (TextView) findViewById(R.id.opponentVpLabel);
        if(tOpp != null) {
            tOpp.setText(Integer.toString(opponentVP));
        }
    }
}
