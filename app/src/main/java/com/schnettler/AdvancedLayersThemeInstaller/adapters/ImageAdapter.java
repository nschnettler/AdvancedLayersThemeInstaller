package com.schnettler.AdvancedLayersThemeInstaller.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.schnettler.AdvancedLayersThemeInstaller.R;
import com.schnettler.AdvancedLayersThemeInstaller.activities.ImageViewerActivity;
import com.schnettler.AdvancedLayersThemeInstaller.activities.MainActivity;

/**
 * Created by Niklas on 09.04.2015.
 */

public class ImageAdapter extends PagerAdapter {
    Context context;

    final int[] GalImages = MainActivity.GalImages;


    public ImageAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context)
                .load(GalImages[position])
                .into(imageView);

        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}