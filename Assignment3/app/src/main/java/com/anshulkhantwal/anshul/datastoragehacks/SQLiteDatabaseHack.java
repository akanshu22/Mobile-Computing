package com.anshulkhantwal.anshul.datastoragehacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class SQLiteDatabaseHack extends AppCompatActivity {
    private EditText name, rollno, email, mobno;
    private String name_text, rollno_text, email_text, mobno_text;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlitedatabasehack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.db_toolbar);
        setSupportActionBar(toolbar);

        name_text = "";
        rollno_text = "";
        email_text = "";
        mobno_text = "";

        db = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.db_name);
        email = (EditText) findViewById(R.id.db_email);
        rollno = (EditText) findViewById(R.id.db_rollno);
        mobno = (EditText) findViewById(R.id.db_mobno);
    }

    private void getText() {
        this.rollno_text = rollno.getText().toString();
        this.name_text = name.getText().toString();
        this.mobno_text = mobno.getText().toString();
        this.email_text = email.getText().toString();
    }

    private void resetInput() {
        name.setText("");
        email.setText("");
        mobno.setText("");
        rollno.setText("");
    }

    public void onInsert(View view) {
        getText();
        if (name_text.length() == 0 || mobno_text.length() == 0 || rollno_text.length() == 0 || email_text.length() == 0) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "Please fill in the data completely.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (db.insertStudentRecord(name_text, rollno_text, email_text, Long.valueOf(mobno_text)) == Long.valueOf(-1)) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "Error in inserting the record. The student might already be registered with us.", Snackbar.LENGTH_SHORT).show();
            resetInput();
            return;
        }
        resetInput();
        Snackbar.make(this.findViewById(R.id.db_linear1), "Data have been inserted into the database successfully.", Snackbar.LENGTH_SHORT).show();
    }

    public void onDisplay(View view) {
        if (db.numberOfRows() == 0) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "Error in displaying the records. Make sure atleast one student is registered with us.", Snackbar.LENGTH_SHORT).show();
            resetInput();
            return;
        }
        Intent intent = new Intent(this, DisplayDatabase.class);
        startActivity(intent);
    }

    public void onSearch(View view) {
        getText();
        if (rollno_text.length() == 0) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "Please fill in the Roll No of the student to search.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (db.getSearchData(rollno_text).getCount() == 0) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "No such student record found. Please retry with different Roll No.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, DisplayDatabase.class);
        intent.putExtra(DatabaseHelper.STUDENTS_ROLLNO, rollno_text);
        startActivity(intent);
        resetInput();
    }

    public void onDelete(View view) {
        getText();
        if (rollno_text.length() == 0) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "Please fill in the Roll No of the student to delete the data.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (db.deleteSearchData(rollno_text) == 0) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "Error in deleting the record. Make sure the student is registered with us before continuing.", Snackbar.LENGTH_SHORT).show();
            resetInput();
            return;
        }
        resetInput();
        Snackbar.make(this.findViewById(R.id.db_linear1), "Record has been deleted from out database successfully.", Snackbar.LENGTH_SHORT).show();
    }

    public void onDeleteAll(View view) {
        getText();
        if (db.deleteCompleteData() == 0) {
            Snackbar.make(this.findViewById(R.id.db_linear1), "Error in deleting the table. Make sure atleast one student is registered with us.", Snackbar.LENGTH_SHORT).show();
            resetInput();
            return;
        }
        resetInput();
        Snackbar.make(this.findViewById(R.id.db_linear1), "Records has been deleted from out database successfully.", Snackbar.LENGTH_SHORT).show();
    }
}
