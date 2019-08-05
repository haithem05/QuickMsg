package com.example.quickmsg;


import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {


    ListView listView;
    String current_user_id;

    DatabaseReference database;
    ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        listView = view.findViewById(R.id.list_of_messages);
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database =  FirebaseDatabase.getInstance().getReference();


        database.child("Messages").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                     String message_text;
                     String from_user;
                     String to_user;
                     String from_user_id;
                     String to_user_id;
                     long message_time;

                    from_user_id = String.valueOf(snapshot.child("from_user_id").getValue());
                    to_user_id = String.valueOf(snapshot.child("to_user_id").getValue());
                    if(from_user_id.equals(current_user_id) || to_user_id.equals(current_user_id)){
                        message_text = String.valueOf(snapshot.child("message_text").getValue());
                        from_user = String.valueOf(snapshot.child("from_user").getValue());
                        to_user = String.valueOf(snapshot.child("to_user").getValue());
                        message_time = Long.parseLong(String.valueOf(snapshot.child("message_time").getValue()));


                        ChatMessage chatMessage = new ChatMessage(message_text,from_user,to_user,from_user_id,to_user_id,message_time);
                        chatMessages.add(chatMessage);
                        // System.out.println("message : "+snapshot.child("message_text").getValue());
                        System.out.println("message : "+chatMessage.getMessage_text());
                        System.out.println("from : "+chatMessage.getFrom_user_id());

                        ChatAdapter chatAdapter = new ChatAdapter(getActivity(), chatMessages);

                        System.out.println("adapter chat : "+chatAdapter);
                        listView.setAdapter(chatAdapter);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;


    }
}

class ChatAdapter implements ListAdapter{

    ArrayList<ChatMessage> arrayList;
    Context context;
    public ChatAdapter(Context context, ArrayList<ChatMessage> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = arrayList.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.message_layout, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView message = convertView.findViewById(R.id.message_text);
            message.setText(chatMessage.getMessage_text());

        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}