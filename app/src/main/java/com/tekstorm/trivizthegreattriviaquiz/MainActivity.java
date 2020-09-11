package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    static MediaPlayer mainMusic;

    private Vibrator myVib;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);


        TextView nickname = findViewById(R.id.nickname);
        nickname.setText(sharedPreferences.getString("nickname",""));
        StaticConstants.email=sharedPreferences.getString("email","");
        StaticConstants.user_nickname=sharedPreferences.getString("nickname","");

    }


    public void settings_view(View view) {
        Settings settings=new Settings();
        settings.show(getSupportFragmentManager(),"Settings");
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
    }

    public void openCategory(View view) {
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        startActivity(new Intent(MainActivity.this,Categories.class));

    }


    public void playButton(View view)
    {

        mainMusic.stop();
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        if(!StaticConstants.cat.equals("0") && QuestionCount.c2==0)
        {
            StaticConstants.numberOfQuestions="10";
        }
        startActivity(new Intent(MainActivity.this,QuestionAnswer.class));

    }


    @Override
    public void onBackPressed() {

    }



    public void questionCount(View view) {
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        QuestionCount questionCount=new QuestionCount();
        questionCount.show(getSupportFragmentManager(),"Question Count");
    }

    public void change(View view) {
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        ChangeNickname changeNickname=new ChangeNickname();
        changeNickname.show(getSupportFragmentManager(),"Change Nickname");
        TextView nickname = findViewById(R.id.nickname);
        nickname.setText(sharedPreferences.getString("nickname",""));

    }

    @Override
    protected void onPause() {
        super.onPause();
        mainMusic.stop();

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

        TextView nickname = findViewById(R.id.nickname);
        nickname.setText(sharedPreferences.getString("nickname",""));


    }


    public void openStats(View view) {
        Statistics statistics=new Statistics();
        statistics.show(getSupportFragmentManager(),"Statistics");
    }
}