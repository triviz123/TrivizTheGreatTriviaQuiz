package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        TextView nickname = findViewById(R.id.nickname);
        nickname.setText(sharedPreferences.getString("nickname",""));
        StaticConstants.email=sharedPreferences.getString("email","");
        StaticConstants.user_nickname=sharedPreferences.getString("nickname","");


    }


    public void settings_view(View view) {

        Settings settings=new Settings();
        settings.show(getSupportFragmentManager(),"Settings");

    }

    public void openCategory(View view) {
        startActivity(new Intent(MainActivity.this,Categories.class));
    }


    public void playButton(View view)
    {
        /*if(Categories.c1==0 && QuestionCount.c2==0)
        {
            Toast.makeText(this, "Default Category is Triviz Ultimate Quiz Challenge and default question count is 30.", Toast.LENGTH_SHORT).show();
        }
        if(Categories.c1==0 && QuestionCount.c2!=0)
        {
            Toast.makeText(this, "Default Category is Triviz Ultimate Quiz Challenge.", Toast.LENGTH_SHORT).show();
        }
        if(QuestionCount.c2==0 && Categories.c1!=0)
        {
            Toast.makeText(this, "Default question count is 10.", Toast.LENGTH_SHORT).show();
            StaticConstants.numberOfQuestions="10";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(MainActivity.this,QuestionAnswer.class));
            }
        }).start();*/
        if(!StaticConstants.cat.equals("0"))
        {
            StaticConstants.numberOfQuestions="10";
        }
        startActivity(new Intent(MainActivity.this,QuestionAnswer.class));

    }


    @Override
    public void onBackPressed() {

    }



    public void questionCount(View view) {
        QuestionCount questionCount=new QuestionCount();
        questionCount.show(getSupportFragmentManager(),"Question Count");
    }

    public void change(View view) {
        TextView nickname = findViewById(R.id.nickname);
        nickname.setText(sharedPreferences.getString("nickname",""));
        ChangeNickname changeNickname=new ChangeNickname();
        changeNickname.show(getSupportFragmentManager(),"Change Nickname");
        nickname = findViewById(R.id.nickname);
        nickname.setText(sharedPreferences.getString("nickname",""));
    }


    @Override
    protected void onResume() {
        super.onResume();
        TextView nickname = findViewById(R.id.nickname);
        nickname.setText(sharedPreferences.getString("nickname",""));
    }
}