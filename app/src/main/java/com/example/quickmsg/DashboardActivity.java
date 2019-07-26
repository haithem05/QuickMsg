package com.example.quickmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

       mAuth =FirebaseAuth.getInstance();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionn, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            sendUserToLogin();}
            if (item.getItemId() == R.id.setings) {
                sendUserToSettings();
            }

            if (item.getItemId() == R.id.FindFriends) {

            }


            return true;

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




    private void sendUserToLogin() {
        Intent loginIntent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
    private void sendUserToSettings() {
        Intent settingsIntent = new Intent(DashboardActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
        finish();
    }


}