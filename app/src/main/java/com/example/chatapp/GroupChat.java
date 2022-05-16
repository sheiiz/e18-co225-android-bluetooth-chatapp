package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chatapp.Adapters.MessageAdapter;
import com.example.chatapp.Models.User;

import java.util.ArrayList;
import java.util.List;

public class GroupChat extends AppCompatActivity {

    MessageAdapter messageAdapter;
    User user1;
    List<Message> message ;
    RecyclerView rvMessage;
    EditText etMesssage;
    ImageButton imgButton;

    public static ArrayList<String> groupList1 = new ArrayList<>();
    ArrayList<String> groupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);



    }
    private void init(){

    }


}