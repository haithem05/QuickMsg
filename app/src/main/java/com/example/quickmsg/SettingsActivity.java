package com.example.quickmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {


    TextView textView;
    Button set_btn_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        textView = findViewById(R.id.user_name);
        set_btn_profile = findViewById(R.id.set_btn_profile);


        set_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textView.getText().toString().isEmpty()){
                    DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
                    rootRef.child("Users").child(FirebaseAuth.getInstance().getUid()).child("user_name").setValue(textView.getText().toString());
                    sendUserToDashboard();

                }
                else
                    textView.setError("Enter your user name please...");

            }
        });



    }

    private void sendUserToDashboard () {
        Intent dashboardIntent = new Intent(this,DashboardActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dashboardIntent);
        finish();
    }



}
