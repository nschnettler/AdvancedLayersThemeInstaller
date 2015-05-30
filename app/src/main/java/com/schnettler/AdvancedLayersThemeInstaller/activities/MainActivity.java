package com.schnettler.AdvancedLayersThemeInstaller.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.schnettler.AdvancedLayersThemeInstaller.R;
import com.schnettler.AdvancedLayersThemeInstaller.helper.CopyUnzipHelper;
import com.schnettler.AdvancedLayersThemeInstaller.helper.RootCommandsInstallationHelper;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;



public class MainActivity extends AppCompatActivity {


    //Variables you HAVE to CHANGE!
    final int NumberOfOverlays = 6;        //Number of "Normal Overlays
    final int NumberOfColorOverlays = 5;    //Number of Overlays which are available in different Colors

    final int NumberOfColors = 2;           //Number of available Colors (0 if you only use normal Overlays)

    public static final int NumberOfScreenshots = 16;

    public static String ThemeName = "Test";         //Name of the folder created in /Overlays. This name should not include spaces.






    //Variables you SHOULD NOT CHANGE!

    private boolean[][] DontInstallOnInstallAll = new boolean[NumberOfOverlays+ NumberOfColorOverlays+1][10];

    private int whichRadioButton = 1;

    String ProjectsProgress = null;

    private int atleastone=0;

    public static final int NumberOfScreenshotsMain = 3;
    public static int[] GalImages = new int[NumberOfScreenshots];

    boolean isVisible = false;

    private String whichColor = null;

    final String TargetPath = "/system/vendor/overlay/";
    private String ThemeFolder = "/sdcard/Overlays/"+ThemeName+"/";
    private String ThemeFolderGeneral = ThemeFolder+"General/";

    private String AppPath =null;

    final ImageView imageView[] = new ImageView[NumberOfScreenshots+1];

    private int InstallOverlay[] = new int[NumberOfOverlays+NumberOfColorOverlays+1];

    private String OverlayPath[] = new String[NumberOfOverlays+NumberOfColorOverlays+1];
    private String OverlayName[] = new String[NumberOfOverlays+NumberOfColorOverlays+1];

    int[][] InstallAdditionalOverlays = new int[NumberOfOverlays+NumberOfColorOverlays+3][10];

    private boolean[] ShowAdditionalOverlaysDialog = new boolean[NumberOfOverlays+NumberOfColorOverlays+2];


    private String AdditionalOverlayPath[][] = new String[NumberOfOverlays+NumberOfColorOverlays+2][10];
    private String AdditionalOverlayName[][] = new String[NumberOfOverlays+NumberOfColorOverlays+2][10];


    final int[] NumberOfAdditionalOverlays = new int[NumberOfOverlays+NumberOfColorOverlays+1];

    private int[] AdditionalDialogMode = new int[NumberOfOverlays+NumberOfColorOverlays+2];


    private String[] Color = new String[NumberOfColors+1];

    int atleastOneIsClicked = 0;




    //Observable Scroll View variables | DON´T Change/////////////////////////
    private View mFab;
    private View mFab2;






    ////////////////////////////////////////////////////////////////////////////////////////////////
    //ON Create Method///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.support.design.widget.FloatingActionButton fab2 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab2);

        if (isFirstTime2()) {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        }
        fab2.setVisibility(View.INVISIBLE);

        //Initialize Layout
        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getResources().getString(R.string.AppName));
        loadBackdrop();


        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installTheme(this);
            }
        });

        mFab2 = findViewById(R.id.fab2);
        mFab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installTheme(this);
            }
        });




        //Names Of "normal" Overlays

        OverlayName[0] = "App Name 1";                  //Name of app which is Overlayed by first Overlay
        OverlayName[1] = "App Name 2";                  //...
        OverlayName[2] = "App Name 3";
        OverlayName[3] = "App Name 4";
        OverlayName[4] = "App Name 5";
        OverlayName[5] = "App Name 6";


        //Names of color specific Overlays, delete them if you dont use color specific Overlays

        OverlayName[6] =  "";                              //If you use color specific Overlays add a OverlayName[] after the last normal overlay one.

        OverlayName[7] = "Color App Name 1";              //name of the app which is Overlayed by the first color specific Overlay
        OverlayName[8] = "Color App Name 2";
        OverlayName[9] = "Color App Name 3";
        OverlayName[10] = "Color App Name 4";
        OverlayName[11] = "Color App Name 5";



        //Colors your theme is available in
        Color[0] = "Color1";
        Color[1] = "Color2";
        //Color[2] = "Color3";
        //Color[3] = "Color4";
        //...



        //Add optional / additional Overlays remove the /* and */ if you want to use additional Overlays. Remove the ones I wrote and add those yyou need. This is only a example.
