package com.anshulkhantwal.myquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Help extends AppCompatActivity {

    private final String LOG="MyQuizApp_Help";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i(LOG,"onCreate Called");

        // Display icon in the toolbar
        getSupportActionBar().setLogo(R.mipmap.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    public void goBack(View view){
        Log.i(LOG,"goBack Called");
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG,"onCreateOptionsMenu Called");
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG,"onOptionsItemSelected Called");
        int id = item.getItemId();
        if(id == R.id.menu_help){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}