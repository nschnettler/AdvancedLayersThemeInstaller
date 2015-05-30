package com.schnettler.AdvancedLayersThemeInstaller.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.schnettler.AdvancedLayersThemeInstaller.R;
import com.schnettler.AdvancedLayersThemeInstaller.adapters.CustomListAdapter;


public class AboutActivity extends Activity {
    ListView list1, list2, list3, list4, list5;
    ImageButton moreButton1, moreButton2, moreButton3;

    Integer[] listImage2 = {
            R.drawable.niklas,
    };
    Integer[] listImage3 = {
            R.drawable.bitsyko,
    };
    Integer[] listImage1 = {
            R.drawable.themedeveloper,
    };
    Integer[] listImage4 = {
            R.drawable.mailson,
    };
    Integer[] listImage5 = {
            R.drawable.stefano,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String[] ListContent2 = {
                this.getString(R.string.NameOfAppDeveloper)
        } ;
        String[] ListContent3 = {
                this.getString(R.string.ThanksTo1)
        } ;
        String[] ListContent4 = {
                this.getString(R.string.ThanksTo2)
        } ;
        String[] ListContent5 = {
                this.getString(R.string.ThanksTo3)
        } ;

        String[] ListContent1 = {
                this.getString(R.string.NameOfThemeDeveloper)
        };


        //set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setActionBar(toolbar);


        //List 1 //Theme Developer
        CustomListAdapter adapter3 = new CustomListAdapter(AboutActivity.this, ListContent1, listImage1);
        list1=(ListView)findViewById(R.id.listView_ThemeDeveloper);
        list1.setAdapter(adapter3);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.LinkToYourProfile))));
            }
        });

        //List 2 //App Developer // Niklas Schnettler
        CustomListAdapter adapter = new CustomListAdapter(AboutActivity.this, ListContent2, listImage2);
        list2=(ListView)findViewById(R.id.listView_AppDeveloper);
        list2.setAdapter(adapter);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.AppDeveloperGooglePlus))));
            }
        });


        //List3 //Thanks To 1 //Bitsyko Development Team
        CustomListAdapter adapter2 = new
                CustomListAdapter(AboutActivity.this, ListContent3, listImage3);
        list3=(ListView)findViewById(R.id.listView_ThanksTo1);
        list3.setAdapter(adapter2);
        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.Thanksto1GooglePlus))));
            }
        });

        //List4 //Thanks to 2 //Mailson Campos
        CustomListAdapter adapter4 = new
                CustomListAdapter(AboutActivity.this, ListContent4, listImage4);
        list4=(ListView)findViewById(R.id.listView_ThanksTo2);
        list4.setAdapter(adapter4);
        list4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.Thanksto2GooglePlus))));
            }
        });

        //List5 //Thanks to 3 //Stefano Trevisani
        CustomListAdapter adapter5 = new
                CustomListAdapter(AboutActivity.this, ListContent5, listImage5);
        list5=(ListView)findViewById(R.id.listView_ThanksTo3);
        list5.setAdapter(adapter5);
        list5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.Thanksto3GooglePlus))));
            }
        });


        //app version textView
        TextView tv_version = (TextView) findViewById(R.id.tv_Version);
        try {
            String versionName = AboutActivity.this.getPackageManager()
                    .getPackageInfo(AboutActivity.this.getPackageName(), 0).versionName;
            tv_version.setText("Version " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //i Buttons
        moreButton1 = (ImageButton) findViewById(R.id.imBu_more1);
        moreButton2 = (ImageButton) findViewById(R.id.imBu_more2);
        moreButton3 = (ImageButton) findViewById(R.id.imBu_more3);
        moreButton1.setOnClickListener(onclicklistener);
        moreButton2.setOnClickListener(onclicklistener);
        moreButton3.setOnClickListener(onclicklistener);



    }

    View.OnClickListener onclicklistener = new View.OnClickListener() {
        public void onClick(View v) {
            String dialogText = "";
            String dialogTitleText = "";
            LayoutInflater li = LayoutInflater.from(AboutActivity.this);
            View view3 = li.inflate(R.layout.dialog_license, null);
            final TextView tv_license = (TextView) view3.findViewById(R.id.tv_license);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(AboutActivity.this);
            dialog.setView(view3);
            switch(v.getId()) {
                case R.id.imBu_more1:
                    dialogText = getResources().getString(R.string.License1more);
                    dialogTitleText = getResources().getString(R.string.License1);
                    dialog.setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.License1github)));
                            startActivity(browserIntent);
                        }
                    });
                    break;
                case R.id.imBu_more2:
                    dialogText = getResources().getString(R.string.License2more);
                    dialogTitleText = getResources().getString(R.string.License2);
                    dialog.setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.License2github)));
                            startActivity(browserIntent);
                        }
                    });
                    break;
                case R.id.imBu_more3:
                    dialogText = getResources().getString(R.string.License3more);
                    dialogTitleText = getResources().getString(R.string.License3);
                    dialog.setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.License3github)));
                            startActivity(browserIntent);
                        }
                    });
                    break;
            }
            dialog.setTitle(dialogTitleText);
            tv_license.setText(dialogText);
            dialog.setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
        }
    };

    public void openCommunity(View view) {
        // Do something in response to button
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.community))));

    }
}

