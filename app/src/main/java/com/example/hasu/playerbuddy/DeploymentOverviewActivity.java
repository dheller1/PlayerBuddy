package com.example.hasu.playerbuddy;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DeploymentOverviewActivity extends AppCompatActivity {
    static final String KEY_HIGHLIGHTED = "KEY_HIGHLIGHTED";

    private ArrayList<ImageView> mDeploymentViews;
    private int mHighlightedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment_overview);

        mDeploymentViews = new ArrayList<>();
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView1));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView2));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView3));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView4));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView5));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView6));

        mDeploymentViews.get(0).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
        mDeploymentViews.get(1).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy2));
        mDeploymentViews.get(2).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy3));
        mDeploymentViews.get(3).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy4));
        mDeploymentViews.get(4).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy5));
        mDeploymentViews.get(5).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy6));

        ((TextView)findViewById(R.id.textView1)).setText(R.string.deployment_map_1);
        ((TextView)findViewById(R.id.textView2)).setText(R.string.deployment_map_2);
        ((TextView)findViewById(R.id.textView3)).setText(R.string.deployment_map_3);
        ((TextView)findViewById(R.id.textView4)).setText(R.string.deployment_map_4);
        ((TextView)findViewById(R.id.textView5)).setText(R.string.deployment_map_5);
        ((TextView)findViewById(R.id.textView6)).setText(R.string.deployment_map_6);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_HIGHLIGHTED, mHighlightedIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDeploymentViews.clear();
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView1));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView2));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView3));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView4));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView5));
        mDeploymentViews.add((ImageView)findViewById(R.id.deploymentView6));
        mHighlightedIndex = savedInstanceState.getInt(KEY_HIGHLIGHTED);
        scrollToIndex(mHighlightedIndex, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deployment_overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.randomizeDeploymentMap:
                chooseRandomDeployment();
                return true;
            case R.id.clearDeploymentMap:
                clearDeployment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearDeployment() {
        if(mHighlightedIndex >= 0) {
            ImageView iv = mDeploymentViews.get(mHighlightedIndex);
            if(iv != null) {
                iv.setPadding(0, 0, 0, 0);
                iv.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        mHighlightedIndex = -1;
        scrollToIndex(0, true);
    }

    private void chooseRandomDeployment() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, mDeploymentViews.size());

        if(mHighlightedIndex >= 0) {
            ImageView iv = mDeploymentViews.get(mHighlightedIndex);
            if(iv != null) {
                iv.setPadding(0, 0, 0, 0);
                iv.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        ImageView iv = mDeploymentViews.get(randomIndex);
        if(iv != null) {
            iv.setPadding(5, 5, 5, 5);
            iv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mHighlightedIndex = randomIndex;
            scrollToIndex(randomIndex, true);
        }
    }

    private void scrollToIndex(int index, boolean animate) {
        final ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        if(sv != null) {
            final int scrollToY = mDeploymentViews.get(index).getTop()-200;

            if(animate) {
                sv.post(new Runnable() {
                    @Override
                    public void run() {
                        sv.smoothScrollTo(0, scrollToY);
                    }
                });
            }
            else {
                sv.scrollTo(0, scrollToY);
            }
        }
    }

    public void onImageClicked(View view) {
        ImageView iv = (ImageView) view;
        if(iv != null) {
            // FIXME: Try to query the resource ID here?! But it seems not easy starting with an
            // ImageView instance...
            Intent fullscreenIntent = new Intent(this, ShowFullscreenImageActivity.class);

            int resId = 0;
            if(iv == mDeploymentViews.get(0)) {
                resId = R.drawable.deploy1;
            }
            else if(iv == mDeploymentViews.get(1)) {
                resId = R.drawable.deploy2;
            }
            else if(iv == mDeploymentViews.get(2)) {
                resId = R.drawable.deploy3;
            }
            else if(iv == mDeploymentViews.get(3)) {
                resId = R.drawable.deploy4;
            }
            else if(iv == mDeploymentViews.get(4)) {
                resId = R.drawable.deploy5;
            }
            else if(iv == mDeploymentViews.get(5)) {
                resId = R.drawable.deploy6;
            }
            fullscreenIntent.putExtra(ShowFullscreenImageActivity.FULLSCREEN_IMAGE_ID, resId);
            startActivity(fullscreenIntent);
        }
    }
}
