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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {

    View groupView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    Database skyChat;
    ArrayList<String> groupList= new ArrayList<>();


    public GroupsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        groupView = inflater.inflate(R.layout.group_fragment, container, false);
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

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String name = ((TextView) view).getText().toString();
            Intent intent = new Intent(getActivity(), GroupChatActivity.class);
            intent.putExtra("ReceiverName", name);
            startActivity(intent);

        });

        return  groupView;
    }



}