/*
        InstallAdditionalOverlays[0][0] = 1;                        //First App has additional Overlays
        NumberOfAdditionalOverlays[0] = 2;                          //How many additional Overlays App1 has
        //ShowAdditionalOverlaysDialog[0] = true;                   //Show a Dialog where the user can select which Overlays to install?
        AdditionalOverlayName[0][1] = "Additional Overlay 1 Without Dialog";
        AdditionalOverlayName[0][2] = "Additional Overlay 2 Without Dialog";



        InstallAdditionalOverlays[1][0] = 1;                        //Second App has additional Overlays
        NumberOfAdditionalOverlays[1] = 2;                          //How many additional Overlays App2 has
        ShowAdditionalOverlaysDialog[1] = true;                     //Show a Dialog where the user can select which Overlays to install?
        AdditionalOverlayName[1][1] = "Additional Overlay 3 With CheckboxDialog";
        AdditionalOverlayName[1][2] = "Additional Overlay 4 With CheckboxDialog";



        InstallAdditionalOverlays[8][0] = 1;                        //Ninth App has additional Overlays
        NumberOfAdditionalOverlays[8] = 2;                          //How many additional/optional Overlays App9 has
        ShowAdditionalOverlaysDialog[8] = true;                     //Show a Dialog where the user can select which Overlays to install?
        AdditionalDialogMode[8] = 1;                                // 0: Dialog with Checkboxes (More than one overlay can be chosen) | 1: Dialog with Radio Buttons (only one overlay can be chosen)
        AdditionalOverlayName[8][1] = "Additional Overlay 5 With RadioDialog";
        AdditionalOverlayName[8][2] = "Additional Overlay 6 With RadioDialog";

*/





        for (int i = 0; i<NumberOfScreenshots;i++){
            int i2=i+1;
            int j = getResources().getIdentifier("screenshot"+i2, "drawable", getPackageName());
            GalImages[i]=j;
        }








        //Generate filepaths of normal Overlays
        if (NumberOfOverlays==0){
            for (int i = 1; i <NumberOfOverlays+NumberOfColorOverlays+1;i++){
                String CurrentOverlyName = OverlayName[i].replaceAll(" ", "");
                OverlayPath[i] = ThemeName+"_"+CurrentOverlyName+".apk";

            }
        }else {
            for (int i = 0; i < NumberOfOverlays + NumberOfColorOverlays + 1; i++) {
                String CurrentOverlyName = OverlayName[i].replaceAll(" ", "");
                OverlayPath[i] = ThemeName + "_" + CurrentOverlyName + ".apk";

            }
        }

        //Generate filepaths of normal additional Overlays
        for (int i = 0; i<NumberOfOverlays+NumberOfColorOverlays+1; i++){
            if (NumberOfAdditionalOverlays[i]>0){
                for (int e=1; e< NumberOfAdditionalOverlays[i]+1;e++){
                    String CurrentOverlyName = AdditionalOverlayName[i][e].replaceAll(" ", "");
                    AdditionalOverlayPath[i][e] = ThemeName+"_"+CurrentOverlyName+".apk";
                }
            }
        }

        ProjectsProgress = this.getString(R.string.ProjectsProgress);


        //Scroll view with screenshots
        LinearLayout screenshotLayout = (LinearLayout)findViewById(R.id.LinearLayoutScreenshots);

        for (int i=0; i<NumberOfScreenshotsMain;i++){
            LinearLayout linear = new LinearLayout(this);

            int widht = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 161, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 286, getResources().getDisplayMetrics());
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

            LinearLayout.LayoutParams params
                   = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = margin;



            imageView[i]= new ImageView(this);
            //imageView[i].setTag(+i);
            imageView[i].setBackgroundColor(getResources().getColor(R.color.accent));


            final int finalI = i;
            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openimage(finalI + 1);
                }
            });

            linear.setLayoutParams(params);

            linear.addView(imageView[i]);
            screenshotLayout.addView(linear);
        }
        loadScreenshots();


        LinearLayout CardViewCategory1, CardViewCategory2 ;
        TextView Category1Name, Category2Name;
        if (NumberOfOverlays >= NumberOfColorOverlays) {
            CardViewCategory1 = (LinearLayout) findViewById(R.id.LinearLayoutCategory1);
            CardViewCategory2 = (LinearLayout) findViewById(R.id.LinearLayoutCategory2);
            Category1Name = (TextView) findViewById(R.id.Tv_Category1Name);
            Category1Name.setText(getResources().getString(R.string.Category1Name));
            Category2Name = (TextView) findViewById(R.id.Tv_Category2Name);
            Category2Name.setText(getResources().getString(R.string.Category2Name));
        } else {
            CardViewCategory1 = (LinearLayout) findViewById(R.id.LinearLayoutCategory2);
            CardViewCategory2 = (LinearLayout) findViewById(R.id.LinearLayoutCategory1);
            Category1Name = (TextView) findViewById(R.id.Tv_Category2Name);
            Category1Name.setText(getResources().getString(R.string.Category1Name));
            Category2Name = (TextView) findViewById(R.id.Tv_Category1Name);
            Category2Name.setText(getResources().getString(R.string.Category2Name));
        }

        //Cardview with normal Overlays
        for (int i = 0; i < NumberOfOverlays; i++)
        {
            TableRow row =new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

            final CheckBox[] check = new CheckBox[NumberOfOverlays];
            check[i]= new CheckBox(this);
            check[i].setText(OverlayName[i]);
            check[i].setTag(i);
            check[i].setId(i);
            check[i].setTextColor(getResources().getColor(R.color.chooser_text_color));
            check[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                //check if checkbox is clicked
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    for (int c=0; c<NumberOfOverlays; c++) {
                        if (buttonView.getTag().equals(c)) {
                            if (buttonView.isChecked()){
                                if (NumberOfAdditionalOverlays[c] >0) {
                                    if (InstallAdditionalOverlays[c][0] == 1){
                                        InstallOverlay[c]=0;
                                        atleastOneIsClicked = atleastOneIsClicked+1;

                                        if (ShowAdditionalOverlaysDialog[c]) {

                                            AdditionalOverlaysDialog(c);

                                        }
                                        else {
                                            for (int d = 0; d < NumberOfAdditionalOverlays[c]+1; d++){

                                                InstallAdditionalOverlays[c][d+1]=1;

                                            }
                                        }
                                    }
                                } else {
                                    InstallOverlay[c] = 1;
                                    atleastOneIsClicked = atleastOneIsClicked + 1;
                                }
                            }
                            else {
                                for (int i = 1; i <NumberOfAdditionalOverlays[c]+2;i++) {
                                    InstallAdditionalOverlays[c][i] = 0;
                                }
                                InstallOverlay[c] = 0;
                                atleastOneIsClicked = atleastOneIsClicked -1;
                            }

                            if (atleastOneIsClicked> 0) {

                                   fab2.setVisibility(View.VISIBLE);

                            } else {
                                fab2.setVisibility(View.INVISIBLE);

                            }
                        }
                    }
                }
            });
            row.addView(check[i]);
            CardViewCategory1.addView(row);
        }




        //CardView with color specific Overlays


        for (int i = NumberOfOverlays+1; i < NumberOfColorOverlays+NumberOfOverlays+1; i++)
        {
            TableRow row =new TableRow(this);

            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            final CheckBox[] check = new CheckBox[NumberOfColorOverlays+NumberOfOverlays+1];
            check[i]= new CheckBox(this);
            check[i].setText(OverlayName[i]);
            check[i].setTag(i);
            check[i].setId(i);
            check[i].setTextColor(getResources().getColor(R.color.chooser_text_color));
            check[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    for (int c = NumberOfOverlays+1; c <  NumberOfColorOverlays+NumberOfOverlays+1; c++) {

                        if (buttonView.getTag().equals(c)) {

                            if (buttonView.isChecked()) {
                                if (NumberOfAdditionalOverlays[c] > 0) {
                                    System.out.println("Install?" +InstallAdditionalOverlays[c][0]);
                                    if (InstallAdditionalOverlays[c][0] == 1) {
                                        InstallOverlay[c] = 0;
                                        atleastOneIsClicked = atleastOneIsClicked + 1;
                                        System.out.println(atleastOneIsClicked);
                                        if (ShowAdditionalOverlaysDialog[c]) {

                                            AdditionalOverlaysDialog(c);

                                        } else {
                                            for (int d = 0; d < NumberOfAdditionalOverlays[c] + 1; d++) {

                                                InstallAdditionalOverlays[c][d + 1] = 1;

                                            }
                                        }

                                    }
                                } else {
                                    InstallOverlay[c] = 1;
                                    atleastOneIsClicked = atleastOneIsClicked + 1;
                                }

                            } else {
                                atleastOneIsClicked = atleastOneIsClicked - 1;
                                for (int i = 1; i < NumberOfAdditionalOverlays[c] + 2; i++) {
                                    InstallAdditionalOverlays[c][i] = 0;

                                }
                                InstallOverlay[c] = 0;
                            }
                            if (atleastOneIsClicked> 0) {

                                isVisible = true;
                                fab2.setVisibility(View.VISIBLE);
                            }
                            else {
                                fab2.setVisibility(View.INVISIBLE);
                                isVisible = false;
                            }
                        }

                    }

                }
            });
            row.addView(check[i]);
            CardViewCategory2.addView(row);
        }


        //If there arent any color specific Overlays, hide the cardview
        if(NumberOfColorOverlays==0 || NumberOfOverlays==0) {
            CardView CardViewCategory = (CardView)findViewById(R.id.CardViewCategory2);
            CardViewCategory.setVisibility(View.GONE);
        }



        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);

        String InstallationMethod = myPrefs.getString("InstallationMode","");

        if (InstallationMethod.equals("Just copy Overlays")) {

            UncheckAllCheckBoxes("Disable");

        }



        //create the Theme folder
        File ThemeDirectory = new File("/sdcard/Overlays/"+ThemeName+"/");
        ThemeDirectory.mkdirs();



        fab2.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                installTheme(this);
            }
        }));

    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        }


        //OverflowMenu
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();


            if (id == R.id.action_settings) { //About

                super.onOptionsItemSelected(item);
                this.closeOptionsMenu();
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);

                startActivity(intent);
                return true;
            }

            else {
                if (id == R.id.action_delete) {  //Delete
                    DeleteOverlaysDialog();

                } else {
                    if (id == R.id.action_install) { //Install
                        super.onOptionsItemSelected(item);
                        this.closeOptionsMenu();
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.lovejoy777.rroandlayersmanager", "com.lovejoy777.rroandlayersmanager.managers.CardManager")); //open install Activity of Layers Manager
                        startActivity(intent);
                    }
                    else {
                        if (id == R.id.action_reboot) {
                            super.onOptionsItemSelected(item);
                            this.closeOptionsMenu();
                            (new Reboot()).execute();  //open Async task to reboot
                        }
                        else {
                            if (id == R.id.action_swipeview) {
                                super.onOptionsItemSelected(item);
                                this.closeOptionsMenu();
                                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);  //open settings

                                startActivity(intent);

                            }
                        }
                    }
                }
            }
            return super.onOptionsItemSelected(item);
        }





        public void openimage (int imagenumber) {
            Intent intent = new Intent(MainActivity.this, ImageViewerActivity.class);
            intent.putExtra("image",imagenumber);
            MainActivity.this.startActivity(intent);

        }







        //If FAB is clicked
        public void installTheme(View.OnClickListener view) {

            int NumberOfSelectedNormalOverlays = 0;
            for (int i = 0; i < NumberOfOverlays; i++)
            {
                NumberOfSelectedNormalOverlays = NumberOfSelectedNormalOverlays + InstallOverlay[i];
            }


            int NumberOfSelectedColorOverlays = 0;


            for (int i= NumberOfOverlays+1; i < NumberOfOverlays+NumberOfColorOverlays+1; i++){
                NumberOfSelectedColorOverlays = NumberOfSelectedColorOverlays + InstallOverlay[i];
            }


            int NumberOfSelectedaAdditionalOverlays = 0;
            for (int i = 0; i<NumberOfOverlays; i++){

                for (int e=1; e< NumberOfAdditionalOverlays[i]+2;e++){
                    NumberOfSelectedaAdditionalOverlays = NumberOfSelectedaAdditionalOverlays + InstallAdditionalOverlays[i][e];
                }
            }


            int NumberOfSelectedaAdditionalColorOverlays = 0;
            for (int i = NumberOfOverlays+1; i<NumberOfColorOverlays+NumberOfOverlays+1; i++){

                for (int e=1; e< NumberOfAdditionalOverlays[i]+2;e++){
                    NumberOfSelectedaAdditionalColorOverlays = NumberOfSelectedaAdditionalColorOverlays + InstallAdditionalOverlays[i][e];
                }
            }




        //No checkBox is checked
            if (NumberOfSelectedNormalOverlays==0 & NumberOfSelectedColorOverlays==0& NumberOfSelectedaAdditionalOverlays==0 & NumberOfSelectedaAdditionalColorOverlays == 0) {                                                            //wieder berichtigen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);

                String InstallationMethod = myPrefs.getString("InstallationMode","");
                Boolean FABDirectInstall = myPrefs.getBoolean("FABInstallAllOverlays",true);

                //Just copy Overlays
                if (InstallationMethod.equals("Just copy Overlays")) {

                    MoveDialog();

                } else {

                    //Direct Installation
                    if (InstallationMethod.equals("Direct installation")) {

                        //FAB INSTALL ALL
                        if (FABDirectInstall) {

                            installAllOverlays();


                        }
                        else {
                            selectOverlaysFirstSnackbar();
                        }
                    } else {

                        if (InstallationMethod.equals("Ask before every installation")) {

                            new AlertDialog.Builder(this)
                                    .setTitle(R.string.ChoseInstallationMode)
                                    .setMessage(R.string.ChoseInstallationMode2)
                                    .setPositiveButton(R.string.CopyOverlays, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            MoveDialog();

                                        }
                                    })
                                    .setNegativeButton(R.string.InstallOverlays, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            selectOverlaysFirstSnackbar();

                                        }
                                    })
                                    .show();
                        }
                    }
                }

            } else {

            //when a color checkbox is checked
                if (NumberOfSelectedColorOverlays != 0 || NumberOfSelectedaAdditionalColorOverlays !=0) {

                    SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);


                    final String InstallationMethod = myPrefs.getString("InstallationMode","");

                    if (InstallationMethod.equals("Ask before every installation")) {

                        AskBeforeEveryInstallationDialog("Color");

                    }
                    else {
                        if (InstallationMethod.equals("Direct installation")) {

                            colorDialog();

                        }
                    }
                }

                //if only normal Overlays are selected
                else {

                    SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);


                    String InstallationMethod = myPrefs.getString("InstallationMode","");

                    if (InstallationMethod.equals("Direct installation")) {

                        installDialog();

                    } else {
                        if (InstallationMethod.equals("Just copy Overlays")) {

                            MoveDialog();

                        }
                        else {
                            if (InstallationMethod.equals("Ask before every installation")) {

                                AskBeforeEveryInstallationDialog("Normal");

                            }
                        }
                    }
                }
            }
        }







        //Preperation of Installation Of Overlays
        protected void InstallOverlays() throws InterruptedException {
        String sSUCommand;
        String sSuCommand2;



            //install Normal Overlays
            for (int i=0; i <NumberOfOverlays; i++){

                if (InstallOverlay[i] == 1) {
                    AppPath = OverlayPath[i];
                    InstallOverlay[i] = 0;
                    sSUCommand = "cp " + ThemeFolderGeneral + AppPath + " " + TargetPath;
                    sSuCommand2 = "chmod 666 " + TargetPath + AppPath;
                    installAPK(sSUCommand, sSuCommand2);
                }
            }


            //install Additional Overlays
            for (int i = 0; i<NumberOfOverlays+NumberOfColorOverlays+1; i++){
                if (NumberOfAdditionalOverlays[i]>0){
                    for (int e=1; e< NumberOfAdditionalOverlays[i]+2;e++){
                        if (InstallAdditionalOverlays[i][e]==1){
                            AppPath = AdditionalOverlayPath[i][e];
                            sSUCommand = "cp " + ThemeFolderGeneral + AppPath + " " + TargetPath;
                            sSuCommand2 = "chmod 666 " + TargetPath + AppPath;
                            installAPK(sSUCommand, sSuCommand2);

                            sSUCommand = "cp " + ThemeFolder + whichColor + "/"+ AppPath + " " +  TargetPath;
                            sSuCommand2 = "chmod 666 " + TargetPath +AppPath;
                            installAPK(sSUCommand, sSuCommand2);
                        }
                    }
                }
            }


            //install Color Specific Overlays
            for (int i4=NumberOfOverlays+1; i4 <NumberOfOverlays+NumberOfColorOverlays+1; i4++){

                if (InstallOverlay[i4] == 1) {
                    AppPath = OverlayPath[i4];
                    InstallOverlay[i4] = 0;
                    sSUCommand = "cp " + ThemeFolder + whichColor + "/"+ AppPath + " " +  TargetPath;
                    sSuCommand2 = "chmod 666 " + TargetPath +AppPath;
                    installAPK(sSUCommand, sSuCommand2);
                }
            }
        }








        //Installation of Overlays
        public void installAPK(String sSUCommand, String sSuCommand2) throws InterruptedException {

            RootCommandsInstallationHelper cls2= new RootCommandsInstallationHelper();
            cls2.installOverlays(sSUCommand, sSuCommand2);

        }











        private boolean isFirstTime2(){
            SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);

            boolean ranBefore = myPrefs.getBoolean("RanBefore2", false);
            if (!ranBefore) {

            }
            return !ranBefore;
        }

        private boolean showInstallationConfirmDialog(){
            SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);

            boolean showInstallationConfirmDialog = myPrefs.getBoolean("ConfirmInstallationDialog", true);
            return showInstallationConfirmDialog;
        }







        private void CopyFolderToSDCard() {
            CopyUnzipHelper cls2= new CopyUnzipHelper();
            cls2.CopyFolderToSDCard(MainActivity.this, ThemeName);
        }




        //unzip ..... the zip files :DD
        public void unzip() {

            int NumberOfSelectedNormalOverlays = 0;
            for (int i = 0; i < NumberOfOverlays; i++)
            {
                NumberOfSelectedNormalOverlays = NumberOfSelectedNormalOverlays + InstallOverlay[i];
            }


            for (int i = 0; i<NumberOfOverlays; i++){
                for (int e=1; e< NumberOfAdditionalOverlays[i]+2;e++){
                    NumberOfSelectedNormalOverlays =NumberOfSelectedNormalOverlays + InstallAdditionalOverlays[i][e];
                }
            }

            int NumberOfSelectedColorOverlays = 0;
            for (int i = NumberOfOverlays+1; i < NumberOfColorOverlays+NumberOfOverlays+1; i++)
            {
                NumberOfSelectedColorOverlays = NumberOfSelectedColorOverlays + InstallOverlay[i];
            }

            int NumberOfSelectedAdditionalOveerlays = 0;
            for (int i= 0; i < NumberOfOverlays+NumberOfColorOverlays+1;i++){
                for (int e=0; e< NumberOfAdditionalOverlays[i]+1;e++){
                    NumberOfSelectedAdditionalOveerlays = NumberOfSelectedAdditionalOveerlays + InstallAdditionalOverlays[i][e+1];
                }
            }


            CopyUnzipHelper cls2= new CopyUnzipHelper();
            cls2.unzip(ThemeName, NumberOfSelectedNormalOverlays, NumberOfSelectedColorOverlays, whichColor,NumberOfSelectedAdditionalOveerlays);

        }





        private void installAllOverlays(){

            for (int i = 0; i < NumberOfOverlays+NumberOfColorOverlays+1; i++)
            {
                InstallOverlay[i] = 1;
            }


            for (int i3 = 0; i3<NumberOfOverlays+NumberOfColorOverlays+1; i3++) {

                for (int e = 0; e < NumberOfAdditionalOverlays[i3] + 1; e++) {
                    InstallAdditionalOverlays[i3][e + 1] = 1;
                    if (DontInstallOnInstallAll[i3][e+1]){
                        InstallAdditionalOverlays[i3][e + 1] = 0;
                    }
                    if (AdditionalDialogMode[i3] == 1) {
                        InstallAdditionalOverlays[i3][NumberOfAdditionalOverlays[i3]] = 1;
                        for (int f = 1; f < NumberOfAdditionalOverlays[i3]; f++) {
                            InstallAdditionalOverlays[i3][f] = 0;
                        }
                    }
                }
            }
            if (NumberOfColorOverlays>0) {
                colorDialog();
            }else{
                installDialog();
            }
        }





        private void UncheckAllCheckBoxes(String Mode) {
            final android.support.design.widget.FloatingActionButton fab2 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab2);

            if (Mode.equals("Uncheck")) {

                fab2.setVisibility(View.INVISIBLE);

                for (int i = 0; i < NumberOfOverlays; i++){
                    CheckBox checkBox = (CheckBox)findViewById(i);
                    checkBox.setChecked(false);
                    InstallOverlay[i] = 0;
                }

                for (int i = NumberOfOverlays+1; i < NumberOfColorOverlays+NumberOfOverlays+1; i++){
                    CheckBox checkBox = (CheckBox)findViewById(i);
                    checkBox.setChecked(false);
                    InstallOverlay[i] = 0;
                }

                for (int i3 = 0; i3<NumberOfOverlays+NumberOfColorOverlays+1; i3++){
                    for (int e=0; e< NumberOfAdditionalOverlays[i3]+1;e++){

                           InstallAdditionalOverlays[i3][e+1] = 0;

                    }
                }

            }




            if (Mode.equals("Disable")){
                //disable all Checkboxes
                for (int i = 0; i < NumberOfOverlays; i++){
                    CheckBox checkBox = (CheckBox)findViewById(i);
                    checkBox.setEnabled(false);
                }

                for (int i = NumberOfOverlays+1; i < NumberOfColorOverlays+NumberOfOverlays+1; i++)
                {
                    CheckBox checkBox = (CheckBox)findViewById(i);
                    checkBox.setEnabled(false);
                }
            }
        }

























        ///////////////////////////////////////////
        //Dialog Methods
        //////////////////////////////////////////



        //Delete Overlays? How many?
        private  void DeleteOverlaysDialog(){
            new AlertDialog.Builder(this)
                .setTitle(R.string.SelectDeleteMode)
                .setMessage(R.string.deleteModeAbout)
                .setPositiveButton(R.string.deleteModeOne, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.lovejoy777.rroandlayersmanager", "com.lovejoy777.rroandlayersmanager.deleters.CardDeleteLayers"));  //open Layers Manager Delete Activity
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.deleteModeAll, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        (new Delete()).execute(); //start Async Task to delete all Overlays
                    }
                })
                .show();
        }



        //Dialog to choose color
        public void colorDialog() {


            final AlertDialog.Builder colorDialog = new AlertDialog.Builder(MainActivity.this);
            final LayoutInflater inflater = this.getLayoutInflater();
            colorDialog.setTitle(R.string.pick_color);
            View colordialogView = inflater.inflate(R.layout.dialog_colors, null);
            colorDialog.setView(colordialogView);

            for (int i = 0; i < NumberOfColors; i++) {

                RadioGroup my_layout = (RadioGroup) colordialogView.findViewById(R.id.radiogroup);

                RadioGroup.LayoutParams params
                    = new RadioGroup.LayoutParams(MainActivity.this, null);

                params.leftMargin= 66;
                params.topMargin= 2;
                params.bottomMargin= 2;
                params.width= RadioGroup.LayoutParams.FILL_PARENT;

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());
                params.height=height;


                RadioButton radioButton = new RadioButton(this);

                radioButton.setText(Color[i]);
                radioButton.setId(i);
                radioButton.setTag("r"+i);
                radioButton.setLayoutParams(params);
                radioButton.setTextSize(18);

                my_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                    whichColor = Color[checkedId];
                    }
                });

                SharedPreferences myPrefs = MainActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = myPrefs.edit();
                int which = myPrefs.getInt("ColorDialogRadioButton",0);
                if (i==which){
                    radioButton.setChecked(true);
                }
                my_layout.addView(radioButton);
            }

            colorDialog.setCancelable(false);
            colorDialog.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int whichRadioButton = 0;
                SharedPreferences myPrefs = MainActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
                for (int e = 0; e < NumberOfColors; e++){
                    if (whichColor.equals(Color[e])){
                        whichRadioButton = e;

                    }
                }
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putInt("ColorDialogRadioButton", whichRadioButton);
                editor.commit();
                installDialog();
            }
            });
            colorDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                UncheckAllCheckBoxes("Uncheck");
                dialog.dismiss();
            }


            });
            colorDialog.create();
            colorDialog.show();
        }



    //Dialog to choose additional SystemUI overlays
    public void AdditionalOverlaysDialog(final int whichCheckbox) {

        LayoutInflater inflater = this.getLayoutInflater();



        //Radio Button Dialog
        if (AdditionalDialogMode[whichCheckbox]==1){



           final View dialogView = inflater.inflate(R.layout.dialog_additionalradioboxes, null);

            for (int i = 0; i < NumberOfAdditionalOverlays[whichCheckbox]; i++) {


                RadioGroup my_layout = (RadioGroup) dialogView.findViewById(R.id.radioGroupAdditional);

                RadioGroup.LayoutParams params
                        = new RadioGroup.LayoutParams(MainActivity.this, null);

                params.leftMargin = 66;
                params.topMargin = 2;
                params.bottomMargin = 2;
                params.width = RadioGroup.LayoutParams.FILL_PARENT;

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());
                params.height = height;


                RadioButton radioButton = new RadioButton(MainActivity.this);

                radioButton.setText(AdditionalOverlayName[whichCheckbox][i + 1]);
                radioButton.setId(i);
                radioButton.setTag("c" + i);
                radioButton.setLayoutParams(params);
                radioButton.setTextSize(18);

                int which =0;
                if (which ==i){
                    radioButton.setChecked(true);
                }

                my_layout.addView(radioButton);

                my_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        {

                            whichRadioButton = checkedId + 1;


                        }
                    }
                });
            }



            final AlertDialog d = new AlertDialog.Builder(MainActivity.this)
                    .setView(dialogView)
                    .setTitle("Choose " + OverlayName[whichCheckbox] + " Overlay")
                    .setPositiveButton(R.string.okay, null)
                    .setCancelable(false)
                    .create();



            d.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            if (whichRadioButton>0) {
                                InstallAdditionalOverlays[whichCheckbox][whichRadioButton]=1;
                                d.dismiss();
                            }
                        }
                    });

                }

            });
            d.show();
        }



            //Checkbox Dialog
            else {


            atleastone = 0;

            View dialogView = inflater.inflate(R.layout.dialog_additionalcheckboxes, null);

            for (int i = 0; i < NumberOfAdditionalOverlays[whichCheckbox]; i++) {

                //Dynamically add Checkboxes to select normal Overlays
                LinearLayout my_layout = (LinearLayout)dialogView.findViewById(R.id.linearDialog2);




                TableRow row =new TableRow(this);
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,    TableRow.LayoutParams.WRAP_CONTENT);
                params.leftMargin= 66;
                params.topMargin= 20;
                params.bottomMargin= 16;
                row.setLayoutParams(params);


                final CheckBox[] check = new CheckBox[NumberOfOverlays];
                check[i]= new CheckBox(this);
                check[i].setText(AdditionalOverlayName[whichCheckbox][i + 1]);
                check[i].setTag("c" + i);
                check[i].setId(i);
                check[i].setTextSize(18);

                check[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    //check if checkbox is clicked
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView2, boolean isChecked) {

                        for (int c = 0; c < NumberOfAdditionalOverlays[whichCheckbox] + 1; c++) {
                            int sum = c + 1;
                            if (buttonView2.getTag().equals("c" + c)) {
                                if (buttonView2.isChecked()) {
                                    atleastone = atleastone + 1;
                                    InstallAdditionalOverlays[whichCheckbox][sum] = 1;

                                } else {
                                    atleastone = atleastone - 1;
                                    InstallAdditionalOverlays[whichCheckbox][sum] = 0;
                                    InstallOverlay[c] = 0;
                                }

                            }
                        }
                    }
                });
                row.addView(check[i]);
                my_layout.addView(row);
            }


            final AlertDialog d = new AlertDialog.Builder(MainActivity.this)
                    .setView(dialogView)
                    .setTitle("Choose parts of " + OverlayName[whichCheckbox])
                    .setPositiveButton(R.string.okay, null)
                    .setCancelable(false)
                    .create();

            d.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            if (atleastone > 0) {

                                d.dismiss();
                            }
                        }
                    });
                }
            });
            d.show();
        }
    }


    //Dialog to choose installation mode
    private void AskBeforeEveryInstallationDialog(final String overlayType) {
        showInstallationConfirmDialog();

            new AlertDialog.Builder(this)
                    .setTitle(R.string.ChoseInstallationMode)
                    .setMessage(R.string.ChoseInstallationMode2)
                    .setPositiveButton(R.string.CopyOverlays, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //start async task to install the Overlays
                            MoveDialog();

                        }
                    })
                    .setNegativeButton(R.string.InstallOverlays, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (overlayType.equals("Color")) {

                                colorDialog();

                            } else if (overlayType.equals("Normal")) {

                                installDialog();

                            }
                        }
                    })
                    .show();
        }




    // Dialog which asks the user if he really wants to install the overlays
    public void installDialog() {

        if (showInstallationConfirmDialog()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.MoveOverlays)
                    .setMessage(R.string.CreateHeXFolder)
                    .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //start async task to install the Overlays
                            (new InstallOverlays()).execute();
                        }
                    })
                    .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UncheckAllCheckBoxes("Uncheck");
                        }
                    })
                    .show();
        }else{
            (new InstallOverlays()).execute();
        }
    }




    public void MoveDialog() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.CopyMoveOverlays)
                .setMessage(R.string.MoveOverlaystoSdCard)
                .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //start async task to install the Overlays
                        (new MoveOverlays()).execute();
                    }
                })
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }



    public void showChangelog(View view) {

        new AlertDialog.Builder(this)
                .setTitle(R.string.Changelog)
                .setView(LayoutInflater.from(this).inflate(R.layout.main_whatsnewdialog, null))
                .setPositiveButton(R.string.VisitCommunity, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ProjectsProgress)));
                    }
                })
                .setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }




    /////////////////////////////////////////
    //SnackBar Methods
    ////////////////////////////////////////

    private void moveFinishedSnackbar() {

        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.MoveFinished, Snackbar.LENGTH_LONG)
                .setAction(R.string.Layers, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.lovejoy777.rroandlayersmanager", "com.lovejoy777.rroandlayersmanager.LayersChooser")); //open install Activity of Layers Manager
                        startActivity(intent);
                    }
                })
                .show();
    }


    private void selectOverlaysFirstSnackbar() {

        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.selectOverlayFirst, Snackbar.LENGTH_SHORT)
                .show();
    }



    private void installationFinishedSnackBar() {

        //show SnackBar after sucessfull installation of the overlays
        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.OverlaysInstalled, Snackbar.LENGTH_LONG)
                .setAction(R.string.Reboot, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (new Reboot()).execute();
                    }
                })
                .show();
    }















