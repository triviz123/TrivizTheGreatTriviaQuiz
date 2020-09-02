package com.tekstorm.trivizthegreattriviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    EditText emailText, passwordText;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        emailText=findViewById(R.id.emailid);
        passwordText=findViewById(R.id.password);
    }

    public void signUp(View view) {
        String email=emailText.getText().toString();
        String password=passwordText.getText().toString();
        LottieAnimationView animationView=(LottieAnimationView) findViewById(R.id.lottieAnimationView);
        animationView.setVisibility(View.VISIBLE);
        Button signInButton=findViewById(R.id.signup_btn);
        signInButton.setVisibility(View.GONE);
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