package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;


import static com.example.chatapp.SingleChatActivity.setReceiverMsg;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ChatUtils {

    private BluetoothAdapter bluetoothAdapter;
    public Context context;
    public String status = "Not Connected";
    public String tempReceiveMessage;

    private SendReceive sendReceive;
    Database skyChatDB;


    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;

    public int  CURRENT_STATE = STATE_CONNECTION_FAILED;


    private static final String APP_NAME = "SkyChat";
    private static final UUID MY_UUID = UUID.fromString("38fe6632-9620-4bd9-96c6-bc1f100827c3");

    public ChatUtils(){
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case STATE_LISTENING:
                    status = "Listening...";
                    break;
                case STATE_CONNECTING:
                    status = "Connecting...";
                    break;
                case STATE_CONNECTED:
                    status = "Connected";
                    break;
                case STATE_CONNECTION_FAILED:
                    status = "Connection Failed";
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuffer = (byte[]) message.obj;
                    tempReceiveMessage = new String(readBuffer,0,message.arg1); // receiving message - add this to database


                    setReceiverMsg(tempReceiveMessage);

                    break;
            }

            CURRENT_STATE = message.what;
             SingleChatActivity.userState.setText(status);

            return true;
        }
    });


    public ServerClass getServerClassInstance(){

        return new ServerClass();
    }

    public ClientClass getClientClassInstance(BluetoothDevice device){
        return new ClientClass(device);
    }

    public void Write(String msg){
        sendReceive.write(msg.getBytes());
    }


    private class ServerClass extends Thread{

        private BluetoothServerSocket serverSocket;

        @SuppressLint("MissingPermission")
        public ServerClass(){
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            BluetoothSocket socket = null;
            while(socket==null){
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);

                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();

                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }
                if(socket!=null){
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);

                    sendReceive = new SendReceive(socket);
                    sendReceive.start();

                    break;
                }
            }
        }

//         public void cancel() {
//             try {
//                 serverSocket.close();
//             } catch (IOException e) {
//                 Log.e("Accept->CloseServer", e.toString());
//             }
//         }


    }

    private class ClientClass extends Thread{

        private BluetoothDevice device;
        private BluetoothSocket socket;

        @SuppressLint("MissingPermission")
        public ClientClass(BluetoothDevice device){
            this.device = device;
            try {
                this.socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @SuppressLint("MissingPermission")
        public void run(){
            try {
                socket.connect();
                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);

                sendReceive = new SendReceive(socket);
                sendReceive.start();

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }

//        public void cancel() {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                Log.e("Accept->CloseServer", e.toString());
//            }
//        }

    }


    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket){
            bluetoothSocket = socket;
            InputStream temInputStream = null;
            OutputStream tempOutputStream = null;

            try {
                temInputStream = bluetoothSocket.getInputStream();
                tempOutputStream = bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = temInputStream;
            outputStream = tempOutputStream;

        }

        public void run(){
            byte[] buffer = new byte[1024];
            int bytes;

            while(true){
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }















}
