package com.tekstorm.trivizthegreattriviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText emailText, passwordText,resetPassword_email;
    private FirebaseAuth mAuth;
    String email,password;
    @Override
    protected void onStart() {
        super.onStart();
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

        emailText=findViewById(R.id.emailid);
        passwordText=findViewById(R.id.password);



    }

    public void signIn(View view) {

        email=emailText.getText().toString();
        password=passwordText.getText().toString();
        if(email.equals("") || password.equals("")){
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

                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            String emailAddress = "anusha267@gmail.com";

                            auth.sendPasswordResetEmail(emailAddress)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("TAG", "Email sent.");
                                            }
                                        }
                                    });

                        } else {
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
}