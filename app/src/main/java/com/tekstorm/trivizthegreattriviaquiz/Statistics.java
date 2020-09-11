package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tekstorm.trivizthegreattriviaquiz.Login;
import com.tekstorm.trivizthegreattriviaquiz.MainActivity;
import com.tekstorm.trivizthegreattriviaquiz.R;

import java.util.Objects;

import static android.content.Context.VIBRATOR_SERVICE;

public class Statistics extends AppCompatDialogFragment {
    LayoutInflater inflater;
    View view;
    AlertDialog.Builder builder;
    private EditText new_nickname;
    Button changenickname;
    SharedPreferences sharedPreferences;
    FirebaseFirestore db;
    TextView music_btn,sound_btn,help,privacy,credits;
    private Vibrator myVib;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder=new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);

        inflater= Objects.requireNonNull(getActivity()).getLayoutInflater();
        view=inflater.inflate(R.layout.statistics,null);
        builder.setView(view);
        ImageView close=view.findViewById(R.id.close_dialog);
        sharedPreferences = getContext().getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);



        myVib = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myVib.vibrate(30);
                if(sharedPreferences.getString("soundToggle","").equals("0"))
                {
                    MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
                    buttonClick.start();
                }
                dismiss();
            }
        });



        return builder.create();
    }


}
