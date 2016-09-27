package com.anshulkhantwal.myquizassignment2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private TextView question;
    private RadioGroup userAns;
    private int score;
    private Boolean ans;
    private final String tag1="SCORE",tag2="ANS",tag3="QUESTION";
    private final String LOG="MyQuiz2App_MainActivity";
    public final static String QUESTION = "com.anshulkhantwal.myquizassignment2.QUESTION";
    public final static String ANSWER = "com.anshulkhantwal.myquizassignment2.ANSWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i(LOG,"onCreate Called");

        // Display icon in the toolbar
        getSupportActionBar().setLogo(R.mipmap.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        question=(TextView)findViewById(R.id.tv_question);
        userAns=(RadioGroup)findViewById(R.id.radioGroup);
        score=0;
        ans=getQuestion();
    }

    public void onRestartScore(View view){
        Log.i(LOG,"onRestartScore Called");
        score=0;
        ans=getQuestion();
        Snackbar.make(view,"Your Score has been reset to 0.",LENGTH_LONG).show();
    }

    public void onCheckScore(View view){
        Log.i(LOG,"onCheckScore Called");
        Snackbar.make(view,"Your current score is "+score+".", LENGTH_LONG).show();
    }

    public void onSkip(View view){
        Log.i(LOG,"onSkip Called");
        ans=getQuestion();
        Snackbar.make(view,"Moving on to new question.", LENGTH_SHORT).show();
    }

    public void onAnswerSubmit(View view){
        Log.i(LOG,"onAnswerSubmit Called");
        if((userAns.getCheckedRadioButtonId()==R.id.btn_yes && ans) || (userAns.getCheckedRadioButtonId()==R.id.btn_no && !ans)){
            score++;
            Snackbar.make(view,"Correct!!!",LENGTH_SHORT).show();
            ans=getQuestion();
        }
        else{
            Snackbar.make(view,"Wrong Answer!!!",LENGTH_SHORT).show();
            ans=getQuestion();
        }
    }

    private Boolean getQuestion() {
        Log.i(LOG,"getQuestion Called");
        Random b = new Random();
        int a = b.nextInt(1000)+1;
        question.setText("Is "+a+" a prime number?");
        return checkPrime(a);
    }

    private Boolean checkPrime(int a){
        if(a==1)
            return false;
        int i=2;
        int f=0;
        for(;i<=Math.sqrt(a);i++)
            if(a%i==0){
                f=1;
                break;
            }
        return f != 1;
    }

    public void sendEmail(View view){
        Log.i(LOG,"sendEmail Called");
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","anshul16010@iiitd.ac.in", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MyQuiz App Reports and Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Feel free to express how you feel about our app?\n\n");
        startActivity(Intent.createChooser(emailIntent, "Provide us feedback at:"));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(LOG,"onRestoreInstanceState Called");
        score=savedInstanceState.getInt(tag1);
        ans=savedInstanceState.getBoolean(tag2);
        question.setText(savedInstanceState.getString(tag3));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG,"onSaveInstanceState Called");
        outState.putInt(tag1,score);
        outState.putBoolean(tag2,ans);
        outState.putString(tag3,question.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(LOG,"onCreateOptionsMenu Called");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG,"onOptionsItemSelected Called");
        int id = item.getItemId();
        if (id == R.id.menu_exit) {
            finish();
        }
        if(id == R.id.menu_help){
            Intent intent = new Intent("com.anshulkhantwal.myquizassignment2.Help");
            this.startActivity(intent);
        }
        if(id == R.id.menu_cheat){
            Intent intent = new Intent("com.anshulkhantwal.myquizassignment2.Cheat");
            intent.putExtra(QUESTION, question.getText().toString());
            intent.putExtra(ANSWER, ans.toString());
            //this.startActivity(intent);
            startActivityForResult(intent,2);
        }
        if(id == R.id.menu_hint){
            Intent intent = new Intent("com.anshulkhantwal.myquizassignment2.Hint");
            intent.putExtra(QUESTION, question.getText().toString());
            intent.putExtra(ANSWER, ans.toString());
            startActivityForResult(intent,3);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG,"onActivityResult Called");
        //super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == 2) {
            Log.i(LOG,"Inside If Called");
            // Make sure the request was successful
            if (resultCode == 2) {
                int cheatStatus=data.getIntExtra("CHEATSTATUS",2);
                if(cheatStatus==1){
                    Snackbar.make(findViewById(R.id.Relativelayout2),"You have cheated!!!",LENGTH_SHORT).show();
                }
                else if(cheatStatus==0){
                    Snackbar.make(findViewById(R.id.Relativelayout2),"You have not cheated!!!",LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == 3) {
            Log.i(LOG,"Inside If Called");
            // Make sure the request was successful
            if (resultCode == 3) {
                int hintStatus=data.getIntExtra("HINTSTATUS",2);
                if(hintStatus==1){
                    Snackbar.make(findViewById(R.id.Relativelayout2),"You have used hints!!!",LENGTH_SHORT).show();
                }
                else if(hintStatus==0){
                    Snackbar.make(findViewById(R.id.Relativelayout2),"You have not used any hints!!!",LENGTH_SHORT).show();
                }
            }
        }
    }
}
