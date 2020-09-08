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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText emailText, passwordText, nicknameText;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
String email;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nicknameText=findViewById(R.id.nickname);
        emailText=findViewById(R.id.emailid);
        passwordText=findViewById(R.id.password);
    }

    public void signUp(View view) {
        email=emailText.getText().toString();
        String password=passwordText.getText().toString();
        String nickname=nicknameText.getText().toString();
        LottieAnimationView animationView=(LottieAnimationView) findViewById(R.id.lottieAnimationView);
        animationView.setVisibility(View.VISIBLE);
        Button signInButton=findViewById(R.id.signup_btn);
        signInButton.setVisibility(View.GONE);
        final Map<String, Object> user_details = new HashMap<>();
        user_details.put("email", email);
        user_details.put("nickname", nickname);
        if(email.equals("") || password.equals("")){

            signInButton.setVisibility(View.VISIBLE);

            animationView.setVisibility(View.GONE);
            Toast.makeText(this, "Please enter your email address and password!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("Email Verification", "Email sent.");
                                                Toast.makeText(SignUp.this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                                                db.collection("users").add(user_details)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("failed", "Error adding document", e);
                                                            }
                                                        });

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


                                            }
                                        }
                                    });



                            startActivity(new Intent(SignUp.this,MainActivity.class));
                        } else {
                            Button signInButton=findViewById(R.id.signup_btn);
                            signInButton.setVisibility(View.VISIBLE);
                            LottieAnimationView animationView=(LottieAnimationView) findViewById(R.id.lottieAnimationView);
                            animationView.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Error Occured!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this,Login.class));
                        }

                        // ...
                    }
                });
    }


    public void signIn(View view) {

        startActivity(new Intent(SignUp.this,Login.class));

    }
}