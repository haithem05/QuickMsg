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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText email, password,confirm;
    private Button SignUpButton ;
    private TextView alreadyHaveAccount;
    private FirebaseAuth nAuth;
    private ProgressDialog LoadingnBar;
    private DatabaseReference rootRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialize();


           alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToDashboard();
            }
        });
           SignUpButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   CreateNewAcount();
               }
           });
    }






    private void CreateNewAcount() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        if(Email.isEmpty() || Password.isEmpty()){

            if(Email.isEmpty())
                Toast.makeText(this,"please enter email...." ,Toast.LENGTH_SHORT).show();


            if(Password.isEmpty())
                Toast.makeText(this,"please enter password...." ,Toast.LENGTH_SHORT).show();

        }else {

            startLoading();

            FirebaseApp.initializeApp(this);
            nAuth = FirebaseAuth.getInstance();
            nAuth.createUserWithEmailAndPassword(Email,Password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        rootRef= FirebaseDatabase.getInstance().getReference();
                        String currentUserID=nAuth.getCurrentUser().getUid();
                        rootRef.child("Users").child(currentUserID).setValue("");

                        sendUserToDashboard();
                        Toast.makeText(SignUpActivity.this, "Account Created successfully...", Toast.LENGTH_SHORT).show();
                        LoadingnBar.dismiss();
                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(SignUpActivity.this, "Error :" + message, Toast.LENGTH_SHORT).show();

                        System.out.println("MSG : "+message);
                        LoadingnBar.dismiss();
                    }
                }
                     });

        }



    }


    private void sendUserToDashboard () {
        Intent dashboardIntent = new Intent(SignUpActivity.this,DashboardActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dashboardIntent);
        finish();
    }

    private void initialize()
    {

        SignUpButton= (Button) findViewById(R.id.signup_btn);
        email = (EditText) findViewById(R.id.emaill) ;
        password = (EditText) findViewById(R.id.passwordd);
        alreadyHaveAccount = (TextView) findViewById(R.id.already_have_acount);
        confirm = (EditText) findViewById(R.id.confirm);
        LoadingnBar=new ProgressDialog(this);


    }

    private void startLoading(){
        LoadingnBar.setTitle("Creating a new Account");
        LoadingnBar.setMessage("please wait,while we are creating new account for you .....");
        LoadingnBar.setCanceledOnTouchOutside(true);
        LoadingnBar.show();
    }

}


