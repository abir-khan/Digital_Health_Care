package com.khanabir42gmail.abir.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by abir on 21/06/2016.
 */
public class DBHelper_person extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "person.db";
    public static final String TABLE_NAME = "person";

    public static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";

    SQLiteDatabase db;

    public DBHelper_person(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_NAME +"( "+
            COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COLUMN_NAME +
            " TEXT NOT NULL , "+COLUMN_AGE +" TEXT NOT NULL , "+COLUMN_HEIGHT+
            " TEXT NOT NULL , "+COLUMN_WEIGHT+" TEXT NOT NULL ) ";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate(db);
    }

    //insert a person into a SQLite Database
    public long insertPeople(Person person){
        //Open Connection to Write data
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //Collect data from person class and represent it as a pair
        values.put(COLUMN_NAME,person.getName());
        values.put(COLUMN_AGE,person.getAge());
        values.put(COLUMN_HEIGHT,person.getHeight());
        values.put(COLUMN_WEIGHT,person.getWeight());

        long insert = db.insert(TABLE_NAME,null,values);
        db.close();
        return  insert;

    }

    public void delete(int person_id){
        db = this.getWritableDatabase();
        //// It's a good practice to use parameter ?, instead of concatenate string
        db.delete(TABLE_NAME,COLUMN_ID + "= ?",new String[]{String.valueOf(person_id)});
        db.close();//Close the SQLite connection
    }

    public void update(Person person){
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME,person.getName());
        values.put(COLUMN_AGE,person.getAge());
        values.put(COLUMN_HEIGHT,person.getHeight());
        values.put(COLUMN_WEIGHT,person.getWeight());

        db.update(TABLE_NAME,values,COLUMN_ID+ "= ?",new String[]{String.valueOf(person.getId())});
        db.close();
    }

    public ArrayList<HashMap<String,String>> getPersonList(){
        db = this.getReadableDatabase();

        String selectQuery = "SELECT "+COLUMN_ID+" , "+COLUMN_NAME+" , "+COLUMN_NAME+" , "
                +COLUMN_AGE+" , "+COLUMN_HEIGHT+" , "+COLUMN_WEIGHT+" FROM "+TABLE_NAME;

        ArrayList<HashMap<String,String>> personList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> person = new HashMap<String, String>();
                person.put("id",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                person.put("name",cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                /*person.put("age",cursor.getString(cursor.getColumnIndex(COLUMN_AGE)));
                person.put("height",cursor.getString(cursor.getColumnIndex(COLUMN_HEIGHT)));
                person.put("weight",cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT)));
                */
                personList.add(person);
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return personList;
    }

    public Person getById(int id){
        db = this.getReadableDatabase();

        String selectQuery = "SELECT "+COLUMN_ID+" , "+COLUMN_NAME+" , "+COLUMN_NAME+" , "
                +COLUMN_AGE+" , "+COLUMN_HEIGHT+" , "+COLUMN_WEIGHT+" FROM "+TABLE_NAME+" WHERE "+
                COLUMN_ID +" =?";

        int iCount = 0;
        Person person = new Person();

        Cursor cursor = db.rawQuery(selectQuery,new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){
            do{
                person.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                person.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                person.age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));
                person.height = cursor.getString(cursor.getColumnIndex(COLUMN_HEIGHT));
                person.weight = cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return person;

    }


}
