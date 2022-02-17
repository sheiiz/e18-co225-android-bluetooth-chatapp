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
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference RootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        RootReference = FirebaseDatabase.getInstance("https://chatapp-c7b80-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        //databaseReference=Firebase.database.getReference("Users")
        InitializedFields();

        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLoginActivity();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
       // RootReference = FirebaseDatabase.getInstance().getReference();
    }

    private void CreateNewAccount() {
        String email= UserEmail.getText().toString();
        String password= UserPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email...", Toast.LENGTH_SHORT).show();
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait... ");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String currentUserID =mAuth.getCurrentUser().getUid();

                                RootReference.child("Users").child(currentUserID).setValue("");
                                sendUserToMainActivity();
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();


                            }
                            else{
                                loadingBar.dismiss();
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error : "+message, Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
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