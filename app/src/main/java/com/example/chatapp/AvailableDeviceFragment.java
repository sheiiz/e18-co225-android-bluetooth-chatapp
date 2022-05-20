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


public class AvailableDeviceFragment extends Fragment {

    View fragmentViewAvailableDevices;
    ListView listViewAvailableDevices;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentViewAvailableDevices = inflater.inflate(R.layout.fragment_available_device, container, false);
        listViewAvailableDevices = fragmentViewAvailableDevices.findViewById(R.id.list_available_devices);
        listViewAvailableDevices.setAdapter(MainActivity.myBluetooth.getAvailableDevices());

        listViewAvailableDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = ((TextView) view).getText().toString();
                Intent intent = new Intent(getActivity(), SingleChatActivity.class);
                intent.putExtra("ReceiverName", name);
                intent.putExtra("deviceIndex", Integer.toString(i));
                intent.putExtra("deviceType", "Available");
                startActivity(intent);
            }
        });

        return fragmentViewAvailableDevices;
    }
}