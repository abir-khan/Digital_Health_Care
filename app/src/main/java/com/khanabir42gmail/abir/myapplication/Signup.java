package com.khanabir42gmail.abir.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText userEditText;
    EditText emailEditText;
    EditText passEditText;
    EditText confarmPassEditText;
    Button create; 

    DatabaseHelper db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DatabaseHelper(this);
    }

    public void onCreateBtn(View v) {
        if (v.getId() == R.id.createBtn) {
            userEditText = (EditText) findViewById(R.id.usernameET);
            emailEditText = (EditText) findViewById(R.id.emailET);
            passEditText = (EditText) findViewById(R.id.passwordET);
            confarmPassEditText = (EditText) findViewById(R.id.confarmpassET);
            create = (Button) findViewById(R.id.createBtn);

            String user = userEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String bpass = passEditText.getText().toString();
            String apass = confarmPassEditText.getText().toString();

            if (!bpass.equals(apass)) {
                Toast.makeText(Signup.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            } else {
                Contact contact = new Contact();
                contact.setName(user);
                contact.setEmail(email);
                contact.setPassword(bpass);

                db.insertContact(contact);

                Toast.makeText(Signup.this, "Password match", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
