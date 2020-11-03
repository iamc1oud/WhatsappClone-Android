package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.whatsappclone.Chat.MessageAdapter;
import com.example.whatsappclone.Chat.MessageObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mChat;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private Button mSend;
    private EditText mMessage;
    ArrayList<MessageObject> messageList = new ArrayList();

    String chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mSend = findViewById(R.id.sendMessageBtn);
        mMessage = findViewById(R.id.message);
        mSend.setOnClickListener(v -> sendMessage());

        // Get the chatID passed in intent in ChatListAdapter
        chatID = getIntent().getExtras().getString("chatID");
        initializeRecyclerView();
    }

    private void sendMessage(){
        if(!mMessage.getText().toString().isEmpty()){
            DatabaseReference newMessageDB = FirebaseDatabase.getInstance().getReference().child("chat").child(chatID).push();
            Map newMessage = new HashMap();
            newMessage.put("text", mMessage.getText().toString());
            newMessage.put("creator", FirebaseAuth.getInstance().getCurrentUser().getUid());

            newMessageDB.updateChildren(newMessage);
            mMessage.setText(null);
        }
    }

    @SuppressLint("WrongConstant")
    private void initializeRecyclerView(){
        mChat = findViewById(R.id.messageList);
        mChat.setHasFixedSize(false);
        mChat.setNestedScrollingEnabled(false);
        mChatLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mChat.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new MessageAdapter(messageList);
        mChat.setAdapter(mChatAdapter);

    }
}