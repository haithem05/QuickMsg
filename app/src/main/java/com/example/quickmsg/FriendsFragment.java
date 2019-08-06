package com.example.quickmsg;


import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {


    RecyclerView recyclerView;
    String current_user_id;
    RelativeLayout user_container;

    DatabaseReference database;
    ArrayList<UserData> users = new ArrayList<UserData>();
    FriendsAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        recyclerView = view.findViewById(R.id.list_of_users);
        user_container = view.findViewById(R.id.user_container);


        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference();







        getFriends();

        mAdapter = new FriendsAdapter(getActivity(),users);
        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        return view;
    }



    public void getFriends(){
        database.child("Users").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String user_id;


                    user_id = String.valueOf(snapshot.getKey());

                    System.out.println("user id  friend "+user_id);
                    if (!user_id.equals(current_user_id) && !userExist(user_id)) {

                        UserData userData = new UserData(user_id,snapshot.child("user_name").getValue().toString(),snapshot.child("user_picture").getValue().toString());
                        users.add(userData);


                        if(mAdapter!=null)
                            mAdapter.notifyDataSetChanged();



                    }



                }
                System.out.println("users count : "+users.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean userExist(String user_id){
        for (int i = 0; i <users.size() ; i++)
            if(users.get(i).getUser_id().equals(user_id))
                return true;

    return false;

    }
}

class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder>{

    ArrayList<UserData> arrayList;
    Context context;
    public FriendsAdapter(Context context, ArrayList<UserData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_layout, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
         UserData userData = arrayList.get(position);

        holder.user_name.setText(userData.getUser_name());


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToChat(context,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        System.out.println("users count : "+arrayList.size());
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, user_id;
        public ImageView user_icon;
        public RelativeLayout container;

        public MyViewHolder(View view) {
            super(view);
            user_name = (TextView) view.findViewById(R.id.user_name);
            container = view.findViewById(R.id.user_container);

        }
    }

    public void sendUserToChat(Context context,int position){
        Intent i = new Intent(context,ChatActivity.class);
        i.putExtra("user_id",arrayList.get(position).getUser_id());
        i.putExtra("user_name",arrayList.get(position).getUser_name());
        i.putExtra("user_picture",arrayList.get(position).getUser_picture());
        context.startActivity(i);

    }
}


