package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void signout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
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
        if(Categories.c1==0 && QuestionCount.c2==0)
        {
            Toast.makeText(this, "Default Category is Triviz Ultimate Quiz Challenge and default question count is 30.", Toast.LENGTH_SHORT).show();
        }
        if(Categories.c1==0 && QuestionCount.c2!=0)
        {
            Toast.makeText(this, "Default Category is Triviz Ultimate Quiz Challenge.", Toast.LENGTH_SHORT).show();
        }
        if(QuestionCount.c2==0 && Categories.c1!=0)
        {
            Toast.makeText(this, "Default question count is 30.", Toast.LENGTH_SHORT).show();
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
        }).start();

    }


    @Override
    public void onBackPressed() {

    }



    public void questionCount(View view) {
        QuestionCount questionCount=new QuestionCount();
        questionCount.show(getSupportFragmentManager(),"Question Count");
    }
}