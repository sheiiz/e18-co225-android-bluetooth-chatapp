package com.example.chatapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class Bluetooth {

    private BluetoothAdapter bluetoothAdapter;
    public Context context;


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
}
