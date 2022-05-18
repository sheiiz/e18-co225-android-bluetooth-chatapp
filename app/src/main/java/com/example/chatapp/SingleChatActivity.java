package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SingleChatActivity extends AppCompatActivity {

    public static TextView userState;
    private static Database skyChatDB;

    Intent intent;
    Button listenButton, connectButton;
    TextView userName;
    EditText getMessage;
    ImageButton sendMessageButton, backButton;
    androidx.appcompat.widget.Toolbar chatToolbar;
    CardView sendMessageCardView;
    RecyclerView msgRecycleView;
    BluetoothDevice device;
    MessagesAdapter messagesAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Messages> messagesArrayList = new ArrayList<>();

    public static String receiverName;

    public static int msgID=30;
    String enteredMessage;

    ChatUtils chatUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        userName = findViewById(R.id.Nameofspecificuser);
        userState = findViewById(R.id.userState);
        getMessage = findViewById(R.id.getmessage);
        sendMessageButton = findViewById(R.id.imageviewsendmessage);
        backButton = findViewById(R.id.backbuttonofspecificchat);
        chatToolbar = findViewById(R.id.toolbarofspecificchat);
        sendMessageCardView = findViewById(R.id.carviewofsendmessage);
        msgRecycleView = findViewById(R.id.recyclerviewofsinglechat);
        listenButton = findViewById(R.id.btn_listen);
        connectButton = findViewById(R.id.btn_connect);
        skyChatDB= new Database(this);

        chatUtils = new ChatUtils();
        intent = getIntent();


        receiverName = intent.getStringExtra("ReceiverName");

        if(receiverName.length()>12){
            receiverName = receiverName.substring(0,11)+" ...";
        }
        //To store in SQLite
        Update(receiverName);

        userName.setText(receiverName);
        userState.setText(chatUtils.status);

         String receiverIndex = intent.getStringExtra("deviceIndex");
         String deviceType = intent.getStringExtra("deviceType");

         if(deviceType.equals("Paired")){
             device = MainActivity.myBluetooth.getPairedDevice(Integer.parseInt(receiverIndex));
         }else{
             device = MainActivity.myBluetooth.getAvailableDevice(Integer.parseInt(receiverIndex));
         }

        /*--------------------------------------------------------------------------------------------------
        /*----- Delete this part ---------
         messagesArrayList.clear();
        messagesArrayList.add(new Messages("hello", "sent", "1", "5","1"));
        messagesArrayList.add(new Messages("My name is shehan madhusanka", "sent", "1", "5","1"));
        messagesArrayList.add(new Messages("Im fine thank you", "receive", "1", "5","1"));
        messagesArrayList.add(new Messages("hello", "sent", "1", "5","1"));
        messagesArrayList.add(new Messages("Bye", "receive", "1", "5","1"));
        /*--------------------------------------------------------------------------------------------------
        /*--------------------------------------------------------------------------------------------------*/
        messagesArrayList.clear();
        messagesArrayList.add(new Messages(1, 6, "sent", "hiiii"));

        int chatID = skyChatDB.getCHATID(getUserEmail(),receiverName);
        messagesArrayList=skyChatDB.getMsg(chatID);


        linearLayoutManager=new LinearLayoutManager(SingleChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        msgRecycleView.setLayoutManager(linearLayoutManager);
        messagesAdapter=new MessagesAdapter(SingleChatActivity.this,messagesArrayList);
        msgRecycleView.setAdapter(messagesAdapter);
        messagesAdapter.notifyDataSetChanged();


/*
          Write a method to get messages from database.
        * Take all the messages with sender and user id and other arguments
        * Create Message instances with that data
        * add thease instances to messagesArrayList
        * put  | messagesAdapter.notifyDataSetChanged();| end of the method
        */


        setSupportActionBar(chatToolbar);
        chatToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*Put here what happened when touch the tool bar*/
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread listen = new Thread(chatUtils.getServerClassInstance());
                listen.start();
                userState.setText("Starting...");

            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread client = new Thread(chatUtils.getClientClassInstance(device));
                client.start();
                userState.setText("Connecting...");
            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredMessage = getMessage.getText().toString();
                if(enteredMessage.isEmpty()){
                    Toast.makeText(SingleChatActivity.this, "Enter a message", Toast.LENGTH_SHORT).show();
                }
                else if(chatUtils.CURRENT_STATE == 3 || chatUtils.CURRENT_STATE == 5){
                    String send_msg = String.valueOf(getMessage.getText());
                    chatUtils.Write(send_msg);
                    getMessage.setText(null);
                    getMessage.setHint("Type a message");

                    /*In here add the send_msg to database*/
                    String user = getUserEmail();
                    int chatID = skyChatDB.getCHATID(user,receiverName);
                    msgID++;
                    skyChatDB.insertMsg(msgID,chatID,"sent",enteredMessage);

                }
                else{
                    Toast.makeText(SingleChatActivity.this, "Connection not established", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void Update(String FRIEND){

        String USER= getUserEmail();

        Boolean insert = skyChatDB.insertChat(USER,FRIEND);
        if(insert==true){
            Toast.makeText(SingleChatActivity.this, "ADDED", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(SingleChatActivity.this, "NOT ADDED", Toast.LENGTH_SHORT).show();


        }









    }
    public static void setReceiverMsg(String enteredMessage){

        String user = getUserEmail();
        int chatID = skyChatDB.getCHATID(user,receiverName);
        msgID++;
        skyChatDB.insertMsg(msgID,chatID,"received",enteredMessage);

    }

}