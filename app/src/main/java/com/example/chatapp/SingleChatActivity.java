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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public static MessagesAdapter messagesAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<Messages> messagesArrayList = new ArrayList<>();

    public static String receiverName;

    public static int msgID=0;
    String enteredMessage;

    ChatUtils chatUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        userName = findViewById(R.id.single_chat_username);
        userState = findViewById(R.id.userState);
        getMessage = findViewById(R.id.getmessage);
        sendMessageButton = findViewById(R.id.imageviewsendmessage);
        backButton = findViewById(R.id.single_chat_back_btn);
        chatToolbar = findViewById(R.id.single_chat_toolbar);
        sendMessageCardView = findViewById(R.id.carviewofsendmessage);
        msgRecycleView = findViewById(R.id.single_chat_recycleview);
        listenButton = findViewById(R.id.btn_listen);
        connectButton = findViewById(R.id.btn_connect);
        skyChatDB= new Database(this);

        chatUtils = new ChatUtils();
        intent = getIntent();

        int chatID = skyChatDB.getCHATID(getUserEmail(),intent.getStringExtra("ReceiverName"));

        receiverName = intent.getStringExtra("ReceiverName");

        if(receiverName.length()>12){
            receiverName = receiverName.substring(0,11)+" ...";
        }

        //To store in SQLite
        Update(intent.getStringExtra("ReceiverName"));

        userName.setText(receiverName);
        userState.setText(chatUtils.status);

         String deviceType = intent.getStringExtra("deviceType");

         if(deviceType.equals("Paired")){
             String receiverIndex = intent.getStringExtra("deviceIndex");
             device = MainActivity.myBluetooth.getPairedDevice(Integer.parseInt(receiverIndex));
         }else if(deviceType.equals("Available")){
             String receiverIndex = intent.getStringExtra("deviceIndex");
             device = MainActivity.myBluetooth.getAvailableDevice(Integer.parseInt(receiverIndex));
         }else{
             // Need to implement this later
             device = null;
         }


        messagesArrayList.clear();
        messagesArrayList.addAll(skyChatDB.getMsg(chatID));


        linearLayoutManager=new LinearLayoutManager(SingleChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        msgRecycleView.setLayoutManager(linearLayoutManager);
        messagesAdapter=new MessagesAdapter(SingleChatActivity.this,messagesArrayList);
        msgRecycleView.setAdapter(messagesAdapter);
        messagesAdapter.notifyDataSetChanged();



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

                    Messages msg = new Messages(msgID,chatID,"sent",enteredMessage);
                    messagesArrayList.add(msg);

                    messagesAdapter.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(SingleChatActivity.this, "Connection not established", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void Update(String FRIEND){

        String USER= getUserEmail();
        byte[] FRIEND_DEVICE = makeBYTE(device);

        Boolean insert = skyChatDB.insertChat(USER,FRIEND,FRIEND_DEVICE);
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

        Messages msg = new Messages(msgID,chatID,"received",enteredMessage);
        messagesArrayList.add(msg);

        messagesAdapter.notifyDataSetChanged();


    }

    // Convert BluetoothDevice object to byte array
    public byte[] makeBYTE(BluetoothDevice btdevice) {
        try {

            ByteArrayOutputStream baos  = null;
            ObjectOutputStream oos = null;

            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            oos.writeObject(btdevice);
            byte[] employeeAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
            return employeeAsBytes;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get BluetoothDevice object from  byte array
    public BluetoothDevice makeOBJECT(byte[] data) {
        try {

            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            BluetoothDevice dataobj = (BluetoothDevice ) ois.readObject();
            return dataobj ;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}