package com.schnettler.AdvancedLayersThemeInstaller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.schnettler.AdvancedLayersThemeInstaller.R;

/**
 * Created by Niklas on 04.04.2015.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName("myPrefs");
        addPreferencesFromResource(R.xml.settings);

        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = myPrefs.edit();
        String InstallationMethod = myPrefs.getString("InstallationMode", "");

        ListPreference colour = (ListPreference) findPreference("InstallationMode");
        if (colour.getValue() == null) {
            colour.setValue(InstallationMethod);
        }




    }

    @Override
        public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }





}
