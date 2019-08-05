package com.example.quickmsg;

import android.content.Context;
import android.database.DataSetObserver;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    ListView listView;
    EditText message;
    FloatingActionButton send_button;
    String friend_id;
    Toolbar toolbar;

    ImageView friend_icon;
    TextView friend_name;
    MessageAdapter messageAdapter;

    ArrayList<ChatMessage> messages ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = findViewById(R.id.list_of_messages);
        message = findViewById(R.id.message);
        send_button = findViewById(R.id.send_button);
        toolbar = findViewById(R.id.chat_toolbar);
        friend_icon = findViewById(R.id.friend_icon);
        friend_name = findViewById(R.id.friend_name);
        friend_id = getIntent().getStringExtra("user_id");
        friend_name.setText(friend_id);
        friend_icon.setImageResource(R.drawable.iconprofil);

        messages = new ArrayList<>();

        messageAdapter = new MessageAdapter(this, messages);
         messages.add(new ChatMessage("1","1","1","1","1"));

        listView.setAdapter(messageAdapter);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _message = message.getText().toString();
                if(!_message.isEmpty()){

                    String from_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String to_user_id = friend_id;
                    ChatMessage new_message = new ChatMessage(_message,
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getDisplayName(),friend_id,from_user_id,to_user_id);
                    FirebaseDatabase.getInstance()
                            .getReference("Messages")
                            .push()
                            .setValue(new_message);

                    message.setText("");

                   addMessage(new_message);


                }

                Toast.makeText(ChatActivity.this,"sent",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addMessage(final ChatMessage new_message){

    }




}
class MessageAdapter implements ListAdapter{

    ArrayList<ChatMessage> arrayList;
    Context context;
    public MessageAdapter(Context context, ArrayList<ChatMessage> arrayList) {
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
