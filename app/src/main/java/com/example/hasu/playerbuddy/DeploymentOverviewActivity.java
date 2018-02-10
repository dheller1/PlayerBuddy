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

        for(ImageView v : mDeploymentViews) {
            v.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
        }
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
            fullscreenIntent.putExtra(ShowFullscreenImageActivity.FULLSCREEN_IMAGE_ID, R.drawable.deploy1);
            startActivity(fullscreenIntent);
        }
    }

    /*public Bitmap loadImage(int imageID, int targetHeight, int targetWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), imageID, options);

        final int origHeight = options.outHeight;
        final int origWidth = options.outWidth;
        int inSampleSize = 1;
        while((origHeight/(2*inSampleSize)) > targetHeight &&
              (origWidth/(2*inSampleSize)) > targetWidth)
        {
            inSampleSize *= 2;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), imageID, options);
    }*/
}
