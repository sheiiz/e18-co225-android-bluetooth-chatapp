package com.example.chatapp;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class DevicesFragment extends Fragment {

    private View devicesFragmentView;
    Button scan_btn, visibility_btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        devicesFragmentView = inflater.inflate(R.layout.fragment_devices, container, false);
        scan_btn = devicesFragmentView.findViewById(R.id.btn_scan);
        visibility_btn = devicesFragmentView.findViewById(R.id.btn_visibility);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.myBluetooth.isBluetoothEnable()){
                    MainActivity.myBluetooth.scan();
                }
                else{
                    Toast.makeText(MainActivity.myBluetooth.context, "Bluetooth is not enabled", Toast.LENGTH_SHORT).show();
                }

            }
        });

        visibility_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.myBluetooth.makeDiscoverable();
            }
        });

        return devicesFragmentView;
    }
}