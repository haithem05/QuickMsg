package com.example.quickmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddAccountActivity extends AppCompatActivity {
    private TextView userName ;
    private Button addAccount;

    private ImageView userIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
       Initialized();
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToSignup();


            }
        });
    }

    private void Initialized() {
        userName = findViewById(R.id.user_name);
        addAccount = findViewById(R.id.addAcount);
        userIcon = findViewById(R.id.user_icon);
    }

    private void sendUserToSignup () {
        Intent addaccountIntent = new Intent(AddAccountActivity.this, SignUpActivity.class);
        startActivity(addaccountIntent);
    }

}
