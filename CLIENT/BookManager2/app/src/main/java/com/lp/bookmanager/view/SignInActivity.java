package com.lp.bookmanager.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lp.bookmanager.R;
import com.lp.bookmanager.data_container.UserManager;
import com.lp.bookmanager.model.User;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    TextView mETBirthDate;
    EditText mETLogin;
    EditText mETMail;
    EditText mETFirstName;
    EditText mETLastName;
    EditText mETPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mETBirthDate = (TextView)findViewById(R.id.birthDate);
        mETLogin = (EditText)findViewById(R.id.login);
        mETMail = (EditText)findViewById(R.id.mail);
        mETFirstName = (EditText)findViewById(R.id.firstName);
        mETLastName = (EditText)findViewById(R.id.lastName);
        mETPassword = (EditText)findViewById(R.id.password);

        ((ImageButton)findViewById(R.id.birthDatePicker)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(SignInActivity.this, SignInActivity.this, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
                dialog.show();
            }
        });

        findViewById(R.id.login_validate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean filedsOk = verifyNewUserInformation();
                if(filedsOk) {
                    // Todo : Send request to WebService
                    int response = 1;
                    if (response != -1) {
                        //TODO construct user from server response
                        User user = new User(mETFirstName.getText().toString(), mETLastName.getText().toString(),
                                mETLogin.getText().toString(), mETBirthDate.getText().toString(), mETMail.getText().toString());
                        UserManager.getInstance().setUser(user);
                        finish();
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(SignInActivity.this);
                        dialog.setMessage(R.string.errorNetWork)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // FIRE ZE MISSILES!
                                        dialog.dismiss();
                                    }
                                });
                    }
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);

        return true;
    }

    private boolean verifyNewUserInformation() {
        boolean infosOk;

        if(mETLogin.getText() == null
                || mETLogin.getText().toString().equalsIgnoreCase("")
                || mETLogin.getText().toString().length() < 3){

            mETLogin.setError(getString(R.string.loginError));
            infosOk = false;
        }else
            infosOk = true;

        if(infosOk && (mETPassword.getText() == null
                || mETPassword.getText().toString().equalsIgnoreCase("")
                || mETPassword.getText().toString().length() < 8)){

            mETPassword.setError(getString(R.string.passwordError));

            infosOk = false;
        }else {
            infosOk = true;
        }

        if(infosOk && (mETMail.getText() == null
                || mETMail.getText().toString().equalsIgnoreCase("")
                || !isEmailValid(mETMail.getText().toString()) )){

            mETMail.setError(getString(R.string.mailError));
            infosOk = false;
        }else
            infosOk = true;

        return infosOk;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-_]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
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
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        mETBirthDate.setText(i3 + "/" + (i2 + 1) + "/" + i);
    }
}
