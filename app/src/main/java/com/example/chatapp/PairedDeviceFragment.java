package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class PairedDeviceFragment extends Fragment {

    View fragmentViewPairedDevices;
    ListView listViewPairedDevices;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentViewPairedDevices = inflater.inflate(R.layout.fragment_paired_devices, container, false);
        listViewPairedDevices = fragmentViewPairedDevices.findViewById(R.id.list_paired_devices);

        listViewPairedDevices.setAdapter(MainActivity.myBluetooth.getPairedDevices());


        listViewPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = ((TextView) view).getText().toString();
                Intent intent = new Intent(getActivity(), SingleChatActivity.class);
                intent.putExtra("ReceiverName", name);
                intent.putExtra("deviceIndex", Integer.toString(i));
                intent.putExtra("deviceType", "Paired");
                startActivity(intent);
            }
        });

        return fragmentViewPairedDevices;
    }
}