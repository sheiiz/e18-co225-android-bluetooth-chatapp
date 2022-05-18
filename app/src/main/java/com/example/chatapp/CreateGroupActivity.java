package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateGroupActivity extends AppCompatActivity {
    private EditText groupName;
    private Button createButton;
    private ImageButton backButton;
    Database skyChatDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        skyChatDB= new Database(this);
        groupName = (EditText) findViewById(R.id.set_group_name);
        createButton = (Button) findViewById(R.id.creating_group_button);
        backButton = (ImageButton) findViewById(R.id.backbuttonofCreateGroup);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        ImageView gallery = findViewById(R.id.set_profile_image);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null) {
            Uri selectedImage = data.getData();
            ImageView set_profile_image = findViewById(R.id.set_profile_image);
            set_profile_image.setImageURI(selectedImage);
        }
    }



    private void createGroup() {

        String group_Name= groupName.getText().toString();
        String groupAdmin= getUserEmail();

        if(TextUtils.isEmpty(groupName.getText().toString()) ){
            Toast.makeText(CreateGroupActivity.this, "Group name required", Toast.LENGTH_SHORT).show();
        }
        else{
            Boolean checkGroup = skyChatDB.checkGROUPNAME(groupName.getText().toString());
            if(checkGroup==false){
                Boolean insert = skyChatDB.insertGroup(group_Name,groupAdmin);
                if(insert==true){
                    Toast.makeText(CreateGroupActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                    sendUserToGroupChatActivity();
                }
                else{
                    Toast.makeText(CreateGroupActivity.this, "Unsucessfull", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(CreateGroupActivity.this, "Group already exist", Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void sendUserToGroupChatActivity() {
        Intent loginIntent = new Intent(CreateGroupActivity.this,MainActivity.class);
        startActivity(loginIntent);
    }
}