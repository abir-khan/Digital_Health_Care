package com.khanabir42gmail.abir.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abir on 13/06/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "contacts.db";
    private final static String TABLE_NAME = "contacts";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_EMAIL = "email";
    private final static String COLUMN_PASSWORD = "password";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_NAME + "( "+COLUMN_ID +
            " INTEGER PRIMARY KEY NOT NULL , "+COLUMN_NAME +" TEXT NOT NULL, "+
            COLUMN_EMAIL +" TEXT NOT NULL, "+COLUMN_PASSWORD +" TEXT NOT NULL )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertContact(Contact c){
        db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT * FROM contacts";
        Cursor cursor = db.rawQuery(query , null);
        int count = cursor.getCount();



        values.put(COLUMN_ID,count);
        values.put(COLUMN_NAME,c.getName());
        values.put(COLUMN_EMAIL,c.getEmail());
        values.put(COLUMN_PASSWORD,c.getPassword());

        db.insert(TABLE_NAME , null , values);
        db.close();
    }

    public String search(String uname){
        db = this.getReadableDatabase();
        String query = "Select name, password from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        String username,password;
        password = "Not Found!!";
        if(cursor.moveToFirst()){
            do {
                username = cursor.getString(0);
                if(username.equals(uname)){
                    password = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return password;

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_CREATE;
        db.execSQL(query);
        this.onCreate(db);
    }
}
