package com.lp.bookmanager.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lp.bookmanager.R;
import com.lp.bookmanager.model.User;
import com.lp.bookmanager.tools.Constants;
import com.lp.bookmanager.tools.JsonLoader;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class MainActivity extends Activity {

    private EditText etLogin;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin =  (EditText)findViewById(R.id.login);
        etPassword = (EditText)findViewById(R.id.password);

        ((Button)findViewById(R.id.login_validate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                user.setNickname(login);
                connect(user);
            }
        });

        ((Button)findViewById(R.id.signIn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void connect(final User user){

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_AUTH + "isExist/user/" + user.getNickname(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("ERROR_RESPONSE", error.toString());
            }
        });

        queue.add(jsonObjReq);

//        if(user != null) {
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... voids) {
//                    String url = Constants.URL_AUTH + "isExist/user/" + user.getNickname();
//                    String userJson = null;
//                    try {
//                        userJson = JsonLoader.loadJson("GET", url);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d("USER", userJson);
//                    return null;
//                }
//            }.execute();
//        }else{
//
//        }

    }
}
