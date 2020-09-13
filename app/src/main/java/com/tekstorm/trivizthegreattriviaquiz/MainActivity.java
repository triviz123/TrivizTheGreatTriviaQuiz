package com.tekstorm.trivizthegreattriviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

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
        StaticConstants.email=sharedPreferences.getString("email","");


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);
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
        if(!StaticConstants.cat.equals("0") && StaticConstants.numberOfQuestions.equals("30"))
        {
            StaticConstants.numberOfQuestions="10";
        }
        startActivity(new Intent(MainActivity.this,QuestionAnswer.class));

    }


    @Override
    public void onBackPressed() {
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }

        ExitGame exit=new ExitGame();
        exit.show(getSupportFragmentManager(),"Exit");


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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainMusic.stop();

    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
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

        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("users").document(StaticConstants.email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Statistics.level=document.getString("level");
                        Statistics.total=Integer.parseInt(Objects.requireNonNull(document.getString("total")));
                        Statistics.correct=Integer.parseInt(Objects.requireNonNull(document.getString("correct")));
                        Statistics.skip=Integer.parseInt(Objects.requireNonNull(document.getString("skip")));
                        Log.d("tag2",Statistics.level+Statistics.total+Statistics.correct+Statistics.skip);
                        Statistics statistics = new Statistics();
                        statistics.show(getSupportFragmentManager(), "Statistics");

                    } else {
                        Log.d("TAG", "No such document");

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching data! Try again later.", Toast.LENGTH_SHORT).show();

                }





            }
        });









    }
}