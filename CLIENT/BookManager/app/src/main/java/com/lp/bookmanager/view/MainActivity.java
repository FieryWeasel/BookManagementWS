package com.lp.bookmanager.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Account;
import com.lp.bookmanager.tools.Constants;
import com.lp.bookmanager.tools.network.NetworkRequests;


public class MainActivity extends Activity implements NetworkRequests.ConnectionListener {

    private EditText etLogin;
    private EditText etPassword;

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
            Intent intent = new Intent(MainActivity.this, ListBookActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionSucceeded() {
        Intent intent = new Intent(MainActivity.this, ListBookActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed() {
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
}
