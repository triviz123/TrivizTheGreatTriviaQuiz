package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Categories extends AppCompatActivity {
    static String cat="";
    SharedPreferences i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        if (pinkText!=null) {
            pinkText.setBackgroundResource(R.drawable.pink_button);
            pinkText.setTextColor(Color.WHITE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pinkText!=null) {
            pinkText.setBackgroundResource(R.drawable.pink_button);
            pinkText.setTextColor(Color.WHITE);
        }
    }

    public void backToMain(View view)
    {
        super.onBackPressed();
    }


    public void categorySelect(View view) {

        TextView button=(TextView) view;
        cat=button.getTag().toString();
        pinkText=button;
        for (int i = 1; i <= 25; i++) {
                TextView changeButton = (TextView) findViewById(getResources().getIdentifier("button" + i, "id",
                        this.getPackageName()));
            changeButton.setBackgroundResource(R.drawable.white_button);
            changeButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }
        button.setBackgroundResource(R.drawable.pink_button);
        button.setTextColor(Color.WHITE);
        Log.d("jakj",cat);
    }
}