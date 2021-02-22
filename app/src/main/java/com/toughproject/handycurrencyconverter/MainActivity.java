package com.toughproject.handycurrencyconverter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.wear.widget.BoxInsetLayout;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends WearableActivity {
    String GAMMA;
    String gamma;
    Currency traits;

    String url1;
    String url2;

    TextView conv1_curr2;
    TextView conv2_curr1;


    boolean continueRot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();

        continueRot = false;

        SharedPreferences settings = getSharedPreferences("gamma", Context.MODE_PRIVATE);

        gamma = settings.getString("gamma", "default");
        GAMMA = gamma;
        traits = new Currency(gamma);
    }

    @Override
    protected void onPause() {
        super.onPause();

        continueRot = false;
    }

    protected void onStart() {
        super.onStart();

        BoxInsetLayout root_layout = (BoxInsetLayout) findViewById(R.id.root_layout);

        final ImageView settings_button = (ImageView) findViewById(R.id.settings_button);

        findViewById(R.id.loading).setVisibility(View.GONE);


        final ImageView flag1 = (ImageView) findViewById(R.id.flag1);
        final ImageView flag2 = (ImageView) findViewById(R.id.flag2);

        conv1_curr2 = (TextView) findViewById(R.id.conv1_curr2);
        conv2_curr1 = (TextView) findViewById(R.id.conv2_curr1);


        root_layout.post( new Runnable() {
            @Override
            public void run() {
                //initial setting of flags
                set_flags();

                //open settings
                settings_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        continueRot = true;
                        rotate_settings(settings_button, 0);
                        open_settings();
                    }
                });

                //change currency 1
                flag1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        traits.addCurrency(0);
                        set_flags();
                    }
                });

                //change currency 2
                flag2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        traits.addCurrency(1);
                        set_flags();
                    }
                });

                //change currency 1 back 1
                flag1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        traits.subCurrency(0);
                        set_flags();
                        return true;
                    }
                });

                //change currency 2 back 1
                flag2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        traits.subCurrency(1);
                        set_flags();
                        return true;
                    }
                });




            }
        });
    }

    public void rotate_settings(ImageView settings, int rotation) {
        rotation += 10;

        final Handler handler = new Handler(Looper.getMainLooper());
        int finalRotation = rotation;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                settings.setRotation(finalRotation);
                if(continueRot) {
                    rotate_settings(settings, finalRotation);
                }
            }
        }, 100);

    }

    public void open_settings(){
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("GAMMA", gamma);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    public void set_conv2_text() {
        final TextView conv2_curr1 = (TextView) findViewById(R.id.conv2_curr1);
        conv2_curr1.setText("loading...");

        String url_p1 = "https://www.x-rates.com/calculator/?from=";
        String url_p2 = "&to=";
        String url_p3 = "&amount=1";

        try {
            Ion.with(getApplicationContext()).load(url_p1 + traits.names.get(traits.current.get(1)) + url_p2 + traits.names.get(traits.current.get(0)) + url_p3).asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    String getit = result.split("ccOutputRslt")[1];
                    conv2_curr1.setText(getit.substring(2, 7));
                }
            });
        } catch (Exception o) {
            conv2_curr1.setText("can't load");
        }
    }

    public void set_flags() {
        final ImageView flag1 = (ImageView) findViewById(R.id.flag1);
        final ImageView flag2 = (ImageView) findViewById(R.id.flag2);

        flag1.setImageResource(traits.getFlag(0));
        flag2.setImageResource(traits.getFlag(1));

        final TextView currency1 = (TextView) findViewById(R.id.currency1);
        final TextView currency2 = (TextView) findViewById(R.id.currency2);

        currency1.setText(traits.getName(0));
        currency2.setText(traits.getName(1));



        new setConv1().execute();
        //set_conv2_text();

        set_widths_textviews();
    }

    public void set_widths_textviews() {
        //Everything on the left side first
        findViewById(R.id.currency1).setMinimumWidth(findViewById(R.id.flag1).getMeasuredWidth());
        findViewById(R.id.conv1_curr1).setMinimumWidth(findViewById(R.id.flag1).getMeasuredWidth());
        findViewById(R.id.conv2_curr1).setMinimumWidth(findViewById(R.id.flag1).getMeasuredWidth());

        //Everything in the middle next
        findViewById(R.id.textView6).setMinimumWidth(findViewById(R.id.arrow).getMeasuredWidth());
        findViewById(R.id.equal1).setMinimumWidth(findViewById(R.id.arrow).getMeasuredWidth());
        findViewById(R.id.equal2).setMinimumWidth(findViewById(R.id.arrow).getMeasuredWidth());

        //Everything on the right side last
        findViewById(R.id.currency2).setMinimumWidth(findViewById(R.id.flag2).getMeasuredWidth());
        findViewById(R.id.conv1_curr2).setMinimumWidth(findViewById(R.id.flag2).getMeasuredWidth());
        findViewById(R.id.conv2_curr2).setMinimumWidth(findViewById(R.id.flag2).getMeasuredWidth());
    }

    @SuppressLint("StaticFieldLeak")
    private class setConv1 extends AsyncTask<Void, Void, Void> {
        String url_p1 = "https://www.x-rates.com/calculator/?from=";
        String url_p2 = "&to=";
        String url_p3 = "&amount=1";

        String url;

        String usefulInfo;

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            conv1_curr2.setText("loading...");
            url = url_p1 + traits.getName(0) + url_p2 + traits.getName(1) + url_p3;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Connect to the website
                Document document = Jsoup.connect(url).get();

                //Get the html of the website
                String html = document.toString();
                usefulInfo = html.split("ccOutputRslt")[1];


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            conv1_curr2.setText(usefulInfo.substring(2, 7));
        }
    }
}
