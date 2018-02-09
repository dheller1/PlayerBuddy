package com.example.hasu.playerbuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DeploymentOverviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment_overview);

        ImageView iv1 = (ImageView)findViewById(R.id.deploymentView1);
        iv1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
        ImageView iv2 = (ImageView)findViewById(R.id.deploymentView2);
        iv2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
        ImageView iv3 = (ImageView)findViewById(R.id.deploymentView3);
        iv3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
        ImageView iv4 = (ImageView)findViewById(R.id.deploymentView4);
        iv4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
        ImageView iv5 = (ImageView)findViewById(R.id.deploymentView5);
        iv5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
        ImageView iv6 = (ImageView)findViewById(R.id.deploymentView6);
        iv6.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.deploy1));
    }

    public Bitmap loadImage(int imageID, int targetHeight, int targetWidth) {
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
    }
}
