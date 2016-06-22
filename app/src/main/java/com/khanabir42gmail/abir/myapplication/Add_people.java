package com.khanabir42gmail.abir.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_people extends AppCompatActivity {
    EditText nameField;
    EditText ageField;
    EditText heightField;
    EditText weightField;


    private Boolean isEdit = false;
    private int index = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        Bundle bundle = getIntent().getExtras();
        nameField = (EditText) findViewById(R.id.nameET);
        ageField = (EditText) findViewById(R.id.ageET);
        heightField = (EditText) findViewById(R.id.heightET);
        weightField = (EditText) findViewById(R.id.weightET);

        Button save = (Button) findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit){
                    Intent intent = new Intent();
                    intent.putExtra(Welcome.NAME,nameField.getText().toString());
                    intent.putExtra(Welcome.AGE,ageField.getText().toString());
                    intent.putExtra(Welcome.HEIGHT,heightField.getText().toString());
                    intent.putExtra(Welcome.WEIGHT,weightField.getText().toString());
                    intent.putExtra(Welcome.ISEDIT,isEdit);
                    intent.putExtra(Welcome.ID,index);
                    setResult(Welcome.REG_RESULT_CODE,intent);



                }else{
                    //adding
                    Intent intent = new Intent();
                    intent.putExtra(Welcome.NAME,nameField.getText().toString());
                    intent.putExtra(Welcome.AGE,ageField.getText().toString());
                    intent.putExtra(Welcome.HEIGHT,heightField.getText().toString());
                    intent.putExtra(Welcome.WEIGHT,weightField.getText().toString());

                    //now set the flag back to show_member_List
                    intent.putExtra(Welcome.ISEDIT,isEdit);
                    setResult(Welcome.REG_RESULT_CODE,intent);

                }
                finish();

            }
        });





        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (bundle != null){
            isEdit =bundle.getBoolean(Welcome.ISEDIT)  ;
            if (isEdit){

                index = bundle.getInt(Welcome.ID);
                nameField.setText(bundle.getString(Welcome.NAME));
                ageField.setText(bundle.getString(Welcome.AGE));
                heightField.setText(bundle.getString(Welcome.HEIGHT));
                weightField.setText(bundle.getString(Welcome.WEIGHT));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
