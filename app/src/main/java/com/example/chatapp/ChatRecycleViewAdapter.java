package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRecycleViewAdapter extends RecyclerView.Adapter<ChatRecycleViewAdapter.MyViewHolder> {

    ArrayList<String> names;
    Context context;

    public ChatRecycleViewAdapter(ArrayList<String> names, Context context) {
        this.names = names;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view_layout,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.particularUserName.setText(names.get(position));
        String name = names.get(position);
        int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SingleChatActivity.class);
                intent.putExtra("ReceiverName", name);
                intent.putExtra("deviceIndex", Integer.toString(pos));
                intent.putExtra("deviceType", "PreviousChat");
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView particularUserName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            particularUserName = itemView.findViewById(R.id.nameofuser);

        }
    }
}
