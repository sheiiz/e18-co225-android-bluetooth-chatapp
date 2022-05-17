package com.example.chatapp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth {

    private BluetoothAdapter bluetoothAdapter;
    public Context context;
    public  DevicesFragment context1;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> adapterPairedDevices, adapterAvailableDevices;
    private ArrayList<String> stringArrayList = new ArrayList<String>();


    public void initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "No bluetooth found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void enableBluetooth() {
        if (bluetoothAdapter.isEnabled()) {
            Toast.makeText(context, "Bluetooth Switched Off", Toast.LENGTH_SHORT).show();
            bluetoothAdapter.disable();

        } else {
            Toast.makeText(context, "Bluetooth Switched On", Toast.LENGTH_SHORT).show();
            bluetoothAdapter.enable();
        }
    }

    @SuppressLint("MissingPermission")
    public ArrayAdapter<String> getPairedDevices(){
        pairedDevices = bluetoothAdapter.getBondedDevices();
        String[] strings = new String[pairedDevices.size()];
        int index = 0;

        if (pairedDevices != null && pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                strings[index] = device.getName();
                index++;
            }
            adapterPairedDevices = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,strings);
        }
        return adapterPairedDevices;
    }


    public ArrayAdapter<String> getAvailableDevices(){
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(bluetoothDeviceListener, intentFilter);
        adapterAvailableDevices = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
        return adapterAvailableDevices;
    }

    @SuppressLint("MissingPermission")
    public void scan(){
        adapterAvailableDevices.clear();

        Toast.makeText(context, "Scan Started", Toast.LENGTH_SHORT).show();

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        bluetoothAdapter.startDiscovery();
    }

    private BroadcastReceiver bluetoothDeviceListener = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                stringArrayList.add(device.getName());
                adapterAvailableDevices.notifyDataSetChanged();
            }
        }
    };

    @SuppressLint("MissingPermission")
    public void makeDiscoverable(){
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300 );
        context.startActivity(discoveryIntent);
    }

    public boolean isBluetoothEnable(){
        return bluetoothAdapter.isEnabled();
    }


}
