package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ChangeNickname extends AppCompatDialogFragment {
        private EditText new_nickname;
        Button changenickname;
    SharedPreferences sharedPreferences;
    FirebaseFirestore db;
    View view;
    TextView nickname;
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
            db = FirebaseFirestore.getInstance();
            LayoutInflater inflater=getActivity().getLayoutInflater();
            view=inflater.inflate(R.layout.changenickname,null);
            builder.setView(view);
            sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);

            new_nickname=view.findViewById(R.id.new_nickname);
            changenickname=(Button) view.findViewById(R.id.changenickname);

                changenickname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (new_nickname.getText().toString().equals(""))
                    {
                        dismiss();
                        return;
                    }
                    else {
                        String email = sharedPreferences.getString("email", "");
                        assert email != null;
                        DocumentReference emailRef = db.collection("users").document(email);


                        emailRef
                                .update("nickname", new_nickname.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                                        sharedPreferences.edit().putString("nickname", new_nickname.getText().toString()).apply();
                                        Toast.makeText(getContext(), "Nickname successfully updated!", Toast.LENGTH_SHORT).show();
                                        dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error updating document", e);
                                        Toast.makeText(getContext(), "An error occured! Please try again!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    }
                });




            return builder.create();
        }



    }
