package com.anshulkhantwal.myquizassignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

public class Hint extends AppCompatActivity {
    private final String LOG="MyQuiz2App_Hint";
    private String Question;
    private String Answer;
    private int checkHint;
    private String[] Hints;
    private final String tag1="QUESTION",tag2="ANS",tag3="CHECK";
    private TextView ques,hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i(LOG,"onCreate Called");

        // Display icon in the toolbar
        getSupportActionBar().setLogo(R.mipmap.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ques=(TextView) findViewById(R.id.tv_ques_hint);
        hint=(TextView) findViewById(R.id.tv_hint);
        Question=getIntent().getStringExtra(MainActivity.QUESTION);
        Answer=getIntent().getStringExtra(MainActivity.ANSWER);
        Hints=new String[5];
        Hints[0]="Check for divisibility by all the factors.";
        Hints[1]="Check for all the factors less than sqrt(number).";
        Hints[2]="Do check whether the given number is even or odd.";
        Hints[3]="Prime number is a natural number greater than 1 having no positive divisors other than 1 and itself.";
        Hints[4]="Zero and 1 are not considered prime numbers.";
        Random a=new Random();
        Answer=Hints[a.nextInt(5)];
        ques.setText(Question);
        checkHint=0;
    }

    public void onGetHint(View view){
        hint.setText(Answer);
        Snackbar.make(view,"Check for hint above.",Snackbar.LENGTH_SHORT).show();
        checkHint=1;
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
        hint.setText(savedInstanceState.getString(tag2));
        checkHint=savedInstanceState.getInt(tag3);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG,"onSaveInstanceState Called");
        outState.putString(tag1,Question);
        outState.putString(tag2,Answer);
        outState.putInt(tag3,checkHint);
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
        intent.putExtra("HINTSTATUS",checkHint);
        setResult(3,intent);
        super.finish();
    }
}