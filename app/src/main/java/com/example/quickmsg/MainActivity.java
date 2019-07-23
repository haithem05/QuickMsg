package com.example.quickmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button login_btn, signup_btn;
  /*  private Toolbar ktoolbar;
    private ViewPager myViewPager;
    private TableLayout myTabLayout;
    private TabsAccesessorAdapter myTabsAccesessorAdapter ;*/
    /*private FirebaseUser currentUser;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);

       /* nAuth = FirebaseAuth.getInstance();
        CurrentUser = nAuth.getCurrentUser();*/
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
               /* myTabsAccesessorAdapter = new TabsAccesessorAdapter(getSupportFragmentManager()) ;
                ktoolbar=(Toolbar) findViewById( R.id.app_toolbar);
                setSupportActionBar(ktoolbar);
                setTitle("QuickMsg");*/
              /*  myTabsAccesessorAdapter = new TabsAccesessorAdapter(getSupportFragmentManager()) ;
                myViewPager.setAdapter(myTabsAccesessorAdapter);
                TableLayout tabLayout = myTabLayout = (TableLayout) findViewById(R.id.tab_layout);
                //tabLayout.setupWithViewPager(myViewPager);//
                ktoolbar=(Toolbar) findViewById( R.id.app_toolbar);
                myViewPager = (ViewPager) findViewById(R.id.tab_pager);

                setSupportActionBar(ktoolbar);
                setTitle("QuickMsg");*/

            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);

            }

        });


    }

   /* @Override
    protected void onStart() {
        super.onStart();
        if (currentUser==null){
            sendUserToSingUupActivity();
        }
    }*/

  /*  private void sendUserToSingUupActivity() {
        Intent loginIntent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(loginIntent);
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.logout){

        }
        if (item.getItemId()== R.id.setings){

        }

        if (item.getItemId()== R.id.FindFriends){

        }


        return true;
    }
}