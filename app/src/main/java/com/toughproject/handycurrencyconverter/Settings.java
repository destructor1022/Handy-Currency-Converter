package com.toughproject.handycurrencyconverter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;


public class Settings extends WearableActivity {

    String GAMMA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String gamma = getIntent().getStringExtra("GAMMA");
        GAMMA = gamma;

        Currency temp = new Currency(GAMMA);

        LinearLayout root = findViewById(R.id.root);

        root.post( new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < temp.visible.size(); i++) {
                    if(i == 0) {
                        TextView region = new TextView(getApplicationContext());
                        region.setText("Commonly Used");
                        region.setTextColor(Color.parseColor("#ffffff"));
                        region.setTextSize(18f);
                        region.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        root.addView(region);
                    } else if (i == 9) {
                        TextView region = new TextView(getApplicationContext());
                        region.setText("Middle East");
                        region.setTextColor(Color.parseColor("#ffffff"));
                        region.setTextSize(18f);
                        region.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        root.addView(region);
                    } else if (i == 18) {
                        TextView region = new TextView(getApplicationContext());
                        region.setText("Americas");
                        region.setTextColor(Color.parseColor("#ffffff"));
                        region.setTextSize(18f);
                        region.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        root.addView(region);
                    } else if (i == 25) {
                        TextView region = new TextView(getApplicationContext());
                        region.setText("Europe");
                        region.setTextColor(Color.parseColor("#ffffff"));
                        region.setTextSize(18f);
                        region.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        root.addView(region);
                    } else if (i == 35) {
                        TextView region = new TextView(getApplicationContext());
                        region.setText("Asia & Pacific");
                        region.setTextColor(Color.parseColor("#ffffff"));
                        region.setTextSize(18f);
                        region.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        root.addView(region);
                    } else if (i == 49) {
                        TextView region = new TextView(getApplicationContext());
                        region.setText("Africa");
                        region.setTextColor(Color.parseColor("#ffffff"));
                        region.setTextSize(18f);
                        region.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        root.addView(region);
                    }

                    Switch next = new Switch(getApplicationContext());
                    next.setText(temp.currNamesList.get(i));
                    next.setId(i);
                    next.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    boolean bool = (temp.visible.get(i) == 1);
                    next.setChecked(bool);
                    next.setTextColor(Color.parseColor("#bdbdbd"));
                    root.addView(next);

                }

                Button reset = findViewById(R.id.Reset);
                //reset to default
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i < temp.visible.size(); i++) {
                            Switch next = findViewById(i);
                            if(i < 9) {
                                next.setChecked(true);
                            } else {
                                next.setChecked(false);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Currency temp = new Currency(GAMMA);

        SharedPreferences settings = getSharedPreferences("gamma", Context.MODE_PRIVATE);

        StringBuilder gamma = new StringBuilder();
        for(int i = 0; i < temp.visible.size(); i++) {
            Switch s = findViewById(i);
            String g = "1";

            if(s.isChecked() == false) {
                g = "0";
            }

            gamma.append(g);
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gamma", gamma.toString());
        editor.commit();
    }

    public float spToDp(float sp) {
        float px = sp * getResources().getDisplayMetrics().scaledDensity;
        float dp = px / getResources().getDisplayMetrics().density;
        return dp;
    }
}