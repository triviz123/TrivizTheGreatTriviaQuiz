package com.tekstorm.trivizthegreattriviaquiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HighscoreCalculation {
    String scores="";
    FirebaseFirestore db;
    public void importHighscore()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(StaticConstants.email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //scores=document.getString("highscores");
                        //assert scores != null;
                        //Log.d("abc",scores);
                        String user_email=document.getString("email");
                        String nickname=document.getString("nickname");
                        Log.d("abc", "DocumentSnapshot data: " + user_email);
                        Login.sharedPreferences.edit().putString("email",user_email).apply();
                        Log.d("abc", "DocumentSnapshot data: " + Login.sharedPreferences.getString("email",""));
                        Log.d("abc", "DocumentSnapshot data: " + nickname);
                        Login.sharedPreferences.edit().putString("nickname",nickname).apply();
                        Log.d("abc", "DocumentSnapshot data: " + Login.sharedPreferences.getString("nickname",""));
                    } else {
                        Log.d("abc", "No such document");
                    }
                } else {
                    Log.d("abc", "get failed with ", task.getException());
                }
            }
        });
    }


}
