package com.example.chatapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DBNAME = "SkyChat.db";

    public Database( Context context) {
        super(context, "SkyChat.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase skyChatDB) {
        skyChatDB.execSQL("create Table USERS(USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase skyChatDB, int i, int i1) {
        skyChatDB.execSQL("DROP Table if exists USERS");

    }
    public Boolean insertUser(String USERNAME,String PASSWORD){
        SQLiteDatabase skyChatDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",USERNAME);
        contentValues.put("PASSWORD",PASSWORD);
        long result = skyChatDB.insert("USERS",null,contentValues);
        if(result==-1) return false;
        else return true;




    }
    public Boolean updateUser(String USERNAME,String NEW_USERNAME,String NEW_PASSWORD){
        SQLiteDatabase skyChatDB = this.getReadableDatabase();
       // ContentValues contentValues = new ContentValues();
       // contentValues.put("USERNAME",NEW_USERNAME);
       // contentValues.put("PASSWORD",NEW_PASSWORD);

       // long result = skyChatDB.update("USERS",contentValues,"USERNAME=?",new String[] {USERNAME});
       // if(result==-1) return false;
      //  else return true;

        //SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("UPDATE USERS SET USERNAME = " +"'"+ NEW_USERNAME +"' "+" AND  PASSWORD = " + "'"+NEW_PASSWORD+"' "+ "WHERE USERNAME = "+"'"+USERNAME+"'");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("USERNAME", NEW_USERNAME);
        values.put("PASSWORD", NEW_PASSWORD);


        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update("USERS", values, "USERNAME=?", new String[]{USERNAME});

        return true;




    }
    public Boolean checkUSERNAME (String USERNAME){
        SQLiteDatabase skyChatDB = this.getWritableDatabase();
        Cursor cursor = skyChatDB.rawQuery("SELECT * FROM USERS WHERE USERNAME = ?",new String[] {USERNAME});
        if(cursor.getCount()>0){return true;}
        else return false;

    }
    public Boolean checkPASSWORD(String USERNAME,String PASSWORD){
        SQLiteDatabase skyChatDB = this.getWritableDatabase();
        Cursor cursor = skyChatDB.rawQuery("SELECT * FROM USERS WHERE USERNAME = ? and PASSWORD = ?",new String[] {USERNAME,PASSWORD});
        if(cursor.getCount()>0){
            return true;

        }
        else{
            return false;
        }
    }
}
