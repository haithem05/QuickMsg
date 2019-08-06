package com.example.quickmsg;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText message;
    FloatingActionButton send_button;
    String friend_id;
    String friend_name_;
    String friend_picture;
    Toolbar toolbar;

    ImageView friend_icon;
    TextView friend_name;
    MessageAdapter messageAdapter;

    String current_user_name = "";
    ArrayList<ChatMessage> messages ;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.list_of_messages);
        message = findViewById(R.id.message);
        send_button = findViewById(R.id.send_button);
        toolbar = findViewById(R.id.chat_toolbar);
        friend_icon = findViewById(R.id.friend_icon);
        friend_name = findViewById(R.id.friend_name);
        friend_id = getIntent().getStringExtra("user_id");
        friend_name_ = getIntent().getStringExtra("user_name");
        friend_picture = getIntent().getStringExtra("user_picture");
        friend_name.setText(friend_name_);
        friend_icon.setImageResource(R.drawable.iconprofil);

           FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                current_user_name  = dataSnapshot.child("user_name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    messages = new ArrayList<>();

        loadMessages();


        mAdapter = new MessageAdapter(this,messages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.scrollToPosition(mAdapter.getItemCount());

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _message = message.getText().toString();
                if(!_message.isEmpty()){

                    String from_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String to_user_id = friend_id;
                    ChatMessage new_message = new ChatMessage(_message,
                            current_user_name,friend_name_,from_user_id,to_user_id);
                    FirebaseDatabase.getInstance()
                            .getReference("Messages")
                            .push()
                            .setValue(new_message);





                    message.setText("");

                    recyclerView.scrollToPosition(mAdapter.getItemCount());








                }

                Toast.makeText(ChatActivity.this,"sent",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addMessage(final ChatMessage new_message){

        messages.add(new_message);
        mAdapter.notifyDataSetChanged();
    }

    public void loadMessages(){

        DatabaseReference database ;
        final String current_user_id ;
        database =  FirebaseDatabase.getInstance().getReference();
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        database.child("Messages").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    String message_id;
                    String message_text;
                    String from_user;
                    String to_user;
                    String from_user_id;
                    String to_user_id;
                    long message_time;

                    message_id = String.valueOf(snapshot.getKey());
                    System.out.println("message id : "+snapshot.getKey());
                    from_user_id = String.valueOf(snapshot.child("from_user_id").getValue());
                    to_user_id = String.valueOf(snapshot.child("to_user_id").getValue());
                    if(((from_user_id.equals(current_user_id) && to_user_id.equals(friend_id)) || (from_user_id.equals(friend_id) && to_user_id.equals(current_user_id)) ) && !messageExist(message_id)){
                        message_text = String.valueOf(snapshot.child("message_text").getValue());
                        from_user = String.valueOf(snapshot.child("from_user").getValue());
                        to_user = String.valueOf(snapshot.child("to_user").getValue());
                        message_time = Long.parseLong(String.valueOf(snapshot.child("message_time").getValue()));


                        ChatMessage chatMessage = new ChatMessage(message_id,message_text,from_user,to_user,from_user_id,to_user_id,message_time);
                        messages.add(chatMessage);
                        // System.out.println("message : "+snapshot.child("message_text").getValue());
                        System.out.println("message : "+chatMessage.getMessage_text());
                        System.out.println("from : "+chatMessage.getFrom_user_id());

                        if(mAdapter!=null)
                            mAdapter.notifyDataSetChanged();

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public boolean messageExist(String message_id){
        for (int i = 0; i <messages.size() ; i++)
            if(messages.get(i).getMessage_id()!=null)
              if(messages.get(i).getMessage_id().equals(message_id))
                  return true;

    return false;
    }


}
class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{

    ArrayList<ChatMessage> arrayList;
    Context context;
    public MessageAdapter(Context context, ArrayList<ChatMessage> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessage chatMessage = arrayList.get(position);

        holder.message_text.setText(chatMessage.getMessage_text());
        if(new SimpleDateFormat("dd/mm/yyyy").format(new Date(chatMessage.getMessage_time())).equals(new SimpleDateFormat("dd/mm/yyyy").format(new Date())))
           holder.message_time.setText(new SimpleDateFormat("HH:mm").format(new Date(chatMessage.getMessage_time())));
        else
        holder.message_time.setText(new SimpleDateFormat("dd/mm/yyyy HH:mm").format(new Date(chatMessage.getMessage_time())));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message_text, message_time;
        public ImageView message_user;

        public MyViewHolder(View view) {
            super(view);
            message_text = (TextView) view.findViewById(R.id.message_text);
            message_time = (TextView) view.findViewById(R.id.message_time);
//            message_user = view.findViewById(R.id.message_user);
        }
    }
}
