package com.example.chatapp;




import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsFragment extends Fragment {

    View groupView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    Database skyChat;
    ArrayList<String> groupList= new ArrayList<>();


    public GroupsFragment() {
        // Required empty public constructor
    }



    private void setContentView(int fragment_groupchat) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        groupView = inflater.inflate(R.layout.activity_group_chat, container, false);
        skyChat= new Database(getActivity());
        listView = (ListView) groupView.findViewById(R.id.listview_groupchat);
        groupList=skyChat.getGroupNames();


        arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                groupList
        );
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        return  groupView;
    }



}