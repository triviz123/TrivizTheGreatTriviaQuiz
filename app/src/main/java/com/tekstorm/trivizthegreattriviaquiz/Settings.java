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

import java.util.Objects;

import static android.content.Context.VIBRATOR_SERVICE;

public class Settings extends AppCompatDialogFragment {
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
        view=inflater.inflate(R.layout.settings,null);
        builder.setView(view);
        ImageView close=view.findViewById(R.id.close);



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



        TextView signout=view.findViewById(R.id.signout);
        myVib = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myVib.vibrate(30);
                if(sharedPreferences.getString("soundToggle","").equals("0"))
                {
                    MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
                    buttonClick.start();
                }
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),Login.class));
            }
        });
        sharedPreferences = getContext().getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        music_btn=view.findViewById(R.id.music_btn);
        if(sharedPreferences.getString("musicToggle","").equals("0"))
        {
            music_btn.setText("On");
        }
        else
        {
            music_btn.setText("Off");
        }


        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myVib.vibrate(30);
                if(sharedPreferences.getString("soundToggle","").equals("0"))
                {
                    MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
                    buttonClick.start();
                }
                if (music_btn.getText().toString().equals("On")) {
                    MainActivity.mainMusic.stop();
                    sharedPreferences.edit().putString("musicToggle", "1").apply();
                    music_btn.setText("Off");
                } else {
                    MainActivity.mainMusic = MediaPlayer.create(getContext(), R.raw.main_menu_music);
                    MainActivity.mainMusic.start();
                    MainActivity.mainMusic.setLooping(true);
                    sharedPreferences.edit().putString("musicToggle", "0").apply();
                    music_btn.setText("On");
                }
            }
        });
        sound_btn=view.findViewById(R.id.sound_btn);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            sound_btn.setText("On");
        }
        else
        {
            sound_btn.setText("Off");
        }
        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myVib.vibrate(30);
                if(sharedPreferences.getString("soundToggle","").equals("0"))
                {
                    MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
                    buttonClick.start();
                }
                if (sound_btn.getText().toString().equals("On")) {
                    sharedPreferences.edit().putString("soundToggle", "1").apply();
                    sound_btn.setText("Off");
                } else {
                    sharedPreferences.edit().putString("soundToggle", "0").apply();
                    sound_btn.setText("On");
                }
            }
        });

        help=view.findViewById(R.id.help);
        privacy=view.findViewById(R.id.privacy);
        credits=view.findViewById(R.id.credits);



        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myVib.vibrate(30);
                if(sharedPreferences.getString("soundToggle","").equals("0"))
                {
                    MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
                    buttonClick.start();
                }

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "yugentechlabs@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TRIVIZ Help");
                getContext().startActivity(Intent.createChooser(emailIntent, null));

            }
        });

        

privacy.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
            buttonClick.start();
        }

        String url = "https://anusha-bhattacharya.me/TRIVIZfiles/Privacy%20Policy.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
});


        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myVib.vibrate(30);
                if(sharedPreferences.getString("soundToggle","").equals("0"))
                {
                    MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
                    buttonClick.start();
                }
                String url = "https://anusha-bhattacharya.me/TRIVIZfiles/Credits.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        return builder.create();
    }


}
