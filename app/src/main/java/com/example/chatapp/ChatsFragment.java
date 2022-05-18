package com.example.chatapp;

import static com.example.chatapp.LoginActivity.getUserEmail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    View chatView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private RecyclerView.Adapter chatAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager  layoutManager;

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





        chatView = inflater.inflate(R.layout.fragment_chats, container, false);
        skyChat= new Database(getActivity());
//        listView = (ListView) chatView.findViewById(R.id.listview_chats);
        String currentUser= getUserEmail();
        chatList=skyChat.getChatNames(currentUser);
        ////////////////////////////////////////////////


        recyclerView=chatView.findViewById(R.id.singleChatRecyclerView);



        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatRecycleViewAdapter(chatList, getContext());
        recyclerView.setAdapter(chatAdapter);


////////////////////////////////////////////

    //    skyChat= new Database(getActivity());
//        listView = (ListView) chatView.findViewById(R.id.listview_chats);
    //    String currentUser= getUserEmail();
     //   chatList=skyChat.getChatNames(currentUser);


    //    arrayAdapter = new ArrayAdapter<String>(
    //            getActivity(),
    // //           android.R.layout.simple_list_item_1,
   //             chatList
   //     );
   //     listView.setAdapter(arrayAdapter);
   //     arrayAdapter.notifyDataSetChanged();


/*
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String name = ((TextView) view).getText().toString();
            Intent intent = new Intent(getActivity(), SingleChatActivity.class);
            intent.putExtra("ReceiverName", name);
            intent.putExtra("deviceIndex", Integer.toString(i));
            intent.putExtra("deviceType", "Paired");
            startActivity(intent);

        });
*/


        return  chatView;
    }
}