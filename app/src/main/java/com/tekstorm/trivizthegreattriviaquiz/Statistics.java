package com.tekstorm.trivizthegreattriviaquiz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tekstorm.trivizthegreattriviaquiz.Login;
import com.tekstorm.trivizthegreattriviaquiz.MainActivity;
import com.tekstorm.trivizthegreattriviaquiz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.VIBRATOR_SERVICE;

public class Statistics extends AppCompatDialogFragment {
    LayoutInflater inflater;
    View view;
    AlertDialog.Builder builder;
    SharedPreferences sharedPreferences;
    private Vibrator myVib;
    FirebaseFirestore db;
    static String level;
    static int total,correct, skip;
    PieChart pieChart;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder=new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);

        inflater= Objects.requireNonNull(getActivity()).getLayoutInflater();
        view=inflater.inflate(R.layout.statistics,null);
        builder.setView(view);
        ImageView close=view.findViewById(R.id.close_dialog);
        sharedPreferences = getContext().getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        pieChart = view.findViewById(R.id.chart1);
        db = FirebaseFirestore.getInstance();


        ArrayList<PieEntry> arrayList=new ArrayList<>();
        //String correct_percent=(correct*100/total)+"%";
        ///String skip_percent=(skip*100/total)+"%";
        //String wrong_percent=((total-correct-skip)*100/total)+"%";


        arrayList.add(new PieEntry(correct,"Correct Answers"));
        arrayList.add(new PieEntry(skip,"Skipped Questions"));
        arrayList.add(new PieEntry(total-correct-skip,"Wrong Answers"));

        PieDataSet pieDataSet=new PieDataSet(arrayList, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(16f);
        PieData pieData=new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setTextSize(10f);
        legend.setTextColor(R.color.lightBlue);
        legend.setWordWrapEnabled(true);

        TextView totalText,correctText,wrongText,skipText,levelText;
        totalText=view.findViewById(R.id.total);
        correctText=view.findViewById(R.id.correct);
        wrongText=view.findViewById(R.id.wrong);
        skipText=view.findViewById(R.id.skip);
        levelText=view.findViewById(R.id.level);


        totalText.setText(String.valueOf(total));
        correctText.setText(String.valueOf(correct));
        wrongText.setText(String.valueOf(total-correct-skip));
        skipText.setText(String.valueOf(skip));
        levelText.setText(level);


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
