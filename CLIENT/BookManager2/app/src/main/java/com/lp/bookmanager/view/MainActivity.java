package com.lp.bookmanager.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lp.bookmanager.R;
import com.lp.bookmanager.data_container.UserManager;
import com.lp.bookmanager.model.Account;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.model.User;
import com.lp.bookmanager.tools.Constants;
import com.lp.bookmanager.tools.network.NetworkRequests;

import java.util.List;


public class MainActivity extends Activity implements NetworkRequests.ConnectionListener, NetworkRequests.UserInfoListener {

    private EditText etLogin;
    private EditText etPassword;
    private ProgressDialog mRingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin =  (EditText)findViewById(R.id.login);
        etPassword = (EditText)findViewById(R.id.password);

        (findViewById(R.id.login_validate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();

                mRingProgressDialog = ProgressDialog.show(MainActivity.this, getString(R.string.wait), getString(R.string.authenticating), true);
                mRingProgressDialog.setCancelable(false);


                NetworkRequests.connect(MainActivity.this, Account.getCryptedfkey(password, login), MainActivity.this);

            }
        });

        (findViewById(R.id.signIn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivityForResult(intent, Constants.SIGN_IN_SUCCESSFUL);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK && requestCode == Constants.SIGN_IN_SUCCESSFUL){
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionSucceeded(String userId) {

        if(userId.equalsIgnoreCase("")){
            mRingProgressDialog.dismiss();
        }else{
            NetworkRequests.getUSerInfo(MainActivity.this, userId, MainActivity.this);
        }
    }

    @Override
    public void onConnectionFailed() {
        mRingProgressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Error, please try again");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onUserInfoCorrectlyRetrieved(String jsonUser) {
        Gson gson = new Gson();
        List<User> list = gson.fromJson(jsonUser, new TypeToken<List<User>>(){}.getType());
        UserManager.getInstance().setUser(list.get(0));

        mRingProgressDialog.dismiss();
        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
        intent.putExtra("UserRetrieved", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailToRetrieveUserInfo() {
        mRingProgressDialog.dismiss();
        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
        intent.putExtra("UserRetrieved", false);
        startActivity(intent);
        finish();
    }
}
