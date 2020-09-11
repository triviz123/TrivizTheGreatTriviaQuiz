package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


import java.util.Objects;

import static android.content.Context.VIBRATOR_SERVICE;

public class QuestionCount extends AppCompatDialogFragment {
    TextView button1,button2, button3;
    Button saveButton;
    static int c2=0;
    private Vibrator myVib;
    SharedPreferences sharedPreferences;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);

        LayoutInflater inflater= Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view=inflater.inflate(R.layout.question_count,null);
        builder.setView(view);

        sharedPreferences = getContext().getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        myVib = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);


        button1=(TextView) view.findViewById(R.id.button1);
        button2=(TextView)view.findViewById(R.id.button2);
        button3=(TextView)view.findViewById(R.id.button3);
        saveButton=(Button) view.findViewById(R.id.save);


        if (StaticConstants.numberOfQuestions.equals("10")) {
            button1.setBackgroundResource(R.drawable.signin_signup_button);
            button1.setTextColor(Color.WHITE);
        }
        else if(StaticConstants.numberOfQuestions.equals("15"))
        {
            button2.setBackgroundResource(R.drawable.signin_signup_button);
            button2.setTextColor(Color.WHITE);
        }
        else
        {
            button3.setBackgroundResource(R.drawable.signin_signup_button);
            button3.setTextColor(Color.WHITE);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {

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
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countSelect(view);
        }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countSelect(view);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countSelect(view);
            }
        });
        return builder.create();
    }



    public void countSelect(View view) {

        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
            buttonClick.start();
        }

        if(c2==0)
        {
            c2=1;
        }
        if(StaticConstants.cat.equals("0"))
        {
            Toast.makeText(getContext(), "The default value of question count for The Ultimate Quiz Challenge is 30.", Toast.LENGTH_SHORT).show();
            StaticConstants.numberOfQuestions="30";
            dismiss();
            return;
        }

        TextView button=(TextView) view;
        StaticConstants.numberOfQuestions=button.getTag().toString();
        StaticConstants.pinkText1=button;
        if(button.getTag().toString().equals("10"))
        {
            button2.setBackgroundResource(R.drawable.white_border_button);
            button2.setTextColor(Color.parseColor("#FF8FB8"));
            button3.setBackgroundResource(R.drawable.white_border_button);
            button3.setTextColor(Color.parseColor("#FF8FB8"));
        }
        else if(button.getTag().toString().equals("15"))
        {
            button1.setBackgroundResource(R.drawable.white_border_button);
            button1.setTextColor(Color.parseColor("#FF8FB8"));
            button3.setBackgroundResource(R.drawable.white_border_button);
            button3.setTextColor(Color.parseColor("#FF8FB8"));
        }
        else{
            button2.setBackgroundResource(R.drawable.white_border_button);
            button2.setTextColor(Color.parseColor("#FF8FB8"));
            button1.setBackgroundResource(R.drawable.white_border_button);
            button1.setTextColor(Color.parseColor("#FF8FB8"));
        }
        button.setBackgroundResource(R.drawable.signin_signup_button);
        button.setTextColor(Color.WHITE);
        Log.d("jakj",StaticConstants.numberOfQuestions);
    }


}
