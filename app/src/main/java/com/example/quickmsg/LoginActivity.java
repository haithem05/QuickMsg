package com.example.quickmsg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;




public class LoginActivity extends AppCompatActivity {
    private Button login ;
    private EditText email , password ;
    private TextView needNewAcount;
    private FirebaseAuth nAuth;

    private ProgressDialog LoadingnBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null)
            sendUserToDashboard();

        initializeFields();

        needNewAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToSignup();


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });


    }

    private void AllowUserToLogin() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        if(Email.isEmpty() || Password.isEmpty()){
            if (Email.isEmpty())
                Toast.makeText(this, "please enter email....", Toast.LENGTH_SHORT).show();


            if (Password.isEmpty())
                Toast.makeText(this, "please enter password....", Toast.LENGTH_SHORT).show();


        } else {

            startLoading();

            FirebaseApp.initializeApp(this);
            nAuth = FirebaseAuth.getInstance();

            nAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if (task.isSuccessful()) {


                                                       Toast.makeText(LoginActivity.this, "Account Created successfully...", Toast.LENGTH_SHORT).show();
                                                       LoadingnBar.dismiss();
                                                       sendUserToDashboard();
                                                   } else {
                                                       String message = task.getException().toString();
                                                       Toast.makeText(LoginActivity.this, "Error :" + message, Toast.LENGTH_SHORT).show();

                                                       System.out.println("error msg 1 : "+message);
                                                       LoadingnBar.dismiss();
                                                   }

                                               }

                                           }
                    );

        }




    }

    private void sendUserToDashboard () {
        Intent dashboardIntent = new Intent(LoginActivity.this,DashboardActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dashboardIntent);
        finish();
    }





































    private void sendUserToSignup () {
        Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }


    private void initializeFields () {

        login = (Button) findViewById(R.id.login_btn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        needNewAcount = (TextView) findViewById(R.id.need_new_account_link);
        LoadingnBar = new ProgressDialog(this);


    }

    private void startLoading(){
        LoadingnBar.setTitle("Sign in");
        LoadingnBar.setMessage("please wait .....");
        LoadingnBar.setCanceledOnTouchOutside(true);
        LoadingnBar.show();
    }
}
