package com.tekstorm.trivizthegreattriviaquiz;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;


public class JSONtoQues {
    String urlString="";
    Context context;
    String[][] arr;
    public JSONtoQues(String urlString, Context context) {
        this.urlString = urlString;
        this.context=context;
        arr=new String[10][5];
        getQuesBank();

    }

    private void getQuesBank() {
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlString, null, new Response.Listener<JSONObject>() {

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

    public void jsonParser(JSONObject response)
    {
        try {
            String s = response.getString("results");
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < 10; i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                arr[i][0]=jsonObject.getString("question");
                arr[i][1]=jsonObject.getString("correct_answer");
                JSONArray jsonArray1 = jsonObject.getJSONArray("incorrect_answers");
                String[] str_arr = new String[jsonArray1.length()];
                for(int j = 0; j < 3; j++) {

                    str_arr[j] = jsonArray1.getString(j);
                    Spanned spanned = Html.fromHtml(str_arr[j]);
                    str_arr[j] = spanned.toString();
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
    }



    }
