package com.example.hasu.playerbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSwitchToDeploymentOverviewActivity(View view) {
        Intent intent = new Intent(this, DeploymentOverviewActivity.class);
        startActivity(intent);
    }
}
