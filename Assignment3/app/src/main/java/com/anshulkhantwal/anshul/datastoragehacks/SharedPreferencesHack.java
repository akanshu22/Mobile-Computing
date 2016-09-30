package com.anshulkhantwal.anshul.datastoragehacks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPreferencesHack extends AppCompatActivity {

    public static final String MYSHAREDPREFERENCES = "shared_preferences";
    public static final String NAME = "name";
    public static final String ROLLNO = "roll_no";
    public static final String MOBNO = "mobile_no";
    public static final String EMAIL = "email";
    SharedPreferences shared_preferences;
    EditText edit_name, edit_rollno, edit_mobno, edit_email;
    TextView view_name, view_rollno, view_mobno, view_email, status;
    Button btn_save, btn_reset;
    String name, rollno, email, mobno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpreferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.shared_toolbar);
        setSupportActionBar(toolbar);
        shared_preferences = getSharedPreferences(MYSHAREDPREFERENCES, Context.MODE_PRIVATE);

        edit_name = (EditText) findViewById(R.id.shared_name_notsave);
        edit_rollno = (EditText) findViewById(R.id.shared_rollno_notsave);
        edit_email = (EditText) findViewById(R.id.shared_email_notsave);
        edit_mobno = (EditText) findViewById(R.id.shared_mobno_notsave);

        view_name = (TextView) findViewById(R.id.shared_name_onsave);
        view_rollno = (TextView) findViewById(R.id.shared_rollno_onsave);
        view_email = (TextView) findViewById(R.id.shared_email_onsave);
        view_mobno = (TextView) findViewById(R.id.shared_mobno_onsave);

        status = (TextView) findViewById(R.id.shared_text_status);

        btn_save = (Button) findViewById(R.id.shared_save);
        btn_reset = (Button) findViewById(R.id.shared_reset);

        if (shared_preferences.contains(NAME) && !btn_reset.isShown()) {
            name = shared_preferences.getString(NAME, null);
            rollno = shared_preferences.getString(ROLLNO, null);
            email = shared_preferences.getString(EMAIL, null);
            mobno = shared_preferences.getString(MOBNO, null);

            btn_reset.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.INVISIBLE);
            status.setText("(Your Shared Preferences have been listed below)");

            edit_name.setVisibility(View.INVISIBLE);
            edit_email.setVisibility(View.INVISIBLE);
            edit_mobno.setVisibility(View.INVISIBLE);
            edit_rollno.setVisibility(View.INVISIBLE);

            view_email.setText(email);
            view_name.setText(name);
            view_mobno.setText(mobno);
            view_rollno.setText(rollno);

            view_email.setVisibility(View.VISIBLE);
            view_mobno.setVisibility(View.VISIBLE);
            view_name.setVisibility(View.VISIBLE);
            view_rollno.setVisibility(View.VISIBLE);
        }
    }

    public void onSave(View view) {
        SharedPreferences.Editor editor = shared_preferences.edit();

        name = edit_name.getText().toString();
        rollno = edit_rollno.getText().toString();
        email = edit_email.getText().toString();
        mobno = edit_mobno.getText().toString();

        editor.putString(NAME, name);
        editor.putString(ROLLNO, rollno);
        editor.putString(EMAIL, email);
        editor.putString(MOBNO, mobno);
        editor.commit();

        btn_reset.setVisibility(View.VISIBLE);
        btn_save.setVisibility(View.INVISIBLE);
        status.setText("(Your Shared Preferences have been listed below)");

        edit_name.setVisibility(View.INVISIBLE);
        edit_email.setVisibility(View.INVISIBLE);
        edit_mobno.setVisibility(View.INVISIBLE);
        edit_rollno.setVisibility(View.INVISIBLE);

        view_email.setText(email);
        view_name.setText(name);
        view_mobno.setText(mobno);
        view_rollno.setText(rollno);

        view_email.setVisibility(View.VISIBLE);
        view_mobno.setVisibility(View.VISIBLE);
        view_name.setVisibility(View.VISIBLE);
        view_rollno.setVisibility(View.VISIBLE);
    }

    public void onReset(View view) {
        shared_preferences.edit().clear().commit();

        btn_reset.setVisibility(View.INVISIBLE);
        btn_save.setVisibility(View.VISIBLE);
        status.setText("(You haven't stored your shared preferences yet)");

        edit_name.setVisibility(View.VISIBLE);
        edit_email.setVisibility(View.VISIBLE);
        edit_mobno.setVisibility(View.VISIBLE);
        edit_rollno.setVisibility(View.VISIBLE);

        view_email.setVisibility(View.INVISIBLE);
        view_mobno.setVisibility(View.INVISIBLE);
        view_name.setVisibility(View.INVISIBLE);
        view_rollno.setVisibility(View.INVISIBLE);
    }

}