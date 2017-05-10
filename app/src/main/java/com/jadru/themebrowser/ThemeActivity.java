package com.jadru.themebrowser;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class ThemeActivity extends AppCompatActivity {
    private GridviewAdapter mAdapter;

    private List<String> tm;
    private GridView gridView;
    boolean buyornot = false;
    SharedPreferences pref;
    SharedPreferences prefsdef;
    CoordinatorLayout bgg;
    View view;
    Toolbar toolbar;
    ImageView event_img;
    ArrayList<String> eventinfo;
    String language;
    CollapsingToolbarLayout appbar;
    String serverurl = "http://yg.10g.kr/jadru/themebrowser/themes/";
    EditText tagsearch;
    FloatingActionButton fab;
    ProgressBar progresstheme;
    AlertDialog alerttt = null;
    String urL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(android.R.id.content).getRootView();
        bgg = (CoordinatorLayout)findViewById(R.id.bgg);
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        prefsdef = PreferenceManager.getDefaultSharedPreferences(this);
        event_img = (ImageView)findViewById(R.id.event_img);
        appbar = (CollapsingToolbarLayout)findViewById(R.id.appbar);
        tagsearch = (EditText)findViewById(R.id.tagsearch);
        fab = (FloatingActionButton)findViewById(R.id.btn_tag);
        tagsearch.setVisibility(View.INVISIBLE);
        progresstheme = (ProgressBar)findViewById(R.id.progresstheme);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Locale locale = getResources().getConfiguration().locale;
        language = locale.getLanguage();

        feed();

        int statuscolor = pref.getInt("t-color", Color.parseColor("#4dd0e1"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(statuscolor);
            window.setStatusBarColor(statuscolor);
        }
        toolbar.setBackgroundColor(statuscolor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Boolean blackorwhite = prefsdef.getBoolean("t-black", false);
            if (blackorwhite) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else{
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }

        //fab.setOnClickListener(new Button.OnClickListener() {
            //public void onClick(View v) {
                //editshow(fab);
            //}
        //});
        fab.setVisibility(View.INVISIBLE);
    }

    private void setAdapter(){
        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter();

        // Set custom adapter to gridview
        gridView = (GridView)findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                setthemeonclick(tm,position);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gridView.setNestedScrollingEnabled(true);
        }
        String eventst = eventinfo.get(1);

        Glide.with(ThemeActivity.this)
                .load(serverurl+"img/tevent.jpg")
                .error(R.drawable.errorforloadimage)
                .thumbnail(0.1f)
                .centerCrop()
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .into(event_img);

        String eventtxt;
        if(language.equals("ko")){
            eventtxt = eventst.split(",")[0];
        }else{
            eventtxt = eventst.split(",")[1];
        }
        toolbar.setBackgroundColor(Color.parseColor("#00000000"));
        appbar.setTitle(eventtxt);
        event_img.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                setthemeonclick(eventinfo,2);
            }
        });
    }

    private void setthemeonclick(List<String> themearray,int position){
        final String tmbg = themearray.get(position).split(",")[0];
        final String tmcolor = themearray.get(position).split(",")[1];
        String tmtitle;
        String txttt;
        if(language.equals("ko")){
            tmtitle = themearray.get(position).split(",")[2];
        }else{
            tmtitle = themearray.get(position).split(",")[3];
        }
        if(tmtitle.split(" ").length == 3){
            txttt = "#"+ tmtitle.split(" ")[0] + " " + "#"+ tmtitle.split(" ")[1] + " " + "#"+ tmtitle.split(" ")[2];
        }else if(tmtitle.split(" ").length == 2){
            txttt = "#"+ tmtitle.split(" ")[0] + " " + "#" + tmtitle.split(" ")[1];
        }else{
            txttt = "#"+ tmtitle.split(" ")[0];
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ThemeActivity.this);
        builder.setTitle(getResources().getString(R.string.themesettitle));
        builder.setMessage(getResources().getString(R.string.themesettxt) + "\n" + txttt);
        builder.setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                down( serverurl +"img/" + tmbg + ".jpg");
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("t-color", Color.parseColor("#" + tmcolor));
                editor.commit();
                Log.e("GET ","the image url is " + serverurl + "img" + tmbg + ".jpg");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.parseColor("#" + tmcolor));
                    getWindow().setNavigationBarColor(Color.parseColor("#" + tmcolor));
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.btn_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alerttt.dismiss();
            }
        });
        alerttt = builder.create();
        alerttt.setCanceledOnTouchOutside(false);
        alerttt.show();

    }
    public class GridviewAdapter extends BaseAdapter {

        public int getCount() {
        // TODO Auto-generated method stub
        return tm.size();
    }

    public String getItem(int position) {
        // TODO Auto-generated method stub
        return tm.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = getLayoutInflater();

        String everything = tm.get(position);
        final String tmbg = everything.split(",")[0];
        final String tmcolor = everything.split(",")[1];

        String se = serverurl +"img/" + tmbg + ".jpg";
        if (convertView == null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);

            view.img = (ImageView) convertView.findViewById(R.id.imageView);
            view.rL = (RelativeLayout) convertView.findViewById(R.id.rL);
            view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

       Glide.with(ThemeActivity.this)
                .load(se)
                .placeholder(Color.parseColor("#" + tmcolor))
                .error(R.drawable.errorforloadimage)
                .thumbnail(0.1f)
                .crossFade()
                .centerCrop()
                .into(view.img);
        String txtt;

        String tmtitle;
        if(language.equals("ko")){
            tmtitle = everything.split(",")[2];
        }else{
            tmtitle = everything.split(",")[3];
        }

        if(tmtitle.split(" ").length == 3){
            txtt = "#"+ tmtitle.split(" ")[0] + "\n" + "#"+ tmtitle.split(" ")[1] + "\n" + "#"+ tmtitle.split(" ")[2];
        }else if(tmtitle.split(" ").length == 2){
            txtt = "#"+ tmtitle.split(" ")[0] + "\n" + "#" + tmtitle.split(" ")[1];
        }else{
            txtt = "#"+ tmtitle.split(" ")[0];
        }

        view.txtViewTitle.setText(txtt);

        return convertView;

    }
    }

    private class ViewHolder {
        public TextView txtViewTitle;
        public ImageView img;
        public RelativeLayout rL;
    }

    private void feed()
    {
        new getListArrayfromWeb().execute(null,null,null);
    }

    private void down(String urll)
    {
        new downloaddFile().execute(null,null,null);
        urL = urll;
    }

    private class getListArrayfromWeb extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                tm = new ArrayList<String>();
                BufferedReader in1 = new BufferedReader(new InputStreamReader(new URL(
                        serverurl + "theme.txt").openStream()));
                String s1;
                while ((s1 = in1.readLine()) != null) {
                    tm.add(s1);
                }
                in1.close();
                tm.remove(0);
                Collections.shuffle(tm);

                eventinfo = new ArrayList<String>();
                BufferedReader in2 = new BufferedReader(new InputStreamReader(new URL(
                        serverurl + "event.txt").openStream()));
                String s2;
                while ((s2 = in2.readLine()) != null) {
                    eventinfo.add(s2);
                }
                in2.close();
            } catch (Exception e) {
                Log.e("ERROR", "ERROR IN CODE: " + e.toString());
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(ThemeActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            setAdapter();
            cancel(true);
        }
    }

    private class downloaddFile extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresstheme.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                File direct = new File(Environment.getExternalStorageDirectory()
                        + "/Themebrowser");
                File tag = new File(direct + "/themenow");
                File tagg = new File(direct + "/.nomedia");
                if (!direct.exists()) {
                    direct.mkdirs();
                }
                if (!tagg.exists()) {
                    tagg.createNewFile();
                }
                if (tag.exists()) {
                    tag.delete();
                }
                DownloadManager mgr = (DownloadManager) ThemeActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(urL);
                DownloadManager.Request request = new DownloadManager.Request(
                        downloadUri);

                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setDescription("Image")
                        .setDestinationInExternalPublicDir("/Themebrowser/", "themenow");
                mgr.enqueue(request);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR ON GETTING FILE","Error is " +e);
                Toast.makeText(ThemeActivity.this, getResources().getString(R.string.errorsettheme), Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            cancel(true);
        }
    }

    private BroadcastReceiver completeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progresstheme.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(ThemeActivity.this);
            builder.setTitle(getResources().getString(R.string.iconblackchecktitle));
            builder.setMessage(getResources().getString(R.string.iconblackchecktext));
            builder.setPositiveButton(getResources().getString(R.string.btn_white), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences.Editor editor = prefsdef.edit();
                    editor.putBoolean("t-black", false);
                    editor.commit();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                    Toast.makeText(getApplicationContext(), "√, Set", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.btn_black), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences.Editor editor = prefsdef.edit();
                    editor.putBoolean("t-black", true);
                    editor.commit();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                    Toast.makeText(getApplicationContext(), "√, Set", Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(completeReceiver);
    }
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter completeFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(completeReceiver, completeFilter);
    }

    private void editshow(View vview){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int[] locc = {1,1};
            vview.getLocationInWindow(locc);
            int cxxxxx = locc[0];
            int cyxxyy = locc[1];
            int finalRadius = Math.max(tagsearch.getWidth(), tagsearch.getHeight());
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(tagsearch, cxxxxx, cyxxyy, 0, finalRadius);
            tagsearch.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            tagsearch.setVisibility(View.VISIBLE);
        }
    }
}