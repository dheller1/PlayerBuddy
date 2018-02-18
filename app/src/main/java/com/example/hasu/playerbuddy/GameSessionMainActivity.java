package com.example.hasu.playerbuddy;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hasu.playerbuddy.fragments.GameSessionFragmentPagerAdapter;
import com.example.hasu.playerbuddy.fragments.GameSessionPlayFragment;
import com.example.hasu.playerbuddy.fragments.GameSessionSetupFragment;

public class GameSessionMainActivity extends AppCompatActivity
        implements GameSessionSetupFragment.OnFragmentInteractionListener, GameSessionPlayFragment.OnFragmentInteractionListener {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_session_main);

        // find view pager to allow swiping between fragments
        ViewPager vp = findViewById(R.id.viewpager);
        GameSessionFragmentPagerAdapter adapter = new GameSessionFragmentPagerAdapter(this, getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tabLay = findViewById(R.id.sliding_tabs);
        tabLay.setupWithViewPager(vp);
    }

    public void onFragmentInteraction(Uri uri) {
        ;
    }
}
