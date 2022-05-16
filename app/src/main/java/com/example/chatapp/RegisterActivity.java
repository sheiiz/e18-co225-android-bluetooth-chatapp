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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText UserEmail,UserPassword;
    private TextView AlreadyHaveAccountLink;

    private ProgressDialog loadingBar;
    private DatabaseReference RootReference;
    Database skyChatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        skyChatDB= new Database(this);

        InitializedFields();


        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();



            }
        });

        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLoginActivity();
            }
        });


    }

    private void createAccount(){
        String email1= UserEmail.getText().toString();
        String password1 = UserPassword.getText().toString();
        if(TextUtils.isEmpty(UserEmail.getText().toString()) || TextUtils.isEmpty(UserPassword.getText().toString())){
            Toast.makeText(RegisterActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait... ");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();



            Boolean checkUser = skyChatDB.checkUSERNAME(UserEmail.getText().toString());
            if(checkUser==false){
                Boolean insert = skyChatDB.insertUser(email1,password1);
                if(insert==true){
                    Toast.makeText(RegisterActivity.this, "Sucessfull", Toast.LENGTH_SHORT).show();

                    sendUserToLoginActivity();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Unsucessfull", Toast.LENGTH_SHORT).show();

                }

            }
            else{
                Toast.makeText(RegisterActivity.this, "User already exist", Toast.LENGTH_SHORT).show();

            }
            loadingBar.dismiss();

        }




    }
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void InitializedFields() {
        RegisterButton = (Button) findViewById(R.id.register_button);
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_account_link);
        loadingBar = new ProgressDialog(this);

    }
    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }

}