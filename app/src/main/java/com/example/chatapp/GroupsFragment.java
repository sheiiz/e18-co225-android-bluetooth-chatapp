package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class GroupsFragment extends Fragment {
    View chatView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter chatAdapter;
    private LinearLayoutManager layoutManager;
    Database skyChat;
    ArrayList<String> groupList= new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chatView=inflater.inflate(R.layout.fragment_group_chats,container,false);
        skyChat= new Database(getActivity());
//        listView = (ListView) chatView.findViewById(R.id.listview_chats);

        groupList=skyChat.getGroupNames();
        recyclerView=chatView.findViewById(R.id.groupleChatRecyclerView);




        /* Put chat names taken from the database instead of this part */
       // names.add("Shehan"); names.add("Madhusanka"); names.add("Kasun"); names.add("Nimal Peris Iphone");


        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatRecycleViewAdapter(groupList, getContext());
        recyclerView.setAdapter(chatAdapter);

        return chatView;
    }
}