//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////ASYNC TASKS//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Async Task to Install Overlays///////////////////////////////////////////////////////////////
    private class InstallOverlays extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress2;


        protected void onPreExecute() {

            progress2 = ProgressDialog.show(MainActivity.this, "Install overlays",
                    "Installing...", true);
        }



        @Override
        protected Void doInBackground(Void... params) {

            //Mount System to Read / Write
            RootTools.remount("/system/", "RW");


            //initialize last part of root Commands
            String SuperuserCommandOverlayFolderPermission = (String) "chmod 777 /vendor/overlay";
            String SuperuserCommandCreateOverlayFolder = "mkdir /vendor/overlay";




            CommandCapture command4 = new CommandCapture(0, SuperuserCommandCreateOverlayFolder);
            try {
                RootTools.getShell(true).add(command4);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (RootDeniedException e) {
                e.printStackTrace();
            }
            while (!command4.isFinished()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            CopyFolderToSDCard();  //copy Overlay Files to SD Card


            unzip();  //unzip Overlay ZIP´s


            try {
                InstallOverlays();  //open Method to install Overlays
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            CommandCapture command3 = new CommandCapture(0, SuperuserCommandOverlayFolderPermission);
            try {
                RootTools.getShell(true).add(command3);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (RootDeniedException e) {
                e.printStackTrace();
            }
            while (!command3.isFinished()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            RootTools.remount("/system/", "RO");  //remount /system back to RO

            return null;

        }


        protected void onPostExecute(Void result) {

            UncheckAllCheckBoxes("Uncheck");


            progress2.dismiss();
            installationFinishedSnackBar(); //show snackbar with option to reboot

        }
    }




    private class MoveOverlays extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress2;


        protected void onPreExecute() {

            progress2 = ProgressDialog.show(MainActivity.this, "Moving Overlays",
                    "Moving...", true);
        }



        @Override
        protected Void doInBackground(Void... params) {


            CopyFolderToSDCard();  //copy Overlay Files to SD Card
            return null;
        }


        protected void onPostExecute(Void result) {


            progress2.dismiss();
            moveFinishedSnackbar(); //show snackbar with option to reboot

        }
    }











    //Async Task to reboot device///////////////////////////////////////////////////////////////////
    private class Reboot extends AsyncTask<Void, Void, Void> {
        final ProgressDialog progressDialogReboot = new ProgressDialog(MainActivity.this);


        protected void onPreExecute() {
            //progressDialog rebooting / 10 seconds
            progressDialogReboot.setTitle(R.string.rebooting);
            progressDialogReboot.setMessage("Rebooting in 10 seconds...");
            progressDialogReboot.setCanceledOnTouchOutside(false);
            progressDialogReboot.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                //when Cancel Button is clicked
                @Override
                public  void onClick(DialogInterface dialog, int which) {
                    Reboot.this.cancel(true);
                    dialog.dismiss();
                }
            });

            progressDialogReboot.setButton(DialogInterface.BUTTON_POSITIVE, "Reboot Now", new DialogInterface.OnClickListener() {
                //when Cancel Button is clicked
                @Override
                public  void onClick(DialogInterface dialog, int which) {
                    try {
                        Process proc = Runtime.getRuntime()
                                .exec(new String[]{"su", "-c", "busybox killall system_server"});
                    } catch (IOException e) {
                        e.printStackTrace();
                    };
                    dialog.dismiss();
                }
            });
            progressDialogReboot.show();
        }



        //wait 10 seconds to reboot
        @Override
        protected Void doInBackground(Void... params) {
            //wait 10 seconds
            int i = 0;
            while (i<10) {
                i++;
                //cancel AsyncTask if Cancel Button is pressed
                if (isCancelled()) {
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }



        protected void onPostExecute(Void result) {
            //close Dialog
            progressDialogReboot.dismiss();

            //softreboot phone
            try {
                Process proc = Runtime.getRuntime()
                        .exec(new String[]{"su", "-c", "busybox killall system_server"});
            } catch (IOException e) {
                e.printStackTrace();
            };
        }
    }











    //Async Task to deinstall Overlays//////////////////////////////////////////////////////////////
    private class Delete extends AsyncTask<Void, Void, Void> {
        final ProgressDialog progressDialogDelete = new ProgressDialog(MainActivity.this);


        protected void onPreExecute() {
            //progressDialog rebooting / 10 seconds
            progressDialogDelete.setTitle("Deleting");
            progressDialogDelete.setMessage("Deleting all Overlays...");
            progressDialogDelete.setCanceledOnTouchOutside(false);
            progressDialogDelete.show();
        }


        //delete Overlays Folder
        @Override
        protected Void doInBackground(Void... params) {


            RootTools.remount("/system", "RW");
            RootTools.deleteFileOrDirectory("/system/vendor/overlay", true);
            RootTools.remount("/system", "RO");
            return null;
        }


        protected void onPostExecute(Void result) {
            //close Dialog
            progressDialogDelete.dismiss();

            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.RemovedOverays, Snackbar.LENGTH_SHORT)
                    .show();

        }
    }






    //Glide Methods
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this)
                .load(R.drawable.heroimage)
                .centerCrop()
                .into(imageView);
    }

    private void loadScreenshots() {
        for (int i = 0; i< NumberOfScreenshotsMain;i++){
            Glide.with(this)
                    .load(GalImages[i])
                    .asBitmap()
                    .into(new BitmapImageViewTarget(imageView[i]) {
                        @Override
                        protected void setResource(Bitmap resource) {


                            Bitmap resized = Bitmap.createScaledBitmap(resource,(int)(resource.getWidth()*0.3), (int)(resource.getHeight()*0.3), true);

                            super.setResource(resized);
                        }
                    });
        }
    }

}

