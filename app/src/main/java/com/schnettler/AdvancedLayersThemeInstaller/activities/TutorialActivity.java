package com.schnettler.AdvancedLayersThemeInstaller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.schnettler.AdvancedLayersThemeInstaller.R;
import com.schnettler.AdvancedLayersThemeInstaller.adapters.TabsPagerAdapter;

import com.viewpagerindicator.CirclePageIndicator;

public class TutorialActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        // Initilization
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new
                TabsPagerAdapter(getSupportFragmentManager()
        ));

        CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);
        titleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Button skip = (Button) findViewById(R.id.skip);
                Button done = (Button) findViewById(R.id.done);
                switch (pager.getCurrentItem()){
                    case 0:
                        done.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        done.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        done.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        done.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        done.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        done.setVisibility(View.GONE);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        done.setVisibility(View.VISIBLE);
                        skip.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void BackToMain (View view) {
        RadioGroup rd = (RadioGroup) findViewById(R.id.radioGroup);
        // get selected radio button from radioGroup
        int selectedId = rd.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radioSelected = (RadioButton) findViewById(selectedId);

        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("InstallationMode", radioSelected.getText().toString());
        editor.putBoolean("RanBefore2", true);
        editor.commit();


        Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Skip (View view) {
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setCurrentItem(6, true);
    }
}