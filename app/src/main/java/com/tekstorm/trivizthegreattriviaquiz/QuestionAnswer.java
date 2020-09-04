package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;

public class QuestionAnswer extends AppCompatActivity {
    String url="";
    String[][] arr;
    int quesNumber=0;
    TextView question,answer1,answer2,answer3, answer4, quesNum,scoreView;
    int score=0;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        arr=new String[Integer.parseInt(StaticConstants.numberOfQuestions)][5];
        if(StaticConstants.cat.equals("0")) {
            url = "https://opentdb.com/api.php?amount=30&difficulty=hard&type=multiple";
        }
        else
        {
            url="https://opentdb.com/api.php?amount="+StaticConstants.numberOfQuestions+"&category="+StaticConstants.cat+"&type=multiple";
        }
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("dasdash",response.toString());
                jsonParser(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("hsah","That didn't work!");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void backToMain(View view) {
        timer.cancel();
        startActivity(new Intent(QuestionAnswer.this,MainActivity.class));

    }



    @Override
    public void onBackPressed() { }





    public void display(String[] s)
    {
        quesNum=findViewById(R.id.question_number);
        scoreView=findViewById(R.id.score);
        question=findViewById(R.id.question);
        answer1=findViewById(R.id.answer1);
        answer2=findViewById(R.id.answer2);
        answer3=findViewById(R.id.answer3);
        answer4=findViewById(R.id.answer4);
        String str= (quesNumber+1) +"/"+StaticConstants.numberOfQuestions;
        quesNum.setText(str);
        scoreView.setText(String.valueOf(score));
        question.setText(s[0]);
        answer1.setText(s[1]);
        answer2.setText(s[2]);
        answer3.setText(s[3]);
        answer4.setText(s[4]);
        timer.start();
    }










    public void jsonParser(JSONObject response)
    {
        try {
            String s = response.getString("results");
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < Integer.parseInt(StaticConstants.numberOfQuestions); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                arr[i][0]=jsonObject.getString("question");
                arr[i][1]=jsonObject.getString("correct_answer");
                JSONArray jsonArray1 = jsonObject.getJSONArray("incorrect_answers");
                String[] str_arr = new String[jsonArray1.length()];
                for(int j = 0; j < 3; j++) {

                    str_arr[j] = jsonArray1.getString(j);
                    Spanned spanned = Html.fromHtml(str_arr[j]);
                    str_arr[j] = spanned.toString();
                    arr[i][j+2]=str_arr[j];
                    Log.d("hhg",str_arr[j]);
                }

                Spanned spanned = Html.fromHtml(arr[i][0]);
                arr[i][0] = spanned.toString();
                spanned = Html.fromHtml(arr[i][1]);
                arr[i][1] = spanned.toString();
                spanned = Html.fromHtml(arr[i][2]);
                arr[i][2] = spanned.toString();
                spanned = Html.fromHtml(arr[i][3]);
                arr[i][3] = spanned.toString();
                spanned = Html.fromHtml(arr[i][4]);
                arr[i][4] = spanned.toString();

            }

        }
        catch (Exception e)
        {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
        }
       gameController();
    }

    private void gameController() {
        startTimer();
        gameplay();

    }

    private void startTimer() {
       timer=new CountDownTimer(16000,1000) {
            TextView timer=findViewById(R.id.timer);
            @Override
            public void onTick(long i)
            {

                timer.setText(String.valueOf(i/1000));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                if(quesNumber<Integer.parseInt(StaticConstants.numberOfQuestions)-1) {
                    quesNumber++;
                    timer.setText("10");
                    gameController();
                }
                else
                {
                    startActivity(new Intent(QuestionAnswer.this,MainActivity.class));
                }
            }
        };


    }





    private void gameplay() {
        Random rand=new Random();
        int locationOfCorrectAnswer=rand.nextInt(4)+1;
        String[] s=new String[5];
        int c=2;
        s[0]=arr[quesNumber][0];
        //s[locationOfCorrectAnswer]=arr[quesNumber][1];
        for(int j=1; j<=4; j++)
        {
            if(locationOfCorrectAnswer!=j)
            {
                s[j]=arr[quesNumber][c];
                c++;
            }
            else
            {
                s[j]=arr[quesNumber][1];
            }
        }
        //Toast.makeText(this, arr[quesNumber][1], Toast.LENGTH_SHORT).show();
        display(s);
    }

    public void checkAnswer(View view) {
        timer.cancel();
        TextView answerSelected=(TextView) view;
        if(answerSelected.getText().toString().equals(arr[quesNumber][1]))
        {
            score+=10;
        }

        if(quesNumber<Integer.parseInt(StaticConstants.numberOfQuestions)-1)
        {
            quesNumber++;
            gameController();

        }
        else
        {
            startActivity(new Intent(QuestionAnswer.this,MainActivity.class));
        }

    }


    public void skip(View view)
    {
        timer.cancel();
        if(quesNumber<Integer.parseInt(StaticConstants.numberOfQuestions)-1)
        {
            quesNumber++;
            gameController();

        }
        else
        {
            startActivity(new Intent(QuestionAnswer.this,MainActivity.class));
        }

    }
}
