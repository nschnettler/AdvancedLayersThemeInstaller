package com.schnettler.hex_layerstheme.activities;

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

import com.schnettler.hex_layerstheme.adapters.CustomListAdapter;
import com.schnettler.hex_layerstheme.R;


public class AboutActivity extends Activity {
    ListView list;
    ListView list2;
    ListView list3;
    ListView list4;
    String community = null;
    String LinkToYourProfile = null;
    String[] web1 = {
            "Niklas Schnettler",

    } ;

    String[] web4 = {
            "Mailson Campos",

    } ;

    Integer[] imageId1 = {
            R.drawable.niklas,

    };

    String[] web2 = {
            "Bitsyko Development Team",

    } ;
    Integer[] imageId2 = {
            R.drawable.bitsyko,

    };


    Integer[] imageId3 = {
            R.drawable.themedeveloper,

    };

    Integer[] imageId4 = {
            R.drawable.mailson,

    };
    //String web3 = "Just Install Overlays";

    //String imageId3 = "Test";



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


    //@Override
    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_install_dialog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/


    public void License1(View view) {
        // Do something in response to button
        //Intent intent = new Intent(this, About.class);



        new AlertDialog.Builder(this)
                .setTitle("Snackbar")
                .setView(LayoutInflater.from(this).inflate(R.layout.about_license1dialog, null))
                .setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nispok/snackbar"));
                        startActivity(browserIntent);

                    }
                })

                .setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {





                    }
                })

                .show();

    }











    public void License3(View view) {
        // Do something in response to button
        //Intent intent = new Intent(this, About.class);

        new AlertDialog.Builder(this)
                .setTitle("ObservableScrollView")
                .setView(LayoutInflater.from(this).inflate(R.layout.about_license2dialog, null))
                .setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ksoichiro/Android-ObservableScrollView"));
                        startActivity(browserIntent);
                    }
                })

                .setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void License4(View view) {
        // Do something in response to button
        //Intent intent = new Intent(this, About.class);
        new AlertDialog.Builder(this)
                .setTitle("Floating Action Button")
                .setView(LayoutInflater.from(this).inflate(R.layout.about_license3dialog, null))
                .setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/makovkastar/FloatingActionButton"));
                        startActivity(browserIntent);
                    }
                })

                .setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


    public void License5(View view) {
        // Do something in response to button
        //Intent intent = new Intent(this, About.class);
        new AlertDialog.Builder(this)
                .setTitle("NineOldAndroids")
                .setView(LayoutInflater.from(this).inflate(R.layout.about_license4dialog, null))
                .setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JakeWharton/NineOldAndroids/"));
                        startActivity(browserIntent);
                    }
                })

                .setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


    public void License6(View view) {
        // Do something in response to button
        //Intent intent = new Intent(this, About.class);
        new AlertDialog.Builder(this)
                .setTitle("Root Tools")
                .setView(LayoutInflater.from(this).inflate(R.layout.about_license5dialog, null))
                .setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Stericson/RootTools"));
                        startActivity(browserIntent);
                    }
                })

                .setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void License9(View view) {
        // Do something in response to button
        //Intent intent = new Intent(this, About.class);
        new AlertDialog.Builder(this)
                .setTitle("ViewPagerIndicator")
                .setView(LayoutInflater.from(this).inflate(R.layout.about_license6dialog, null))
                .setPositiveButton(R.string.VisitGithub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JakeWharton/ViewPagerIndicator"));
                        startActivity(browserIntent);
                    }
                })

                .setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void GPlusNiklas(View view) {
        // Do something in response to button
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+NiklasSchnettler/posts")));

    }

    public void GPlusBitSyko(View view) {
        // Do something in response to button
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/communities/102261717366580091389")));

    }

    public void BitSykoWebsite(View view) {
        // Do something in response to button
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bitsyko.com/")));

    }

    public void XDABitSyko(View view) {
        // Do something in response to button
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/android/apps-games/official-layers-bitsyko-apps-rro-t3012172")));

    }

    public void openCommunity(View view) {
        // Do something in response to button
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(community)));

    }







}

