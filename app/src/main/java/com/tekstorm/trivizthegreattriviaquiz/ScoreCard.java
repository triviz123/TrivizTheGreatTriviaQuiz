package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.score_card,null);
        builder.setView(view);

        sharedPreferences = getContext().getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        myVib = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);

        mainButton=view.findViewById(R.id.mainmenu);
        scoreCard=view.findViewById(R.id.scoreCard);
        scoreCard.setText((QuestionAnswer.score+" POINTS"));

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
        if(newcorrect<10)
        {
            newlevel="Newbie";
        }
        else if(newcorrect < 50)
        {
            newlevel="Beginner";
        }
        else
        {
            newlevel="Pro";
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
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("nooo", "Error updating document", e);
                    }
                });


    }


}
