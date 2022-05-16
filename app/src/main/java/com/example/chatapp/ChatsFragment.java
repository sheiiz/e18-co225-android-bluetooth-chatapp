package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    View chatView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    Database skyChat;
    ArrayList<String> chatList= new ArrayList<>();

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chatView = inflater.inflate(R.layout.fragment_blank2, container, false);
        skyChat= new Database(getActivity());
        listView = (ListView) chatView.findViewById(R.id.listview_chats);
        String currentUser= getUserEmail();
        chatList=skyChat.getChatNames(currentUser);


        arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                chatList
        );
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        return  chatView;
    }
}