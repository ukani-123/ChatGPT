package com.example.chatgpt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    List<Messsege> messsegeList;
    public MessageAdapter(List<Messsege> messsegeList) {
        this.messsegeList=messsegeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ChatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem,null);
        MyViewHolder myViewHolder = new MyViewHolder(ChatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Messsege messsege = messsegeList.get(position);
        if (messsege.getSentby().equals(Messsege.SENT_BY_ME)){
            holder.LeftChatBoxMessage.setVisibility(View.GONE);
            holder.RightChatBoxMessage.setVisibility(View.VISIBLE);
            holder.RightChatBoxMessage.setText(messsege.getMessage());
       }else{
            holder.RightChatBoxMessage.setVisibility(View.GONE);
            holder.LeftChatBoxMessage.setVisibility(View.VISIBLE);
            holder.LeftChatBoxMessage.setText(messsege.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messsegeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout LeftChatBox,RightChatBox;
        TextView LeftChatBoxMessage,RightChatBoxMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LeftChatBox = itemView.findViewById(R.id.LeftChatBox);
            RightChatBox = itemView.findViewById(R.id.RightChatBox);
            LeftChatBoxMessage = itemView.findViewById(R.id.LeftChatBoxMessage);
            RightChatBoxMessage = itemView.findViewById(R.id.RightChatBoxMessage);
        }
    }

}
