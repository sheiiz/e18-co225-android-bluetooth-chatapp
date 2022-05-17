package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAccessAdapter extends FragmentPagerAdapter {
    public TabAccessAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                ChatsFragment chatsFragment= new ChatsFragment();
                return chatsFragment;

            case 1:
                GroupsFragment groupsFragment= new GroupsFragment();
                return groupsFragment;

            case 2:
                DevicesFragment devicesFragment=new DevicesFragment();
                return devicesFragment;

            default:
                return null;

        }


    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Chats";
            case 1:
                return "Group Chats";
            case 2:
                return "Devices";
            default:
                return null;

        }
    }
}
