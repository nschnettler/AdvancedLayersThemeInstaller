package com.schnettler.AdvancedLayersThemeInstaller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schnettler.AdvancedLayersThemeInstaller.R;

public class TutorialFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tutorial1, container, false);
        return rootView;
    }
}