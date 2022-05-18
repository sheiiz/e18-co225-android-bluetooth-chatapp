package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private Button SaveButton;
    private EditText UserPassword,NewUserEmail,NewUserPassword,NewUserConfirmPassword;
    private ImageButton backButton;

    Database skyChatDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        NewUserEmail = findViewById(R.id.change_settings_email);
        NewUserPassword = findViewById(R.id.change_settings_password);
        NewUserConfirmPassword = findViewById(R.id.change_settings_confirm_password);
        skyChatDB= new Database(this);
        SaveButton = (Button) findViewById(R.id.save_button);
        backButton = (ImageButton) findViewById(R.id.backbuttonofupdateprofile);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
    private void Update(){

        String email= getUserEmail();

        String new_email= NewUserEmail.getText().toString();
        String new_password= NewUserPassword.getText().toString();
        String renew_password= NewUserConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(NewUserEmail.getText().toString()) || TextUtils.isEmpty(NewUserPassword.getText().toString()) || TextUtils.isEmpty(NewUserConfirmPassword.getText().toString())){
            Toast.makeText(SettingsActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
        }
        else if(!new_password.equals(renew_password)){
            Toast.makeText(SettingsActivity.this, "Please Enter Password Correctly", Toast.LENGTH_SHORT).show();

        }
        else{


            Boolean checkUser = skyChatDB.checkUSERNAME(NewUserEmail.getText().toString());
            if(checkUser==false){
                Boolean insert = skyChatDB.updateUser(email,new_email,new_password);
                Boolean insert2 = skyChatDB.updateChats(email,new_email);
                Boolean insert3 = skyChatDB.updateGroupAdmin(email,new_email);
                if(insert==true){
                    Toast.makeText(SettingsActivity.this, "Sucessfull", Toast.LENGTH_SHORT).show();

                    sendUserToLoginActivity();
                }
                else{
                    Toast.makeText(SettingsActivity.this, "Unsucessfull", Toast.LENGTH_SHORT).show();

                }

            }
            else{
                Toast.makeText(SettingsActivity.this, "User already exist", Toast.LENGTH_SHORT).show();

            }


        }





    }



    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(SettingsActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }

/*
        package com.example.chatapp;

import android.widget.EditText;

    public class SettingsActivity {

        //Controls
        private Button updateAccountSettings;
        private EditText userName, userStatus;
        private CircleImageView userProfileImage;

        //Firebase
        private FirebaseAuth mAuth;
        private DatabaseReference dataPath;
        private StorageReference UserProfileImagePath;
        private StorageTask loadingTask;

        private String currentUserId;



    }

 */
}

