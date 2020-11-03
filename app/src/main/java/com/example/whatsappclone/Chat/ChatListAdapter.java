package com.example.whatsappclone.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.ChatActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.User.UserObject;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>{
        ArrayList<ChatObject> ChatList;

        public ChatListAdapter(ArrayList<ChatObject> chatList){
                this.ChatList = chatList;
        }

        @NonNull
        @Override
        public ChatListAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null, false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutView.setLayoutParams(lp);

                ChatListViewHolder rcv = new ChatListViewHolder(layoutView);
                return rcv;
        }

        @Override
        public void onBindViewHolder(@NonNull final ChatListAdapter.ChatListViewHolder holder, int position){
                holder.mTitle.setText(ChatList.get(position).getChatId());
                holder.mLayout.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("chatID", ChatList.get(holder.getAdapterPosition()).getChatId());
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);

                        }
                });
        }

        @Override
        public int getItemCount(){
                return ChatList.size();
        }

        public class ChatListViewHolder extends RecyclerView.ViewHolder{
                public TextView mTitle;
                public LinearLayout mLayout;
                public ChatListViewHolder(@NonNull View itemView){
                        super(itemView);
                        mTitle = itemView.findViewById(R.id.title);
                        mLayout = itemView.findViewById(R.id.layout);
                }
        }
}
