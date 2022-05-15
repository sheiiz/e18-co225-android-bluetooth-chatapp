package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabAccessAdapter myTabAccessAdapter;
    Database skyChatDB;
    Button login,register;
    EditText email,password;

    Bluetooth myBluetooth = new Bluetooth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skyChatDB = new Database(this);


        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("ChatApp");

        myViewPager = findViewById(R.id.main_tabs_pager);
        myTabAccessAdapter = new TabAccessAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabAccessAdapter);

        myTabLayout = findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

        myBluetooth.context = this;
        myBluetooth.initBluetooth();

        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.register_button);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
    }
    //@Override
    //protected void onStart() {
     //   super.onStart();
      //  if(true) {
      //      sendUserToLoginActivity();
      //  }
    //}

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.main_create_group_option){
           // AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);
           // builder.setTitle();
            Intent groupIntent = new Intent(MainActivity.this,CreateGroupActivity.class);
            startActivity(groupIntent);
            return true;
        }

        if(item.getItemId()==R.id.main_settings_option){
            Intent settingsIntent=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(settingsIntent);
        }

        if(item.getItemId()==R.id.main_exit_option){
           // mAuth.signOut();
            sendUserToLoginActivity();
            return true;
        }

        if(item.getItemId() == R.id.main_bluetooth_turn_on_off){
            myBluetooth.enableBluetooth();
        }

        return super.onOptionsItemSelected(item);
    }


    }
