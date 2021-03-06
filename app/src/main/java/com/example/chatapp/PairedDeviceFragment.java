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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PairedDeviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PairedDeviceFragment extends Fragment {

    View fragmentViewPairedDevices;
    ListView listViewPairedDevices;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PairedDeviceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PairedDeviceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PairedDeviceFragment newInstance(String param1, String param2) {
        PairedDeviceFragment fragment = new PairedDeviceFragment();
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
        // Inflate the layout for this fragment
        fragmentViewPairedDevices = inflater.inflate(R.layout.fragment_paired_device, container, false);
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