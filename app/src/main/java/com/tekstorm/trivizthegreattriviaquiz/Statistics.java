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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
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

        TextView totalText,correctText,wrongText,skipText;
        ImageView levelView=view.findViewById(R.id.level);
        totalText=view.findViewById(R.id.total);
        correctText=view.findViewById(R.id.correct);
        wrongText=view.findViewById(R.id.wrong);
        skipText=view.findViewById(R.id.skip);



        totalText.setText(String.valueOf(total));
        correctText.setText(String.valueOf(correct));
        wrongText.setText(String.valueOf(total-correct-skip));
        skipText.setText(String.valueOf(skip));



        if(correct<50)
        {
            levelView.setImageResource(R.drawable.n1);
        }
        else if(correct < 100)
        {
            levelView.setImageResource(R.drawable.n2);
        }
        else if(correct < 300)
        {
            levelView.setImageResource(R.drawable.n3);
        }
        else if(correct<500)
        {
            levelView.setImageResource(R.drawable.b1);
        }
        else if(correct < 800)
        {
            levelView.setImageResource(R.drawable.b2);
        }
        else if(correct < 1200)
        {
            levelView.setImageResource(R.drawable.b3);
        }
        else if(correct<1600)
        {
            levelView.setImageResource(R.drawable.i1);
        }
        else if(correct < 2000)
        {
            levelView.setImageResource(R.drawable.i2);
        }
        else if(correct < 2500)
        {
            levelView.setImageResource(R.drawable.i3);
        }
        else if(correct< 3200)
        {
            levelView.setImageResource(R.drawable.p1);
        }
        else if(correct < 4000)
        {
            levelView.setImageResource(R.drawable.p2);
        }
        else if(correct < 5000)
        {
            levelView.setImageResource(R.drawable.p3);
        }
        else if(correct< 6000)
        {
            levelView.setImageResource(R.drawable.q1);
        }
        else if(correct < 8000)
        {
            levelView.setImageResource(R.drawable.q2);
        }
        else if(correct < 10000)
        {
            levelView.setImageResource(R.drawable.q3);
        }
        else
        {
            levelView.setImageResource(R.drawable.u);
        }


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
