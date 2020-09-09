package com.tekstorm.trivizthegreattriviaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.firestore.FirebaseFirestore;

public class ScoreCard extends AppCompatDialogFragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button mainButton;
    TextView scoreCard;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.score_card,null);
        builder.setView(view);

        mainButton=view.findViewById(R.id.mainmenu);
        scoreCard=view.findViewById(R.id.scoreCard);
        scoreCard.setText((QuestionAnswer.score+" POINTS"));

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });



        return builder.create();
    }



}
