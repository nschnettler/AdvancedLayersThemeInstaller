package com.schnettler.hex_layerstheme.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.schnettler.hex_layerstheme.R;
import com.schnettler.hex_layerstheme.adapters.ImageAdapter;

/**
 * Created by Niklas on 09.04.2015.
 */
public class ImageViewerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewer);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);




        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(ImageViewerActivity.this);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int result = extras.getInt("image");



            viewPager.setAdapter(adapter);

            viewPager.setCurrentItem(result-1);
            System.out.println(result);


        }

    }
}
