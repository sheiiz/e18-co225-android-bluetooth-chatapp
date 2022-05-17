package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Adapters.MessageAdapter;
import com.example.chatapp.Models.User;
import com.example.chatapp.R;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {
    Intent intent;
    TextView userName, userState;
    EditText getMessage;
    ImageButton sendMessageButton, backButton;
    androidx.appcompat.widget.Toolbar chatToolbar;
    CardView sendMessageCardView;
    RecyclerView msgRecycleView;

    String enteredMessage;
    Database skyChatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);


        userName = findViewById(R.id.Nameofspecificgroupuser);
        userState = findViewById(R.id.groupuserState);
        getMessage = findViewById(R.id.getgroupmessage);
        sendMessageButton = findViewById(R.id.imageviewsendgroupmessage);
        backButton = findViewById(R.id.backbuttonofspecificgroupchat);
        chatToolbar = findViewById(R.id.toolbarofspecificgroupchat);
        sendMessageCardView = findViewById(R.id.carviewofsendgroupmessage);
        msgRecycleView = findViewById(R.id.recyclerviewofspecificgroup);
        skyChatDB = new Database(this);

        intent = getIntent();
        String receiverName = intent.getStringExtra("ReceiverName");

        userName.setText(receiverName);
        userState.setText("Not Connected");

        setSupportActionBar(chatToolbar);
        chatToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredMessage = getMessage.getText().toString();
                if(enteredMessage.isEmpty()){

                }
                else{

                }
            }
        });

    }
    private void Update(String FRIEND){

        String USER= getUserEmail();




        Boolean checkFriend = skyChatDB.checkFRIEND(FRIEND);
        if(checkFriend==false){
            Boolean insert = skyChatDB.insertChat(USER,FRIEND);

        }




    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(GroupChatActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}