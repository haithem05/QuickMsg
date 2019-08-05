package com.example.quickmsg;


import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
public class FriendsFragment extends Fragment {


    ListView listView;
    String current_user_id;
    RelativeLayout user_container;

    DatabaseReference database;
    ArrayList<UserData> users = new ArrayList<UserData>();

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        listView = view.findViewById(R.id.list_of_users);
        user_container = view.findViewById(R.id.user_container);
        listView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);


        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference();


        database.child("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String user_id;


                    user_id = String.valueOf(snapshot.getKey());

                    if (user_id.equals(current_user_id)) {
                        UserData userData = new UserData(user_id);
                        users.add(userData);


                        UserAdapter userAdapter = new UserAdapter(getActivity(), users);

                        listView.setAdapter(userAdapter);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendUserToChat(position);

                System.out.println("user position : "+position);
            }

        });



        return view;
    }

    class UserAdapter implements ListAdapter {

        ArrayList<UserData> arrayList;
        Context context;

        public UserAdapter(Context context, ArrayList<UserData> arrayList) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
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
            UserData userData = arrayList.get(position);
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.user_layout, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                TextView user_name = convertView.findViewById(R.id.user_name);
                user_name.setText(userData.getUser_id());

            }
            convertView.setClickable(false);

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

    public void sendUserToChat(int position){
        Intent i = new Intent(getActivity(),ChatActivity.class);
        i.putExtra("user_id",users.get(position).getUser_id());
        startActivity(i);

    }
}
