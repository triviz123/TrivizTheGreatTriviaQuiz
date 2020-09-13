package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.VIBRATOR_SERVICE;

public class ScoreCard extends AppCompatDialogFragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button mainButton;
    TextView scoreCard;

    private Vibrator myVib;
    SharedPreferences sharedPreferences;
    TextView totalText,correctText,wrongText,skipText;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.score_card,null);
        builder.setCancelable(false);

        super.onCreate(savedInstanceState);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });

        builder.setView(view);

        sharedPreferences = getContext().getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        myVib = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);

        mainButton=view.findViewById(R.id.mainmenu);
        scoreCard=view.findViewById(R.id.scoreCard);
        scoreCard.setText((QuestionAnswer.score+" POINTS"));


        totalText=view.findViewById(R.id.totalscore);
        correctText=view.findViewById(R.id.correctscore);
        wrongText=view.findViewById(R.id.wrongscore);
        skipText=view.findViewById(R.id.skipscore);


        totalText.setText(StaticConstants.numberOfQuestions);
        correctText.setText(String.valueOf(QuestionAnswer.corrects));
        wrongText.setText(String.valueOf(Integer.parseInt(StaticConstants.numberOfQuestions)-QuestionAnswer.corrects-QuestionAnswer.skips));
        skipText.setText(String.valueOf(QuestionAnswer.skips));

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myVib.vibrate(30);
                if(sharedPreferences.getString("soundToggle","").equals("0"))
                {
                    MediaPlayer buttonClick=MediaPlayer.create(getContext(), R.raw.button_click);
                    buttonClick.start();
                }

                QuestionAnswer.gameplayMusic.stop();

                addToDatabase();


                //startActivity(new Intent(getContext(),MainActivity.class));
            }


        });



        return builder.create();
    }

    private void addToDatabase() {
        FirebaseFirestore  db = FirebaseFirestore.getInstance();
        String email=sharedPreferences.getString("email","");
        Log.d("abc",email);
        int newtotal=Integer.parseInt(StaticConstants.numberOfQuestions)+Integer.parseInt(StaticConstants.total);
        int newcorrect=QuestionAnswer.corrects+Integer.parseInt(StaticConstants.correct);
        int newskip=QuestionAnswer.skips+Integer.parseInt(StaticConstants.skip);
        Log.d("abc",""+newcorrect+newskip+newtotal);
        String newlevel;

        if(newcorrect<50)
        {
            newlevel="Newbie : Level I";
        }
        else if(newcorrect < 100)
        {
            newlevel="Newbie : Level II";
        }
        else if(newcorrect < 300)
        {
            newlevel="Newbie : Level III";
        }
        else if(newcorrect<500)
        {
            newlevel="Beginner : Level I";
        }
        else if(newcorrect < 800)
        {
            newlevel="Beginner : Level II";
        }
        else if(newcorrect < 1200)
        {
            newlevel="Beginner : Level III";
        }
        else if(newcorrect<1600)
        {
            newlevel="Intermediate : Level I";
        }
        else if(newcorrect < 2000)
        {
            newlevel="Intermediate : Level II";
        }
        else if(newcorrect < 2500)
        {
            newlevel="Intermediate : Level III";
        }
        else if(newcorrect< 3200)
        {
            newlevel="Pro-Quizzer : Level I";
        }
        else if(newcorrect < 4000)
        {
            newlevel="Pro-Quizzer : Level II";
        }
        else if(newcorrect < 5000)
        {
            newlevel="Pro-Quizzer : Level III";
        }
        else if(newcorrect< 6000)
        {
            newlevel="Veteran : Level I";
        }
        else if(newcorrect < 8000)
        {
            newlevel="Veteran : Level II";
        }
        else if(newcorrect < 10000)
        {
            newlevel="Veteran : Level III";
        }
        else
        {
            newlevel="Quiz Master";
        }




        db.collection("users").document(email).update(
                "total",String.valueOf(newtotal),
                "correct",String.valueOf(newcorrect),
                "skip",String.valueOf(newskip),
                "level",newlevel
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("yay", "DocumentSnapshot successfully updated!");

            }
        });

        startActivity(new Intent(getContext(),MainActivity.class));

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        startActivity(new Intent(getContext(),MainActivity.class));
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        startActivity(new Intent(getContext(),MainActivity.class));
    }
}
