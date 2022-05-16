package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chatapp.Adapters.MessageAdapter;
import com.example.chatapp.Models.User;

import java.util.List;

public class GroupChat extends AppCompatActivity {
    MessageAdapter messageAdapter;
    User user1;
    List<Message> message ;
    RecyclerView rvMessage;
    EditText etMesssage;
    ImageButton imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        init();
    }
    private void init(){

    }
}