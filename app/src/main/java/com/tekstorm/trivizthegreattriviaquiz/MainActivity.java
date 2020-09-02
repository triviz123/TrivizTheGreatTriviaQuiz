package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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


    }

    public void openCategory(View view) {
        startActivity(new Intent(MainActivity.this,Categories.class));
    }


    public void playButton(View view)
    {
        if (StaticConstants.cat.equals(""))
        {
            Toast.makeText(this, "Please select a category first!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(MainActivity.this,QuestionAnswer.class));

    }


    @Override
    public void onBackPressed() {

    }
}