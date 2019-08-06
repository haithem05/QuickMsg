package com.example.quickmsg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    FirebaseUser user;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference =  FirebaseDatabase.getInstance().getReference();

        if(user!=null)
            checkUsername();
        else
            sendUserToLogin();

    }
    private void checkUsername(){
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("user_id").getValue()!=null && snapshot.child("user_name").getValue()!=null && snapshot.child("user_picture").getValue()!=null){
                        UserData userData = new UserData(snapshot.child("user_id").getValue().toString(),snapshot.child("user_name").getValue().toString(),snapshot.child("user_picture").getValue().toString());

                        System.out.println(" user id : "+userData.getUser_id());

                        if(userData.getUser_id().equals(user.getUid())){
                            if(userData.getUser_name().equals(""))
                                sendUserToSettings();

                            else
                                sendUserToDashboard();
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendUserToSettings () {
        Intent settings = new Intent(this,SettingsActivity.class);
        settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(settings);
        finish();
    }
    private void sendUserToLogin () {
        Intent login = new Intent(this,LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
        finish();
    }
    private void sendUserToDashboard () {
        Intent dash = new Intent(this,DashboardActivity.class);
        dash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dash);
        finish();
    }
}
