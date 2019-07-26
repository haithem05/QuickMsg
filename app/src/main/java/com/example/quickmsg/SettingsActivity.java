package com.example.quickmsg;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private Button bnt_setting;
    private EditText hey,userName;
    private CircleImageView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Initialized();
    }

    private void Initialized() {
        bnt_setting=(Button) findViewById(R.id.set_btn_profile);
        hey=(EditText) findViewById(R.id.set_profile);
        userName=(EditText) findViewById(R.id.user_profile);
        profile=(CircleImageView) findViewById(R.id.profile_image);

    }


}
