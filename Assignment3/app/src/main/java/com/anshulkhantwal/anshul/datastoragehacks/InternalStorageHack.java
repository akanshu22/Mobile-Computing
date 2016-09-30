package com.anshulkhantwal.anshul.datastoragehacks;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class InternalStorageHack extends AppCompatActivity {

    EditText data;
    EditText filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internalstorage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.internal_toolbar);
        setSupportActionBar(toolbar);

        data = (EditText) findViewById(R.id.internal_data);
        filename = (EditText) findViewById(R.id.internal_filename);
    }

    public void onSave(View view) {
        if (data.getText().toString().length() <= 0) {
            Snackbar.make(this.findViewById(R.id.internal_linear1), "Please enter some data inorder to save to the internal storage.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (filename.getText().toString().length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", filename.getText())) {
            Snackbar.make(this.findViewById(R.id.internal_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        FileOutputStream outputStream;
        try {
            if (filename.getText().toString().length() == 0) {
                outputStream = openFileOutput("anshul.txt", Context.MODE_PRIVATE);

            } else {
                outputStream = openFileOutput(filename.getText().toString(), Context.MODE_PRIVATE);
            }
            outputStream.write(data.getText().toString().getBytes());
            outputStream.close();
            Snackbar.make(this.findViewById(R.id.internal_linear1), "Data have been saved successfully.", Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {
            return;
        } finally {
            data.setText("");
            filename.setText("");
        }
    }

    public void onDisplay(View view) {
        if (filename.getText().toString().length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", filename.getText())) {
            Snackbar.make(this.findViewById(R.id.internal_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        FileInputStream inputStream;
        if (filename.getText().toString().length() == 0) {
            try {
                FileInputStream finput = openFileInput("anshul.txt");
                int nRead;
                String data_file = "";

                while ((nRead = finput.read()) != -1) {
                    data_file = data_file + Character.toString((char) nRead);
                }
                data.setText(data_file);
            } catch (FileNotFoundException e) {
                Snackbar.make(this.findViewById(R.id.internal_linear1), "File doesn't exist. Please insert the data first in anshul.txt file.", Snackbar.LENGTH_SHORT).show();
                return;
            } catch (IOException e) {
                Snackbar.make(this.findViewById(R.id.internal_linear1), "Problem in reading the File. Please try after some time.", Snackbar.LENGTH_SHORT).show();
                return;
            }
        } else {
            try {
                FileInputStream finput = openFileInput(filename.getText().toString());
                int nRead;
                String data_file = "";

                while ((nRead = finput.read()) != -1) {
                    data_file = data_file + Character.toString((char) nRead);
                }
                data.setText(data_file);
            } catch (FileNotFoundException e) {
                Snackbar.make(this.findViewById(R.id.internal_linear1), "File doesn't exist. Please insert the data first in " + filename.getText().toString() + " file.", Snackbar.LENGTH_SHORT).show();
                return;
            } catch (IOException e) {
                Snackbar.make(this.findViewById(R.id.internal_linear1), "Problem in reading the File. Please try after some time.", Snackbar.LENGTH_SHORT).show();
                return;
            }
        }
        Snackbar.make(this.findViewById(R.id.internal_linear1), "Check the EditText for the output of the file. Thanks.", Snackbar.LENGTH_SHORT).show();
    }

    public void onReset(View view) {
        if (filename.getText().toString().length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", filename.getText())) {
            Snackbar.make(this.findViewById(R.id.internal_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        boolean deleted;
        if (filename.getText().toString().length() == 0) {
            File file = new File(getApplicationContext().getFilesDir(), "anshul.txt");
            deleted = file.delete();
        } else {
            File file = new File(getApplicationContext().getFilesDir(), filename.getText().toString());
            deleted = file.delete();
        }

        if (deleted) {
            Snackbar.make(this.findViewById(R.id.internal_linear1), "Data have been reset in the file specified.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.internal_linear1), "Couldn't reset your data. Please check whether the file really exists of not.", Snackbar.LENGTH_SHORT).show();
        }
        data.setText("");
        filename.setText("");
    }

}
