package com.tekstorm.trivizthegreattriviaquiz;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

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
        getQues();
    }

    private void getQues() {
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
            Log.d("hfdh", s);
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < 10; i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                arr[i][0]=jsonObject.getString("question");
                arr[i][1]=jsonObject.getString("correct_answer");
                JSONArray jsonArray1 = jsonObject.getJSONArray("incorrect_answers");
                for(JsonValue value : arrObj){
                    System.out.println(value.toString());
                }

                String[] s=str.split("//;';");
               /* int c=0; String h=str.substring(2,str.length()-2);
                c=str.indexOf(",");
                arr[i][2]=h.substring(0,c-3);
                arr[i][3]=h.substring(c,str.lastIndexOf(",")-3);
                arr[i][4]=h.substring(str.lastIndexOf(","));*/
                Spanned spanned = Html.fromHtml(arr[i][0]);
                arr[i][2] = spanned.toString();
                spanned = Html.fromHtml(arr[i][1]);
                arr[i][2] = spanned.toString();
                spanned = Html.fromHtml(arr[i][2]);
                arr[i][2] = spanned.toString();

                //arr[i][2]=str.substring()


                /*arr[i][2]=str[0];
                arr[i][3]=str[1];
                arr[i][4]=str[2];
                for(int k=0;k<10; k++)
                {
                    for(int l=0;l<5; l++) {
                        Log.d("jhg", arr[k][l]);
                    }
                }*/
                Log.d("hhg",arr[i][2]);
                Log.d("hhg",arr[i][3]);
                Log.d("hhg",arr[i][4]);
            }
        }
        catch (Exception e)
        {
           Log.d("Error", Objects.requireNonNull(e.getMessage()));
        }
    }


    }
