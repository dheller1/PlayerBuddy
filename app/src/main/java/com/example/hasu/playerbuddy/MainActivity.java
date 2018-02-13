package com.example.hasu.playerbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasu.playerbuddy.core.GameSession;
import com.example.hasu.playerbuddy.core.JSONSerializer;
import com.example.hasu.playerbuddy.core.RoundCounter;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {
    static final String KEY_OWNVP = "KEY_OWNVP";
    static final String KEY_OPPONENTVP = "KEY_OPPONENTVP";
    static final String KEY_ROUNDCOUNTER = "KEY_ROUNDCOUNTER";

    static final String KEY_SESSIONFILE = "KEY_SESSION_FILENAME";

    private int mOwnVP, mOpponentVP;
    private RoundCounter mRoundCnt;

    private GameSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOwnVP = 0;
        mOpponentVP = 0;
        mRoundCnt = new RoundCounter();

        Intent intent = getIntent();
        String sessionFilename = intent.getStringExtra(KEY_SESSIONFILE);
        Toast.makeText(this, "Loading " + sessionFilename, Toast.LENGTH_SHORT).show();

        JSONSerializer js = new JSONSerializer(getApplicationContext());
        try {
            mSession = new GameSession("", 0);
            mSession.SERIALIZER.loadFromFile(sessionFilename, getApplicationContext());
        }
        catch(JSONException|IOException|ParseException e) {
            Log.e("Read invalid data", "", e);
        }
        updateControls();
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
