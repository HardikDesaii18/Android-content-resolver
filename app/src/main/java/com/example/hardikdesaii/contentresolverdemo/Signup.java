package com.example.hardikdesaii.contentresolverdemo;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardikdesaii.contentresolverdemo.MainActivity;
import com.example.hardikdesaii.contentresolverdemo.R;
import com.example.hardikdesaii.contentresolverdemo.SharedPrefHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Signup extends AppCompatActivity implements View.OnClickListener
{
    TextInputLayout mlayoutlastname, mlayoutfirstname, mlayoutemail, mlayoutpassword, mlayoutcpassword;
    EditText mFirstName, mLastName, muserEmail, muserPassword, mconfirmPassword;
    Toolbar toolbarSingup;
    TextView maddAccount, mcheckShowPassword, mcheckShowConfirmPassword, tv_Signup_signinLink, checkHidePassword;
    Boolean isPasswordVisible = true;
    private boolean mLoading = false;
    private Button btnsignin;
    private CheckBox cbTerms;
    private TextView tv_signup_tc;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

       // setToolbarData();
        //set up all UI componnent
        setUIComponent();


    }


    /**
     * set Toolbar name
     */
    /*private void setToolbarData() {
        toolbarSingup = (Toolbar) findViewById(R.id.toolbar_signup);
//        setSupportActionBar(toolbarSingup);


        toolbarSingup.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();            }
        });
    }*/

    /**
     * initialization all component
     */
    private void setUIComponent() {
        mlayoutlastname = (TextInputLayout) findViewById(R.id.input_layout_last_name);
        mlayoutfirstname = (TextInputLayout) findViewById(R.id.input_layout_name);
        mlayoutemail = (TextInputLayout) findViewById(R.id.input_layout_email);
        mlayoutpassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        mlayoutcpassword = (TextInputLayout) findViewById(R.id.input_layout_confirm_pass);

        mFirstName = (EditText) findViewById(R.id.et_Signup_name);
        mLastName = (EditText) findViewById(R.id.et_Signup_lastname);
        muserEmail = (EditText) findViewById(R.id.et_Signup_email);
        muserPassword = (EditText) findViewById(R.id.et_Signup_password);
        mconfirmPassword = (EditText) findViewById(R.id.et_Signup_confirm_password);

        mcheckShowPassword = (TextView) findViewById(R.id.tv_Signup_show_password);
        mcheckShowPassword.setOnClickListener(this);

        mcheckShowConfirmPassword = (TextView) findViewById(R.id.tv_Signup_hide_show_confirmpassword);
        mcheckShowConfirmPassword.setOnClickListener(this);

        btnsignin = (Button) findViewById(R.id.btn_Signup_submit);
        btnsignin.setOnClickListener(this);

        maddAccount = (TextView) findViewById(R.id.tv_sign_up_link);
        maddAccount.setOnClickListener(this);

        tv_Signup_signinLink = (TextView) findViewById(R.id.tv_Signup_signinLink);
        tv_Signup_signinLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) 
    {
        switch (view.getId()) {

            case R.id.btn_Signup_submit:
                    if (signUpValidate()) 
                    {
                        submitForm();
                    }
                

                break;
            case R.id.tv_Signup_show_password:
                userhideshowpassword();
                break;

            case R.id.tv_Signup_hide_show_confirmpassword:
                userhideshowconfirmpassword();
                break;

            case R.id.tv_sign_up_link:
            case R.id.tv_Signup_signinLink:
                Signup.this.finish();
                break;
        }
    }

    private void submitForm()
    {
        String email=muserEmail.getText().toString().toLowerCase();
        String password=muserPassword.getText().toString();
        SharedPrefHelper.getPrefsHelper().setData(email,password);
        Toast.makeText(Signup.this,"Registration completed successfully",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(Signup.this,MainActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void setButtonEnable(boolean isEnable)
    {
        if (!isEnable) {
            mFirstName.setEnabled(false);
            mLastName.setEnabled(false);
            muserEmail.setEnabled(false);
            muserPassword.setEnabled(false);
            mconfirmPassword.setEnabled(false);
            btnsignin.setEnabled(false);
        } else {
            mFirstName.setEnabled(true);
            mLastName.setEnabled(true);
            muserEmail.setEnabled(true);
            muserPassword.setEnabled(true);
            mconfirmPassword.setEnabled(true);
            btnsignin.setEnabled(true);
        }
    }

    private Boolean signUpValidate() {
        /**
         * A flag is initialized for checking Edittext value is empty or not
         */
        Boolean logInCheck = true;
        final String Firstname = mFirstName.getText().toString().trim();
        final String Lastname = mLastName.getText().toString().trim();
        final String UserName = muserEmail.getText().toString().trim();
        final String pass = muserPassword.getText().toString().trim();

        Log.e("Firstname", Firstname);
        Log.e("Lastname", Lastname);
        Log.e("Email", UserName);
        Log.e("Password", pass);

        /**
         *  validation: edittext is empty
         */
        if (Firstname.equals("")) {
            mlayoutfirstname.setError(this.getResources().getString(R.string.enterFirstErrMsg));
            requestFocus(mFirstName);
            logInCheck = false;
        } else {
            mlayoutfirstname.setError(null);
            requestFocus(mFirstName);
        }
        if (Lastname.equals("")) {
            mlayoutlastname.setError(this.getResources().getString(R.string.enterLastErrMsg));
            requestFocus(mLastName);
            logInCheck = false;
        } else {
            mlayoutlastname.setError(null);
            requestFocus(mLastName);
        }
        if (pass.isEmpty() || !passwordMatch()) {
            mlayoutpassword.setError(this.getResources().getString(R.string.enterPasswordErrMsg));
            requestFocus(muserPassword);
            logInCheck = false;
        } else {
            mlayoutpassword.setError(null);
            requestFocus(muserPassword);
        }
        if (UserName.isEmpty() || !validEmail(UserName)) {
            mlayoutemail.setError(getString(R.string.enterSignIdErrMsg));
            requestFocus(muserEmail);
            logInCheck = false;
        } else {
            mlayoutemail.setError(null);
            requestFocus(muserEmail);
        }
        return logInCheck;
    }
    private boolean passwordMatch() {
        String pass = muserPassword.getText().toString();
        String cpass = mconfirmPassword.getText().toString();
        if (!pass.equals(cpass)) {
            mlayoutcpassword.setError(getString(R.string.err_msg_passmatch));
            requestFocus(mconfirmPassword);
            return false;
        } else {
            mlayoutcpassword.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validEmail(String email) {
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
    private void userhideshowpassword() {
        if (isPasswordVisible) {
            isPasswordVisible = false;
            mcheckShowPassword.setText(R.string.tv_hide_password);
            muserPassword.setTransformationMethod(null);
            muserPassword.setSelection(muserPassword.getText().length());
            mcheckShowPassword.setVisibility(View.GONE);
            mcheckShowPassword.setVisibility(View.VISIBLE);

        } else {

            // Hide password
            isPasswordVisible = true;
            muserPassword.setTransformationMethod(new PasswordTransformationMethod());
            muserPassword.setSelection(muserPassword.getText().length());
            mcheckShowPassword.setText(R.string.tv_show_password);
            mcheckShowPassword.setVisibility(View.GONE);
            mcheckShowPassword.setVisibility(View.VISIBLE);
        }
    }
    private void userhideshowconfirmpassword() {
        if (isPasswordVisible) {
            isPasswordVisible = false;
            mcheckShowConfirmPassword.setText(R.string.tv_hide_password);
            mconfirmPassword.setTransformationMethod(null);
            mconfirmPassword.setSelection(mconfirmPassword.getText().length());
            mcheckShowConfirmPassword.setVisibility(View.GONE);
            mcheckShowConfirmPassword.setVisibility(View.VISIBLE);

        } else {

            // Hide password
            isPasswordVisible = true;
            mcheckShowConfirmPassword.setText(R.string.tv_show_password);
            mconfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            mconfirmPassword.setSelection(mconfirmPassword.getText().length());
            mcheckShowConfirmPassword.setVisibility(View.GONE);
            mcheckShowConfirmPassword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
} // main class ends here
