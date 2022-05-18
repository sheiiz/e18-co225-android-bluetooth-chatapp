package com.example.chatapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.fragment.app.Fragment;



import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
    public static final String DBNAME = "SkyChat.db";
    public static int chatID = 0;


    public Database(Context context) {

        super(context, "SkyChat.db", null, 1);
    }
    public Database() {
        super(null, "SkyChat.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase skyChatDB) {
        skyChatDB.execSQL("create Table GROUPCHATS(GROUPNAME TEXT PRIMARY KEY, GROUPADMIN TEXT)");
        skyChatDB.execSQL("create Table USERS(USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)");
        skyChatDB.execSQL("create Table CHATS(CHATID INT PRIMARY KEY ,USER TEXT , FRIEND TEXT )");
        skyChatDB.execSQL("create Table MESSAGES(MSGID INT PRIMARY KEY, CHATID INT ,TYPE TEXT , MSG TEXT , FOREIGN KEY (CHATID) REFERENCES CHATS(CHATID))");

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
    public Boolean checkGROUPNAME (String GROUP_NAME){

        SQLiteDatabase skyChatDB = this.getWritableDatabase();

        Cursor cursor = skyChatDB.rawQuery("SELECT * FROM GROUPCHATS WHERE GROUPNAME = ?",new String[] {GROUP_NAME});
        if(cursor.getCount()>0){return true;}
        else return false;

    }
    public Boolean insertGroup(String GROUP_NAME,String GROUP_ADMIN){
        SQLiteDatabase skyChatDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("GROUPNAME",GROUP_NAME);
        contentValues.put("GROUPADMIN",GROUP_ADMIN);
        long result = skyChatDB.insert("GROUPCHATS",null,contentValues);
        if(result==-1) return false;
        else return true;




    }

    public  ArrayList<String>  getGroupNames(){
        SQLiteDatabase skyChatDB = this.getWritableDatabase();
      ArrayList<String> groupList = new ArrayList<>();
        String query = "SELECT * FROM GROUPCHATS";
        Cursor cursor = skyChatDB.rawQuery(query,null);
      while (cursor.moveToNext()){

         groupList.add(cursor.getString(0));

      }
      return  groupList;
    }
    public  ArrayList<String>  getChatNames(String CURRENT_USER){
        SQLiteDatabase skyChatDB = this.getWritableDatabase();

        ArrayList<String> chatList = new ArrayList<>();
        String query = "SELECT * FROM CHATS";
        Cursor cursor = skyChatDB.rawQuery(query,null);
        while (cursor.moveToNext()){
            if(cursor.getString(1).equals(CURRENT_USER)){
            chatList.add(cursor.getString(2));
            }
        }
        return  chatList;
    }


    public Boolean checkFRIEND(String FRIEND) {
        SQLiteDatabase skyChatDB = this.getWritableDatabase();
        Cursor cursor = skyChatDB.rawQuery("SELECT * FROM CHATS WHERE FRIEND = ?",new String[] {FRIEND});
        if(cursor.getCount()>0){return true;}
        else return false;
    }

    public Boolean insertChat(String user, String friend) {
        SQLiteDatabase skyChatDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        chatID++;
        contentValues.put("CHATID",chatID);
        contentValues.put("USER",user);
        contentValues.put("FRIEND",friend);

        Cursor cursor = skyChatDB.rawQuery("SELECT * FROM CHATS WHERE  FRIEND = ? AND USER = ?",new String[] {friend,user});
        if(cursor.getCount()>0){return true;}

        long result = skyChatDB.insert("CHATS",null,contentValues);
        if(result==-1) return false;
        else {
            chatID++;
            return true;

        }

    }

    public Boolean insertMsg(int msgID,int chatID, String type,String msg) {
        SQLiteDatabase skyChatDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("MSGID",msgID);
        contentValues.put("CHATID",chatID);
        contentValues.put("TYPE",type);
        contentValues.put("MSG",msg);

        long result = skyChatDB.insert("MESSAGES",null,contentValues);
        if(result==-1) return false;
        else return true;
    }
    public ArrayList<Messages> getMsg(int chatID) {
        SQLiteDatabase skyChatDB = this.getReadableDatabase();
        ArrayList<Messages> messageArrayList = new ArrayList<>();


        String query = "SELECT * FROM MESSAGES ";
        Cursor cursor = skyChatDB.rawQuery(query,null);
        while (cursor.moveToNext()){
            Messages msg;
            if(cursor.getString(1).equals(chatID)){
                msg = new Messages(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3));
                messageArrayList.add(msg);
            }
        }
        return  messageArrayList;
    }



    public Boolean updateChats(String email,String new_email) {
        SQLiteDatabase skyChatDB = this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USER", new_email);

        db.update("CHATS", values, "USER =?", new String[]{email});

        return true;
    }

    public Boolean updateGroupAdmin(String email, String new_email) {
        SQLiteDatabase skyChatDB = this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("GROUPADMIN", new_email);




        db.update("GROUPCHATS", values, "GROUPADMIN =?", new String[]{email});

        return true;
    }
    int value;
    public int getCHATID(String user, String receiverName) {
        SQLiteDatabase skyChatDB = this.getWritableDatabase();

        Cursor cursor = skyChatDB.rawQuery("SELECT * FROM CHATS WHERE USER = ? AND FRIEND = ?",new String[] {user,receiverName});
        while (cursor.moveToNext()){
            value= cursor.getInt(0);
        }
        return value;

    }
}