package com.anshulkhantwal.anshul.datastoragehacks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "anshul16010@iiitd.ac.in", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MyQuiz App Reports and Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Feel free to express how you feel about our app?\n\n");
                startActivity(Intent.createChooser(emailIntent, "Provide us feedback at:"));
            }
        });
    }

    public void onSharedPreferences(View view) {
        Intent intent = new Intent(this, SharedPreferencesHack.class);
        startActivity(intent);

    }

    public void onInternalStorage(View view) {
        Intent intent = new Intent(this, InternalStorageHack.class);
        startActivity(intent);
    }

    public void onExternalStorage(View view) {
        Intent intent = new Intent(this, ExternalStorageHack.class);
        startActivity(intent);
    }

    public void onDatabase(View view) {
        Intent intent = new Intent(this, SQLiteDatabaseHack.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
