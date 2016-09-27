package com.anshulkhantwal.myquizassignment2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class Cheat extends AppCompatActivity {

    private final String LOG="MyQuiz2App_Cheat";
    private String Question;
    private String Answer;
    private int cheatCheck;
    private final String tag1="QUESTION",tag2="ANS",tag3="CHECK";
    private TextView ques,cheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i(LOG,"onCreate Called");

        // Display icon in the toolbar
        getSupportActionBar().setLogo(R.mipmap.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ques=(TextView) findViewById(R.id.tv_ques_cheat);
        cheat=(TextView) findViewById(R.id.tv_cheat);
        Question=getIntent().getStringExtra(MainActivity.QUESTION);
        Answer=getIntent().getStringExtra(MainActivity.ANSWER);
        if(Answer.equals("true")){
            Answer="Yes, It is a prime number.";
        }else{
            Answer="No, It's not a prime number.";
        }
        ques.setText(Question);
        cheatCheck=0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onGetCheat(View view){
        Log.i(LOG,"onGetCheat Called");
        cheat.setText(Answer);
        cheatCheck=1;
        Snackbar.make(view,"Check for cheat answer above.",LENGTH_SHORT).show();
    }

    public void goBack(View view){
        Log.i(LOG,"goBack Called");
        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(LOG,"onRestoreInstanceState Called");
        ques.setText(savedInstanceState.getString(tag1));
        cheat.setText(savedInstanceState.getString(tag2));
        cheatCheck=savedInstanceState.getInt(tag3);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG,"onSaveInstanceState Called");
        outState.putString(tag1,Question);
        outState.putString(tag2,Answer);
        outState.putInt(tag3,cheatCheck);
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

    @Override
    public void finish() {
        Log.i(LOG,"finish Called");
        Intent intent=new Intent();
        intent.putExtra("CHEATSTATUS",cheatCheck);
        setResult(2,intent);
        super.finish();
    }
}