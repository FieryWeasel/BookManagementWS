package com.lp.bookmanager.tools.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lp.bookmanager.model.User;
import com.lp.bookmanager.tools.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iem on 03/03/15.
 */
public class NetworkRequests {

    public static void connect(Context context, String key, final ConnectionListener listener){


        Map<String, String> params = new HashMap<String, String>();
        params.put("crypted_key", key);

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_AUTH + "user/canConnect", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onConnectionSucceeded(response.getString("id"));
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
        addRequestToQueue(context, request);
    }

    public static void createUser(Context context, User user, final UserCreatedListener listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", user.getFirst_name());
        params.put("last_name", user.getLast_name());
        params.put("nickname", user.getNickname());
        params.put("birth_date", user.getBirth_date());
        params.put("email", user.getMail());
        params.put("crypted_key", user.getCrypted_key());

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "user/add", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onUserCreatedSuccessful();
                            }else {
                                listener.onUserCreatedFailed();
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
        addRequestToQueue(context, request);
    }

    public static void GetBookList(Context context, final BookListListener listener) {
        ObjectRequest request = new ObjectRequest( Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "book/get", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onBookListRetrieved(response.getString("books"));
                            }else {
                                listener.onFailToRetrieveBookList();
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
        addRequestToQueue(context, request);
    }

    public static void getUSerInfo(Context context, String userId, final UserInfoListener listener){

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", userId);

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "user/get", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onUserInfoCorrectlyRetrieved(response.getString("users"));
                            }else {
                                listener.onFailToRetrieveUserInfo();
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

        addRequestToQueue(context, request);
    }

    private static void addRequestToQueue(Context context,  Request request){
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
        public void onConnectionSucceeded(String userId);
        public void onConnectionFailed();
    }

    public interface UserInfoListener{
        public void onUserInfoCorrectlyRetrieved(String jsonUser);
        public void onFailToRetrieveUserInfo();
    }

    public interface BookListListener{
        public void onBookListRetrieved(String jsonBooks);
        public void onFailToRetrieveBookList();
    }

    public interface UserCreatedListener{
        public void onUserCreatedSuccessful();
        public void onUserCreatedFailed();
    }

}
