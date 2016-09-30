package com.anshulkhantwal.anshul.datastoragehacks;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisplayDatabase extends AppCompatActivity {

    private EditText name, rollno, email, mobno;
    private DatabaseHelper db;
    private String rollno_text;
    private Cursor cursor;
    private Button prev, next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.display_name);
        email = (EditText) findViewById(R.id.display_email);
        rollno = (EditText) findViewById(R.id.display_rollno);
        mobno = (EditText) findViewById(R.id.display_mobno);
        prev = (Button) findViewById(R.id.display_prev);
        next = (Button) findViewById(R.id.display_next);

        if (getIntent().hasExtra(DatabaseHelper.STUDENTS_ROLLNO)) {
            rollno_text = getIntent().getStringExtra(DatabaseHelper.STUDENTS_ROLLNO);
            cursor = db.getSearchData(rollno_text);
            cursor.moveToFirst();
            name.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_NAME)));
            rollno.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_ROLLNO)));
            email.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_EMAILID)));
            mobno.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_MOBILENO)));
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        } else {
            cursor = db.getCompleteData();
            cursor.moveToFirst();
            name.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_NAME)));
            rollno.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_ROLLNO)));
            email.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_EMAILID)));
            mobno.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.STUDENTS_MOBILENO)));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onNextRecord(View view) {
        if (!cursor.isLast()) {
            cursor.moveToNext();
            name.setText(cursor.getString(0));
            rollno.setText(cursor.getString(1));
            email.setText(cursor.getString(2));
            mobno.setText(cursor.getString(3));
            Snackbar.make(this.findViewById(R.id.display_linear1), "Next Record fetched successfully.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.display_linear1), "Last Record in the search results.", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onPreviousRecord(View view) {
        if (!cursor.isFirst()) {
            cursor.moveToPrevious();
            name.setText(cursor.getString(0));
            rollno.setText(cursor.getString(1));
            email.setText(cursor.getString(2));
            mobno.setText(cursor.getString(3));
            Snackbar.make(this.findViewById(R.id.display_linear1), "Previous Record fetched successfully.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.display_linear1), "First Record in the search results.", Snackbar.LENGTH_SHORT).show();
        }
    }
}
