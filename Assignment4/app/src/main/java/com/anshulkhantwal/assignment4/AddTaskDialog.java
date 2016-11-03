package com.anshulkhantwal.assignment4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by anshul on 2/11/16.
 */

public class AddTaskDialog extends Activity {
    protected EditText title;
    protected EditText desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_dialog);
        title = (EditText) findViewById(R.id.addtask_title);
        desc = (EditText) findViewById(R.id.addtask_desc);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //getWindow().setLayout((int)(width*0.8),(int)(height*0.6));
    }

    public void addTask(View view){
        int flag=0;
        if(desc.getText().toString().trim().isEmpty() || title.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Make sure you input all the enteries!!!",Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("TITLE",title.getText().toString().trim());
        intent.putExtra("DESCRIPTION",desc.getText().toString().trim());
        setResult(1,intent);
        super.finish();
    }
}
