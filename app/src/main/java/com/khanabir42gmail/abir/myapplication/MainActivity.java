package com.khanabir42gmail.abir.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    Button signupBtn;
    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        signupBtn = (Button)findViewById(R.id.signupBtn);
        db = new DatabaseHelper(this);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Signup.class);
                startActivity(i);

            }
        });
    }

    public void onButtonClick(View v){
        EditText user = (EditText)findViewById(R.id.usernameET);
        String userName = user.getText().toString();
        EditText pass = (EditText)findViewById(R.id.passwordET);
        String password = pass.getText().toString();
        user.setError(null);
        pass.setError(null);

        View focusView  =null;
        boolean cancle =false;

        String pass1 = db.search(userName);

        if(!TextUtils.isEmpty(password)&& !isPasswordValid(password)){
            pass.setError("At least 4!!" );
            focusView = pass;
            cancle = true;
        }else if(TextUtils.isEmpty(userName)){
            user.setError("Fill up ");
            focusView = user;
            cancle =true;
        }else if(!isUsernameValid(userName)){
            user.setError("There is no username!!");
            focusView = pass;
            cancle = true;
        } else if(password.equals(pass1)){
            Intent intent = new Intent(MainActivity.this,Welcome.class);
            intent.putExtra("Username",userName);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),"Username and Password doesn't match", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed( ) {
        super.onBackPressed();
    }

    public boolean isPasswordValid(String pass){
        return pass.length() >= 4 ;
    }
    public boolean isUsernameValid(String username){
        return username.length()>6;
    }

}
