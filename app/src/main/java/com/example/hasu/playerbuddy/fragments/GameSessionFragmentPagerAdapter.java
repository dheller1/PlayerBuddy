package com.example.hasu.playerbuddy.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hasu.playerbuddy.fragments.GameSessionSetupFragment;
import com.example.hasu.playerbuddy.R;

public class GameSessionFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public GameSessionFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // determine fragment for each tab
    @Override public int getCount() { return 2; }
    @Override public Fragment getItem(int position) {
        if(position == 0) {
            return new GameSessionSetupFragment();
        } else if(position == 1) {
            return new GameSessionPlayFragment();
        }
        return null;
    }

    // titles for each tab
    @Override public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return mContext.getString(R.string.category_game_setup);
            case 1:
                return mContext.getString(R.string.category_game_play);
            default:
                return null;
        }
    }

}
