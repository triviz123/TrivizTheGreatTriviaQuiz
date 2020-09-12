package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Categories extends AppCompatActivity {
    SharedPreferences i;
    static int c1=0;
    static MediaPlayer mainMusic;
    private Vibrator myVib;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Log.d("cat",StaticConstants.cat);

        sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);




        if (StaticConstants.pinkText!=null) {
            Log.d("buttonId",String.valueOf(StaticConstants.pinkText.getTag()));
            TextView button=findViewById(StaticConstants.pinkText.getId());
            button.setBackgroundResource(R.drawable.pink_button);
            button.setTextColor(Color.WHITE);
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StaticConstants.pinkText!=null) {
            TextView button=findViewById(StaticConstants.pinkText.getId());
            StaticConstants.pinkText.setBackgroundResource(R.drawable.pink_button);
            StaticConstants.pinkText.setTextColor(Color.WHITE);
        }
    }

    public void backToMain(View view)
    {
        super.onBackPressed();
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        mainMusic.stop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        mainMusic.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainMusic.stop();
    }

    public void categorySelect(View view) {

        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        if(c1==0)
        {
            c1=1;
        }
        TextView button=(TextView) view;
        StaticConstants.cat=button.getTag().toString();
        if(StaticConstants.cat.equals("0"))
        {
            Toast.makeText(this, "The default value of question count for this category is 30.", Toast.LENGTH_SHORT).show();
            StaticConstants.numberOfQuestions="30";
        }
        StaticConstants.pinkText=button;
        for (int i = 1; i <= 25; i++) {
                TextView changeButton = (TextView) findViewById(getResources().getIdentifier("button" + i, "id",
                        this.getPackageName()));
            changeButton.setBackgroundResource(R.drawable.white_button);
            changeButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }
        button.setBackgroundResource(R.drawable.pink_button);
        button.setTextColor(Color.WHITE);
        Log.d("jakj",StaticConstants.cat);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mainMusic=MediaPlayer.create(this, R.raw.main_menu_music);
        if(sharedPreferences.getString("musicToggle","").equals("0"))
        {
            mainMusic.start();
            mainMusic.setLooping(true);
        }
    }
}