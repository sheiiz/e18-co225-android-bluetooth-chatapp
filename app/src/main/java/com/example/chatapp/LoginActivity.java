package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText UserEmail,UserPassword;
    private TextView NeedNewAccountLink,ForgetPasswordLink;

    private ProgressDialog loadingBar;
    Database skyChatDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        skyChatDB= new Database(this);


        InitializedFields();
        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegisterActivity();

            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin() {
        String email= UserEmail.getText().toString();
        String password= UserPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email...", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Log In");
            loadingBar.setMessage("Please wait... ");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            Boolean checkPassword = skyChatDB.checkPASSWORD(email,password);
            if(checkPassword==true){
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                sendUserToMainActivity();
            }
            else{
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
            loadingBar.dismiss();

        }
    }

    private void InitializedFields() {
        LoginButton = (Button) findViewById(R.id.login_button);
        UserEmail = (EditText) findViewById(R.id.login_email);
        UserPassword = (EditText) findViewById(R.id.login_password);
        NeedNewAccountLink = (TextView) findViewById(R.id.do_not_have_account_link);
        ForgetPasswordLink = (TextView) findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);



    }



    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void sendUserToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}