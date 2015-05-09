package com.schnettler.AdvancedLayersThemeInstaller.adapters;

import com.schnettler.AdvancedLayersThemeInstaller.fragments.TutorialFragment1;
import com.schnettler.AdvancedLayersThemeInstaller.fragments.TutorialFragment2;
import com.schnettler.AdvancedLayersThemeInstaller.fragments.TutorialFragment3;
import com.schnettler.AdvancedLayersThemeInstaller.fragments.TutorialFragment4;
import com.schnettler.AdvancedLayersThemeInstaller.fragments.TutorialFragment5;
import com.schnettler.AdvancedLayersThemeInstaller.fragments.TutorialFragment6;
import com.schnettler.AdvancedLayersThemeInstaller.fragments.TutorialFragment7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                // Top Rated fragment activity
                return new TutorialFragment1();
            case 1:
                // Games fragment activity
                return new TutorialFragment3();
            case 2:
                // Movies fragment activity
                return new TutorialFragment4();

            case 3:
                // Movies fragment activity
                return new TutorialFragment6();

            case 4:
                // Movies fragment activity
                return new TutorialFragment5();

            case 5:
                // Movies fragment activity
                return new TutorialFragment7();

            case 6:
                // Movies fragment activity
                return new TutorialFragment2();



        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 7;
    }

}
