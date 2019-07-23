package com.example.quickmsg;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //mAuth =FirebaseAuth.getInstance();

    }


  /*  @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendUserToFirstActivity();
        }

        else
        {

        }
    }   */







    private void sendUserToFirstActivity() {

        Intent firstActIntent = new Intent(DashboardActivity.this,MainActivity.class);
        startActivity(firstActIntent);
        finish();
    }
}
