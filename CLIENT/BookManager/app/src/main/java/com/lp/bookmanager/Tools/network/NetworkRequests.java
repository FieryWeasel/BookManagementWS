package com.lp.bookmanager.tools.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lp.bookmanager.tools.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iem on 03/03/15.
 */
public class NetworkRequests {

    public static void connect(Context context, String key, final ConnectionListener listener){

        //TODO test and complete with async/result return
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<String, String>();
        params.put("crypted_key", key);

        CustomRequest request = new CustomRequest(Request.Method.POST,
                Constants.URL_BASE + Constants.URL_AUTH + "user/canConnect/", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onConnectionSucceeded();
                            }else {
                                listener.onConnectionFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
        });

//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                Constants.URL_BASE + Constants.URL_AUTH + "user/canConnect/", new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("RESPONSE", response.toString());
//                        try {
//                            if(response.getString("response").equalsIgnoreCase("YES")){
//                                listener.onConnectionSucceeded();
//                            }else {
//                                listener.onConnectionFailed();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Log.d("ERROR_RESPONSE", error.toString());
//                    }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Accept", "application/json");
//                headers.put("Content-type", "application/json");
//                return headers;
//            }
//        };

        NetworkData.getInstance().addToRequestQueue(context, request);

    }

//    //TODO Maybe later
//    public static boolean getCover(Context context, final String ISBN){
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                Constants.URL_BOOKSEARCH_FROM_ISBN + ISBN, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("RESPONSE", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Log.d("ERROR_RESPONSE", error.toString());
//                    }
//                });
//        NetworkData.getInstance().addToRequestQueue(context, jsonObjReq);
//
//        return true;
//    }

    public interface ConnectionListener{
        public void onConnectionSucceeded();
        public void onConnectionFailed();
    }


}
