package com.example.hasu.playerbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShowFullscreenImageActivity extends AppCompatActivity {
    public static final String FULLSCREEN_IMAGE_ID = "FULLSCREEN_IMAGE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show_fullscreen_image);

        // set the actual image
        ImageView fullscreenImg = (ImageView) findViewById(R.id.fullscreenImageView);
        Intent callingActivityIntent = getIntent();
        if(callingActivityIntent != null) {
            int imageID = callingActivityIntent.getIntExtra(FULLSCREEN_IMAGE_ID, 0);
            if(imageID != 0) {
                fullscreenImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageID));
            }
        }
    }
}
