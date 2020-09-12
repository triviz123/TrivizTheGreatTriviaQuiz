package com.tekstorm.trivizthegreattriviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
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

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class QuestionAnswer extends AppCompatActivity {
    String url="";
    String[][] arr;
    static MediaPlayer gameplayMusic;
    int quesNumber=0;
    TextView question,answer1,answer2,answer3, answer4, quesNum,scoreView;
    static int score=0;
    static CountDownTimer timer;
    private Vibrator myVib;
    String[] s1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_or_error);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        score=0;
        answer1=findViewById(R.id.answer1);
        answer2=findViewById(R.id.answer2);
        answer3=findViewById(R.id.answer3);
        answer4=findViewById(R.id.answer4);
        if(StaticConstants.cat.equals("0")) {
            url = "https://opentdb.com/api.php?amount=30&difficulty=hard&type=multiple";
            StaticConstants.numberOfQuestions="30";
        }
        else
        {
            url="https://opentdb.com/api.php?amount="+StaticConstants.numberOfQuestions+"&category="+StaticConstants.cat+"&type=multiple";
        }
        arr=new String[Integer.parseInt(StaticConstants.numberOfQuestions)][5];
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("dasdash",response.toString());

                setContentView(R.layout.activity_question_answer);
                try {
                    jsonParser(response);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                setContentView(R.layout.loading_or_error);
                LottieAnimationView anim=findViewById(R.id.loadView);
                anim.setVisibility(View.INVISIBLE);
                LottieAnimationView anim2=findViewById(R.id.loadingView);
                anim2.setVisibility(View.INVISIBLE);
                LottieAnimationView anim1=findViewById(R.id.errorView);
                anim1.setVisibility(View.VISIBLE);
                error.printStackTrace();
                Log.d("hsah","That didn't work!");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(QuestionAnswer.this,MainActivity.class));
                    }
                }).start();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void backToMain(View view) {
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }

        AreYouSure areYouSure=new AreYouSure();
        areYouSure.show(getSupportFragmentManager(),"Are You Sure");

    }



    @Override
    public void onBackPressed() {
    }





    public void display( String[] s) throws InterruptedException {
        /*if (quesNumber != 0) {
            int x=delayThread();
        }*/


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

                answer1.setBackgroundResource(R.drawable.answer_buttons);
                answer2.setBackgroundResource(R.drawable.answer_buttons);
                answer3.setBackgroundResource(R.drawable.answer_buttons);
                answer4.setBackgroundResource(R.drawable.answer_buttons);

                timer.start();



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);
    }


    @Override
    protected void onStart() {
        super.onStart();
         SharedPreferences sharedPreferences = this.getSharedPreferences("com.tekstorm.trivizthegreattriviaquiz", Context.MODE_PRIVATE);
        gameplayMusic= MediaPlayer.create(this, R.raw.gameplay_music);
        if(sharedPreferences.getString("musicToggle","").equals("0"))
        {
            gameplayMusic.start();
           gameplayMusic.setLooping(true);
        }
    }

    public void jsonParser(JSONObject response) throws InterruptedException {
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

    private void gameController() throws InterruptedException {
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
                    try {
                        gameController();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    ScoreCard scoreCard=new ScoreCard();
                    scoreCard.show(getSupportFragmentManager(),"Your Score");
                }
            }
        };


    }





    private void gameplay() throws InterruptedException {
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

    public void checkAnswer(View view) throws InterruptedException {
        timer.cancel();
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        TextView answerSelected=(TextView) view;
        if(answerSelected.getText().toString().equals(arr[quesNumber][1]))
        {
            score+=10;
            answerSelected.setBackgroundResource(R.drawable.correct_answer);


        }
        else
        {
            answerSelected.setBackgroundResource(R.drawable.wrong_answer);
        }



                if(quesNumber<Integer.parseInt(StaticConstants.numberOfQuestions)-1)
                {
                    quesNumber++;
                    gameController();

                }
                else
                {
                    ScoreCard scoreCard=new ScoreCard();
                    scoreCard.show(getSupportFragmentManager(),"Your Score");

                }





    }

    @Override
    protected void onPause() {
        super.onPause();
        gameplayMusic.stop();
    }

    private int delayThread() throws InterruptedException {
        Thread.currentThread();
        sleep(1000);
        return 1;
    }


    public void skip(View view) throws InterruptedException {
        timer.cancel();
        myVib.vibrate(30);
        if(sharedPreferences.getString("soundToggle","").equals("0"))
        {
            MediaPlayer buttonClick=MediaPlayer.create(this, R.raw.button_click);
            buttonClick.start();
        }
        if(quesNumber<Integer.parseInt(StaticConstants.numberOfQuestions)-1)
        {
            quesNumber++;
            gameController();

        }
        else
        {
            ScoreCard scoreCard=new ScoreCard();
            scoreCard.show(getSupportFragmentManager(),"Your Score");
        }

    }
}
