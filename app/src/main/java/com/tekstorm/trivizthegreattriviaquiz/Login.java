package com.tekstorm.trivizthegreattriviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText emailText, passwordText,resetPassword_email;
    private FirebaseAuth mAuth;
    String email,password;
    FirebaseFirestore db;
    static SharedPreferences sharedPreferences;
    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(Login.this,MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);


        emailText=findViewById(R.id.emailid);
        passwordText=findViewById(R.id.password);



    }

    public void signIn(View view) {
        LottieAnimationView animationView=(LottieAnimationView) findViewById(R.id.lottieAnimationView);
        animationView.setVisibility(View.VISIBLE);
        Button signInButton=findViewById(R.id.signin_btn);
        signInButton.setVisibility(View.GONE);
        email=emailText.getText().toString();
        password=passwordText.getText().toString();
        if(email.equals("") || password.equals("")){

            signInButton.setVisibility(View.VISIBLE);

            animationView.setVisibility(View.GONE);
            Toast.makeText(this, "Please enter your email address and password!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signin", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(Login.this,MainActivity.class));

                            sharedPreferences.edit().putString("musicToggle", "0").apply();
                            sharedPreferences.edit().putString("soundToggle", "0").apply();

                            db.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @SuppressLint("CommitPrefEdits")
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {

                                            String user_email=document.getString("email");
                                            String nickname=document.getString("nickname");
                                            Log.d("TAG", "DocumentSnapshot data: " + user_email);
                                            sharedPreferences.edit().putString("email",user_email).apply();
                                            Log.d("share", "DocumentSnapshot data: " + sharedPreferences.getString("email",""));
                                            Log.d("TAG", "DocumentSnapshot data: " + nickname);
                                            sharedPreferences.edit().putString("nickname",nickname).apply();
                                            Log.d("share", "DocumentSnapshot data: " + sharedPreferences.getString("nickname",""));
                                        } else {
                                            Log.d("TAG", "No such document");
                                        }
                                    } else {
                                        Log.d("TAG", "get failed with ", task.getException());
                                    }
                                }
                            });

                        } else {
                            Button signInButton=findViewById(R.id.signin_btn);
                            signInButton.setVisibility(View.VISIBLE);
                            LottieAnimationView animationView=(LottieAnimationView) findViewById(R.id.lottieAnimationView);
                            animationView.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w("signin", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Error! No record found!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }

    public void signUp(View view) {

        startActivity(new Intent(Login.this,SignUp.class));

    }


    public void forgotPassword(View view) {
        ResetPassword resetPassword=new ResetPassword();
        resetPassword.show(getSupportFragmentManager(),"Reset Password");

    }
    @Override
    public void onBackPressed() {

    }
}