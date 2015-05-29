package com.schnettler.AdvancedLayersThemeInstaller.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.schnettler.AdvancedLayersThemeInstaller.R;
import com.schnettler.AdvancedLayersThemeInstaller.adapters.CustomListAdapter;


public class AboutActivity extends Activity {
    ListView list;
    ListView list2;
    ListView list3;
    ListView list4;
    ListView list5;
    String community = null;
    String LinkToYourProfile = null;
    String[] web1 = {
            "Niklas Schnettler",
    } ;
    String[] web2 = {
            "Bitsyko Development Team",
    } ;
    String[] web4 = {
            "Mailson Campos",
    } ;
    String[] web5 = {
            "Stefano Trevisani",
    } ;

    Integer[] imageId1 = {
            R.drawable.niklas,
    };
    Integer[] imageId2 = {
            R.drawable.bitsyko,
    };
    Integer[] imageId3 = {
            R.drawable.themedeveloper,
    };
    Integer[] imageId4 = {
            R.drawable.mailson,
    };
    Integer[] imageId5 = {
            R.drawable.stefano,
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String test = this.getString(R.string.NameOfThemeDeveloper);

        community = this.getString(R.string.community);
        LinkToYourProfile = this.getString(R.string.LinkToYourProfile);
        String[] web3 = {
                test,
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setActionBar(toolbar);

        //List 2
        CustomListAdapter adapter = new
                CustomListAdapter(AboutActivity.this, web1, imageId1);
        list=(ListView)findViewById(R.id.listView7);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+NiklasSchnettler/posts")));
            }
        });


        //List 1
        CustomListAdapter adapter3 = new
                CustomListAdapter(AboutActivity.this, web3, imageId3);
        list3=(ListView)findViewById(R.id.listView6);
        list3.setAdapter(adapter3);

        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LinkToYourProfile)));
            }
        });

        //List3
        CustomListAdapter adapter2 = new
                CustomListAdapter(AboutActivity.this, web2, imageId2);
        list2=(ListView)findViewById(R.id.listView2);
        list2.setAdapter(adapter2);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/communities/102261717366580091389")));
            }
        });

        //List4
        CustomListAdapter adapter4 = new
                CustomListAdapter(AboutActivity.this, web4, imageId4);
        list4=(ListView)findViewById(R.id.listView4);
        list4.setAdapter(adapter4);
        list4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/+MailsonCampos/posts")));
            }
        });

        //List5
        CustomListAdapter adapter5 = new
                CustomListAdapter(AboutActivity.this, web5, imageId5);
        list5=(ListView)findViewById(R.id.listView5);
        list5.setAdapter(adapter5);
        list5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+StefanoTrevisani/posts")));
            }
        });






        TextView tv_version = (TextView) findViewById(R.id.textView_version);
        try {
            String versionName = AboutActivity.this.getPackageManager()
                    .getPackageInfo(AboutActivity.this.getPackageName(), 0).versionName;
            tv_version.setText("Version " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }




        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(community)));
            }
        });


    }




    public void License1(View view) {

        LayoutInflater li = LayoutInflater.from(AboutActivity.this);
        View view3 = li.inflate(R.layout.dialog_license, null);
        final TextView tv_license = (TextView) view3.findViewById(R.id.tv_license);
        tv_license.setText(R.string.License1more);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(AboutActivity.this);
                dialog.setTitle(R.string.License1);
                dialog.setView(view3);
                dialog.setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.License1github)));
                        startActivity(browserIntent);
                    }
                });

                dialog.setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
    }

    public void License2(View view) {

        LayoutInflater li = LayoutInflater.from(AboutActivity.this);
        View view3 = li.inflate(R.layout.dialog_license, null);
        final TextView tv_license = (TextView) view3.findViewById(R.id.tv_license);
        tv_license.setText(R.string.License2more);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(AboutActivity.this);
        dialog.setTitle(R.string.License2);
        dialog.setView(view3);
        dialog.setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.License2github)));
                startActivity(browserIntent);
            }
        });

        dialog.setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }


    public void License3(View view) {

        LayoutInflater li = LayoutInflater.from(AboutActivity.this);
        View view3 = li.inflate(R.layout.dialog_license, null);
        final TextView tv_license = (TextView) view3.findViewById(R.id.tv_license);
        tv_license.setText(R.string.License3more);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(AboutActivity.this);
        dialog.setTitle(R.string.License3);
        dialog.setView(view3);
        dialog.setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.License3github)));
                startActivity(browserIntent);
            }
        });

        dialog.setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    public void openCommunity(View view) {
        // Do something in response to button
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(community)));

    }







}

