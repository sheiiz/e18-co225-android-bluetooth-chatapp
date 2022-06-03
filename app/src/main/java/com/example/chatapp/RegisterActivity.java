package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class  RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText UserEmail,UserPassword;
    private TextView AlreadyHaveAccountLink;

    private Uri selectedImage;
    private ImageView set_profile_image;
    private Bitmap image_to_store;

    private ProgressDialog loadingBar;

    Database skyChatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        skyChatDB= new Database(this);

        InitializedFields();


        set_profile_image = findViewById(R.id.getuserimageinimageview);

        set_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });




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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==RESULT_OK && data!=null) {
                selectedImage = data.getData();
                image_to_store = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                set_profile_image.setImageBitmap(image_to_store);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "e.getMessage()", Toast.LENGTH_SHORT).show();
        }
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
                Boolean insert = skyChatDB.insertUSER(email1,password1,image_to_store);
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
        UserEmail = (EditText) findViewById(R.id.register_username);
        UserPassword = (EditText) findViewById(R.id.register_password);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_account_link);
        loadingBar = new ProgressDialog(this);

    }
    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }

}