package com.tekstorm.trivizthegreattriviaquiz;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class JSONtoQues {
    String urlString="";
    Context context;


    public JSONtoQues(String urlString, Context context) {
        this.urlString = urlString;
        this.context=context;
        getQues();
    }

    private void getQues() {
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlString, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("dasdash",response.toString());
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


}
