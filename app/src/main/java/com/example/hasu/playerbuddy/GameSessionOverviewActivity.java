package com.example.hasu.playerbuddy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hasu.playerbuddy.core.GameSession;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;


public class GameSessionOverviewActivity extends AppCompatActivity {
    class GameSessionAdapter extends BaseAdapter {
        // Should be inner class to have access to the Context and functions as getLayoutInflater().
        private List<GameSession> mSessions = new ArrayList<>();

        private void addItem(GameSession session) {
            mSessions.add(0, session); // insert to front to have the newest displayed first
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mSessions.size();
        }

        @Override
        public GameSession getItem(int which) {
            return mSessions.get(which);
        }

        @Override
        public long getItemId(int which) {
            return which;
        }

        @Override
        public View getView(int which, View view, ViewGroup viewGroup) {
            // ensure view is inflated
            if(view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.listitem_game_session, viewGroup, false);
            }

            TextView titleText = (TextView)view.findViewById(R.id.titleText);
            TextView subtitleText = (TextView)view.findViewById(R.id.subtitleText);

            GameSession session = mSessions.get(which);
            titleText.setText(makeTitleText(session));
            subtitleText.setText(makeSubtitleText(session));

            return view;
        }

        private String makeTitleText(GameSession session) {
            return session.getGameType() + " (" + Integer.toString(session.getPoints()) + "pts)";
        }

        private String makeSubtitleText(GameSession session) {
            String s = "";
            switch(session.getStatus()) {
                case Created:
                    s += "created ";
                    break;
                case Started:
                    s += "started ";
                    break;
                case Finished:
                    s += "finished ";
                    break;
            }
            DateFormat formatter = DateFormat.getDateTimeInstance();
            s += formatter.format(session.getCreationDT());
            return s;
        }
    }

    private GameSessionAdapter mSessionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_session_overview);

        mSessionAdapter = new GameSessionAdapter();
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(mSessionAdapter);
        // Handle clicks in list
        //list.setOnItemClickListener(null);
    }

    public void onAddSession(View view) {
        GameSession s = new GameSession("Warhammer 40.000", 1500);
        mSessionAdapter.addItem(s);
    }
}
