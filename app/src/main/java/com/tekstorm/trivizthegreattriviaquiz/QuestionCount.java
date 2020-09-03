package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class QuestionCount extends AppCompatDialogFragment {
    TextView button1,button2, button3;
    Button saveButton;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view=inflater.inflate(R.layout.question_count,null);
        builder.setView(view);


        button1=(TextView) view.findViewById(R.id.button1);
        button2=(TextView)view.findViewById(R.id.button2);
        button3=(TextView)view.findViewById(R.id.button3);
        saveButton=(Button) view.findViewById(R.id.save);


        if (StaticConstants.numberOfQuestions.equals("10")) {
            button1.setBackgroundResource(R.drawable.pink_button);
            button1.setTextColor(Color.WHITE);
        }
        else if(StaticConstants.numberOfQuestions.equals("20"))
        {
            button2.setBackgroundResource(R.drawable.pink_button);
            button2.setTextColor(Color.WHITE);
        }
        else
        {
            button3.setBackgroundResource(R.drawable.pink_button);
            button3.setTextColor(Color.WHITE);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
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
        else if(button.getTag().toString().equals("20"))
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
        button.setBackgroundResource(R.drawable.pink_button);
        button.setTextColor(Color.WHITE);
        Log.d("jakj",StaticConstants.numberOfQuestions);
    }


}
