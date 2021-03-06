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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvailableDeviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableDeviceFragment extends Fragment {

    View fragmentViewAvailableDevices;
    ListView listViewAvailableDevices;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvailableDeviceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvailableDeviceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvailableDeviceFragment newInstance(String param1, String param2) {
        AvailableDeviceFragment fragment = new AvailableDeviceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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