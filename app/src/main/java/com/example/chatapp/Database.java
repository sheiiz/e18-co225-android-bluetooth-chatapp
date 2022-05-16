package com.example.chatapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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

    public Boolean insertUSER(String USERNAME,String PASSWORD){
        SQLiteDatabase skyChatDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",USERNAME);
        contentValues.put("PASSWORD",PASSWORD);
        long result = skyChatDB.insert("USERS",null,contentValues);
        if(result==-1) return false;
        else return true;
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
