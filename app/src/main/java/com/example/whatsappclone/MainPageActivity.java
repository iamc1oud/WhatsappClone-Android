package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.whatsappclone.Chat.ChatListAdapter;
import com.example.whatsappclone.Chat.ChatObject;
import com.example.whatsappclone.User.UserListAdapter;
import com.example.whatsappclone.User.UserObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {
    private Button mLogout, mFindUser;
    private RecyclerView mChatList;
    private RecyclerView.Adapter mChatListAdapter;
    private RecyclerView.LayoutManager mChatListLayoutManager;
    ArrayList<ChatObject> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mLogout = findViewById(R.id.logout);
        mFindUser = findViewById(R.id.findUser);

        mFindUser.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), FindUserActivity.class));
            }
        });

        getPermissions();
        initaliseRecyclerView();
        getUserChatList();

        mLogout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check for permission before seeing contact details
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS}, 1);
        }
    }

    private void getUserChatList(){
        DatabaseReference mUserChatDB = FirebaseDatabase.getInstance()
                .getReference()
                .child("user")
                .child(FirebaseAuth.getInstance().getUid())
                .child("chat");

        // addValueEventListener keeps listening to the changes in data snapshot
        mUserChatDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                    for(DataSnapshot childSnapshot: snapshot.getChildren()){
                        ChatObject mChat = new ChatObject(childSnapshot.getKey());
                        chatList.add(mChat);
                        mChatListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
    }

    @SuppressLint("WrongConstant")
    private void initaliseRecyclerView(){
        mChatList = findViewById(R.id.chatList);
        mChatList.setNestedScrollingEnabled(false);
        mChatList.setHasFixedSize(false);
        mChatListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mChatList.setLayoutManager(mChatListLayoutManager);
        mChatListAdapter = new ChatListAdapter(chatList);
        mChatList.setAdapter(mChatListAdapter);
    }
}