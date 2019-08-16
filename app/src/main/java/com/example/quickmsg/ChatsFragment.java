package com.example.quickmsg;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {


    RecyclerView recyclerView;
    String current_user_id;

    ArrayList<UserData> users = new ArrayList<UserData>();

    DatabaseReference database;
    ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

    ChatAdapter  mAdapter ;
    LinearLayoutManager mLayoutManager ;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.list_of_messages);
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database =  FirebaseDatabase.getInstance().getReference();


        database.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for ( DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    System.out.println(" snap shot :"+snapshot.getKey());
                    if(!snapshot.getKey().equals(current_user_id) && snapshot.child("user_id").getValue()!=null){
                        UserData userData = new UserData();
                        userData.setUser_id(snapshot.child("user_id").getValue().toString());
                        userData.setUser_name(snapshot.child("user_name").getValue().toString());

                        System.out.println("ouss : "+snapshot.child("user_name").getValue().toString());
                        users.add(userData);

                    }

                }



                database.child("Messages").addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (int i = 0; i <users.size() ; i++) {
                             ChatMessage last_message = new ChatMessage();
                        for (final DataSnapshot snapshot: dataSnapshot.getChildren()) {


                            final ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);




                                if(chatMessage.getfrom_user()!=null ){

                                    if((chatMessage.getFrom_user_id().equals(current_user_id) && chatMessage.getTo_user_id().equals(users.get(i).getUser_id()))|| (chatMessage.getFrom_user_id().equals(users.get(i).getUser_id()) && chatMessage.getTo_user_id().equals(current_user_id))){

                                        last_message.setMessage_id(chatMessage.getMessage_id());
                                        last_message.setfrom_user(chatMessage.getfrom_user());
                                        last_message.setFrom_user_id(chatMessage.getFrom_user_id());
                                        last_message.setTo_user_id(chatMessage.getTo_user_id());
                                        last_message.setMessage_text(chatMessage.getMessage_text());
                                        last_message.setMessage_time(chatMessage.getMessage_time());

                                    }

                                }
                            }

                            if(last_message.getMessage_text()!=null){

                                if(chatMessages.size()!=0){
                                    for (int j = 0; j <chatMessages.size() ; j++) {
                                        System.out.println("index j "+j);
                                        if(((chatMessages.get(j).getFrom_user_id().equals(last_message.getFrom_user_id()) && chatMessages.get(j).getTo_user_id().equals(last_message.getTo_user_id())) ||
                                                (chatMessages.get(j).getFrom_user_id().equals(last_message.getTo_user_id()) && chatMessages.get(j).getTo_user_id().equals(last_message.getFrom_user_id())))){
                                            chatMessages.remove(j);
                                            chatMessages.add(last_message);

                                        }else{
                                            chatMessages.add(last_message);

                                        }

                                    }
                                }else{
                                    chatMessages.add(last_message);

                                }

                            }



                        }


                          mAdapter = new ChatAdapter(getActivity(),chatMessages,current_user_id,users);
                         mLayoutManager = new LinearLayoutManager(getActivity());

                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view;


    }
    public boolean messageExists(String message_id){
        for (int i = 0; i <chatMessages.size() ; i++)
            if(chatMessages.get(i).getMessage_id().equals(message_id))
                return true;

        return false;
    }
}



class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    ArrayList<ChatMessage> arrayList;
    ArrayList<UserData> users;
    Context context;
    String current_user_id;
    public ChatAdapter(Context context, ArrayList<ChatMessage> arrayList,String current_user_id,    ArrayList<UserData> users) {
        this.arrayList=arrayList;
        this.context=context;
        this.current_user_id=current_user_id;
        this.users=users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.last_message_layout, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ChatMessage chatMessage = arrayList.get(position);

        holder.message_text.setText(chatMessage.getMessage_text());
        holder.message_time.setText(new SimpleDateFormat("dd/mm/yyyy HH:mm").format(new Date(chatMessage.getMessage_time())));
        if(!chatMessage.getFrom_user_id().equals(current_user_id)){
            if(chatMessage.getfrom_user().isEmpty())
                holder.user_name.setText(chatMessage.getTo_user());

            else
               holder.user_name.setText(chatMessage.getfrom_user());
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendUserToChat(context,arrayList.get(position).getFrom_user_id(),getUserName(arrayList.get(position).getFrom_user_id()));
                }
            });
        }
        else{

            if(chatMessage.getTo_user().isEmpty())
                holder.user_name.setText(chatMessage.getfrom_user());
            else
                holder.user_name.setText(chatMessage.getTo_user());
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendUserToChat(context,arrayList.get(position).getTo_user_id(),getUserName(arrayList.get(position).getTo_user_id()));
                }
            });

        }


    }

    public String getUserName(String user_id){
        for (int i = 0; i <users.size() ; i++) {
            System.out.println("user ouss :" +users.get(i).getUser_name()+" user id :"+user_id+" x : "+users.get(i).getUser_id());

            if(users.get(i).getUser_id().equals(user_id)){
                return users.get(i).getUser_name();

            }
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message_text, message_time;
        public TextView user_name;
        public ImageView message_user;
        public RelativeLayout container;

        public MyViewHolder(View view) {
            super(view);
            message_time = (TextView) view.findViewById(R.id.message_time);
            message_user =  view.findViewById(R.id.message_user);
            user_name =  view.findViewById(R.id.user_name);
            message_text = (TextView) view.findViewById(R.id.message_text);
            container =  view.findViewById(R.id.user_container);

        }
    }

    public void sendUserToChat(Context context,String user_id,String user_name){
        Intent i = new Intent(context,ChatActivity.class);
        i.putExtra("user_id",user_id);
        i.putExtra("user_name",user_name);

        System.out.println("user id : "+user_id+" user name : "+user_name);
        context.startActivity(i);

    }
}