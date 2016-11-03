package com.anshulkhantwal.assignment4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by anshul on 2/11/16.
 */

public class ShowTaskDialog extends Activity {
    protected AppCompatTextView title;
    protected AppCompatTextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_task_dialog);
        title = (AppCompatTextView) findViewById(R.id.showtaskdialog_title);
        desc = (AppCompatTextView) findViewById(R.id.showtaskdialog_description);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        title.setText(getIntent().getStringExtra("TITLE"));
        desc.setText(getIntent().getStringExtra("DESCRIPTION"));
        //getWindow().setLayout((int)(width*0.8),(int)(height*0.6));
    }
}