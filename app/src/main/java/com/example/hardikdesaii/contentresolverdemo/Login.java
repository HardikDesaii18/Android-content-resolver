package com.example.hardikdesaii.contentresolverdemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener
{
    TextInputLayout mlayoutemail, mlayoutpassword;
    EditText muserEmailAddress, muserPassword;
    TextView  msignup, mhideshowPassword, tv_login_infomsg;
    Boolean isPasswordVisible = true;
    private boolean mLoading = false;
    private Button muserLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * setupToolbar method passed here
         */
        //setToolbar();
        /**
         * used to setup UI components
         */
        setupUIData();

    }




    /**
     * Sets up the toolbar for the activity.
     */
   /* private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
   */ private void setupUIData() {

        muserLogin = (Button) findViewById(R.id.btn_Login_submit);
        muserLogin.setOnClickListener(this);


        msignup = (TextView) findViewById(R.id.tv_login_sign_up_link);
        msignup.setOnClickListener(this);

        mlayoutemail = (TextInputLayout) findViewById(R.id.input_layout_email);
        mlayoutpassword = (TextInputLayout) findViewById(R.id.input_layout_pass);

        muserEmailAddress = (EditText) findViewById(R.id.et_login_email);
        muserPassword = (EditText) findViewById(R.id.et_login_password);

        mhideshowPassword = (TextView) findViewById(R.id.tv_login_hide_show_password);
        mhideshowPassword.setOnClickListener(this);
        tv_login_infomsg = (TextView) findViewById(R.id.tv_login_infomsg);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {

            case R.id.btn_Login_submit:
                if (signInValidate()) {
                   submitForm();
                }
                break;
            case R.id.tv_login_sign_up_link:
            case R.id.tv_login_infomsg:
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
                break;
            case R.id.tv_login_hide_show_password:
                showHidePassword();
                break;

        }

    }

    private void submitForm()
    {
        String email=muserEmailAddress.getText().toString().toLowerCase();
        String password=muserPassword.getText().toString();

        String cpassword=SharedPrefHelper.getPrefsHelper().getPref(email,"empty");
        if(password.equals(cpassword))
        {
            Intent intent=new Intent(Login.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(Login.this,"Invalid email/password",Toast.LENGTH_SHORT).show();
        }


    }

    private void setButtonEnable(boolean isEnable)
    {
        if (!isEnable) {
            muserEmailAddress.setEnabled(false);
            muserPassword.setEnabled(false);
            muserLogin.setEnabled(false);
        } else {
            muserEmailAddress.setEnabled(true);
            muserPassword.setEnabled(true);
            muserLogin.setEnabled(true);
        }
    }
    private Boolean signInValidate()
    {
        /**
         * A flag is initialized for checking Edittext value is empty or not
         */
        Boolean logInCheck = true;
        final String UserName = muserEmailAddress.getText().toString().trim();
        final String pass = muserPassword.getText().toString().trim();

        Log.e("Email", UserName);
        Log.e("Password", pass);

        /**
         *  validation: edittext is empty
         *
         */
        if (UserName.isEmpty() || !validEmail(UserName)) {
            mlayoutemail.setError(getString(R.string.enterSignIdErrMsg));
            requestFocus(muserEmailAddress);
            logInCheck = false;

        } else {
            mlayoutemail.setError(null);
            requestFocus(muserEmailAddress);
        }
        if (pass.equals("")) {
            mlayoutpassword.setError(this.getResources().getString(R.string.enterPasswordErrMsg));
            requestFocus(muserPassword);
            logInCheck = false;
        } else {
            mlayoutpassword.setError(null);
            requestFocus(muserPassword);
        }
        return logInCheck;

    }
    private boolean validEmail(String email)
    {
        String email_pattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private void showHidePassword() {


        // show password

        if (isPasswordVisible) {
            isPasswordVisible = false;
            mhideshowPassword.setText(R.string.tv_hide_password);
            muserPassword.setTransformationMethod(null);
            muserPassword.setSelection(muserPassword.getText().length());
            mhideshowPassword.setVisibility(View.GONE);
            mhideshowPassword.setVisibility(View.VISIBLE);

        } else {

            //Hide password
            isPasswordVisible = true;
            mhideshowPassword.setText(R.string.tv_show_password);
            muserPassword.setTransformationMethod(new PasswordTransformationMethod());
            muserPassword.setSelection(muserPassword.getText().length());
            mhideshowPassword.setVisibility(View.GONE);
            mhideshowPassword.setVisibility(View.VISIBLE);
        }
    }

} // main class ends here
