package com.khanabir42gmail.abir.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Welcome extends AppCompatActivity {
    private ArrayList<HashMap<String,String >> names = new ArrayList<HashMap<String, String>>();
    private ListView listView;
    private SimpleAdapter adapter;

    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String ISEDIT = "is_Edit";
    public static final String ID = "_index";


    public static int REG_REQ_CODE = 55;
    public static int REG_RESULT_CODE = 56;
    public static final int EDIT = 111;
    public static final  int DELETE = 112;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        String[] from = {NAME,AGE,HEIGHT,WEIGHT};
        int [] to = {R.id.nameTV,R.id.ageTV,R.id.heightTV,R.id.heightTV};
        listView = (ListView) findViewById(R.id.nameLV);
        adapter = new SimpleAdapter(this,names,R.layout.person,from,to);
        listView.setAdapter(adapter);


        registerForContextMenu(listView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Do you want to ?");
        menu.add(menu.NONE,EDIT,0,"Edit");
        menu.add(menu.NONE,DELETE,0,"Delete");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case EDIT:
                Intent intent = new Intent(this,Add_people.class);
                intent.putExtra(ISEDIT,true);
                intent.putExtra(ID,info.position);
                intent.putExtra(NAME,names.get(info.position).get(NAME));
                intent.putExtra(AGE,names.get(info.position).get(AGE));
                intent.putExtra(HEIGHT,names.get(info.position).get(HEIGHT));
                intent.putExtra(WEIGHT,names.get(info.position).get(WEIGHT));
                startActivityForResult(intent, REG_REQ_CODE);
                break;
            case DELETE:
                names.remove(info.position);
                adapter.notifyDataSetChanged();
                break;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_person,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.add){

            Intent intent = new Intent(this,Add_people.class);
            intent.putExtra(ISEDIT,false);
            //setting on ISEDIT flag ,when the activity is launched its going to be new data insert
            //so that's way we set ISEDIT ,false
            startActivityForResult(intent, REG_REQ_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REG_REQ_CODE){
            if(resultCode == REG_RESULT_CODE){
                Bundle bundle = data.getExtras();
                if (bundle != null){
                    Boolean isEdit = bundle.getBoolean(ISEDIT);
                    HashMap<String , String> hm = new HashMap<String, String>();
                    hm.put(NAME,bundle.getString(NAME));
                    hm.put(AGE,bundle.getString(AGE));
                    hm.put(HEIGHT,bundle.getString(HEIGHT));
                    hm.put(WEIGHT,bundle.getString(WEIGHT));
                    if (isEdit){
                        //edit
                        names.set(bundle.getInt(ID),hm);
                    }else{
                        names.add(hm);
                    }
                    adapter.notifyDataSetChanged();

                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
