package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Settings extends AppCompatDialogFragment {
    LayoutInflater inflater;
    View view;
    AlertDialog.Builder builder;
    private EditText new_nickname;
    Button changenickname;
    SharedPreferences sharedPreferences;
    FirebaseFirestore db;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder=new AlertDialog.Builder(getActivity());

        inflater= Objects.requireNonNull(getActivity()).getLayoutInflater();
        view=inflater.inflate(R.layout.settings,null);
        builder.setView(view);
        ImageView close=view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        TextView signout=view.findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),Login.class));
            }
        });
        /*TextView change_nickname=view.findViewById(R.id.changenickname);
        change_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view=inflater.inflate(R.layout.changenickname,null);
                builder.setView(view);

                new_nickname=view.findViewById(R.id.new_nickname);
                changenickname=(Button) view.findViewById(R.id.changenickname);

                changenickname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db = FirebaseFirestore.getInstance();
                        String email=sharedPreferences.getString("email","");
                        assert email != null;
                        DocumentReference emailRef = db.collection("users").document(email);


                        emailRef
                                .update("nickname", new_nickname.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                                        sharedPreferences.edit().putString("nickname",new_nickname.getText().toString()).apply();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error updating document", e);
                                    }
                                });
                    }
                });

            }
        });*/

        return builder.create();
    }


}
