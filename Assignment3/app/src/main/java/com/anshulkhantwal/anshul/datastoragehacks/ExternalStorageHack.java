package com.anshulkhantwal.anshul.datastoragehacks;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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

public class ExternalStorageHack extends AppCompatActivity {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private EditText data, filename;
    private String text_data, text_filename;
    private String private_dir, shared_dir;

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_externalstorage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.external_toolbar);
        setSupportActionBar(toolbar);

        verifyStoragePermissions(this);
        data = (EditText) findViewById(R.id.external_data);
        filename = (EditText) findViewById(R.id.external_filename);
        text_data = "";
        text_filename = "";
        private_dir = "Private_Anshul";
        shared_dir = "Shared_Anshul";
    }

    private void getText() {
        text_filename = filename.getText().toString();
        text_data = data.getText().toString();
    }

    private void clearText() {
        data.setText("");
        filename.setText("");
    }

    public Boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public Boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public void onSavePrivate(View view) {
        getText();
        if (text_data.length() <= 0) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter some data inorder to save to the Private External Storage.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (text_filename.length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", text_filename)) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (isExternalStorageWritable()) {
            File file1, file2;
            file1 = new File(getExternalFilesDir(null).toString(), private_dir);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            if (text_filename.length() == 0)
                file2 = new File(file1.getPath() + File.separatorChar + "anshul.txt");
            else
                file2 = new File(file1.getPath() + File.separatorChar + text_filename);
            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
                    return;
                }
            }

            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file2.getAbsolutePath());
                outputStream.write(text_data.getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
            } catch (IOException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
            }

            Snackbar.make(this.findViewById(R.id.external_linear1), "Data have been saved successfully to Private External Storage.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.external_linear1), "External Storage is not Writable at the moment. Please try after some time.", Snackbar.LENGTH_SHORT).show();
        }
        clearText();
    }

    public void onSaveShared(View view) {
        getText();
        if (text_data.length() <= 0) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter some data inorder to save to the Private External Storage.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (text_filename.length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", text_filename)) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (isExternalStorageWritable()) {
            File file1, file2;
            file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), shared_dir);
            file1.mkdirs();

            if (text_filename.length() == 0) {
                file2 = new File(file1.getPath() + "/anshul.txt");
            } else {
                file2 = new File(file1.getPath() + "/" + text_filename);
            }
            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
                    return;
                }
            }

            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file2.getAbsolutePath());
                outputStream.write(text_data.getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
            } catch (IOException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
            }

            Snackbar.make(this.findViewById(R.id.external_linear1), "Data have been saved successfully to Shared External Storage.", Snackbar.LENGTH_SHORT).show();
            clearText();
        } else {
            Snackbar.make(this.findViewById(R.id.external_linear1), "External Storage is not Writable at the moment. Please try after some time.", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onDisplayPrivate(View view) {
        getText();
        if (text_filename.length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", text_filename)) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (isExternalStorageReadable()) {
            File file1, file2;
            FileInputStream inputStream;
            file1 = new File(getExternalFilesDir(null), private_dir);
            if (!file1.exists()) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
            }
            if (text_filename.length() == 0) {
                file2 = new File(file1.getAbsolutePath() + File.separatorChar + "anshul.txt");
            } else {
                file2 = new File(file1.getAbsolutePath() + File.separatorChar + text_filename);
            }

            System.out.println(file2.getAbsolutePath());
            if (!file2.exists()) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            try {
                inputStream = new FileInputStream(file2.getAbsolutePath());
                int nRead;
                String data_file = "";

                while ((nRead = inputStream.read()) != -1) {
                    data_file = data_file + Character.toString((char) nRead);
                }

                data.setText(data_file);
            } catch (FileNotFoundException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
                return;
            } catch (IOException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem in reading/writing the File. Please try after some time.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            Snackbar.make(this.findViewById(R.id.external_linear1), "Check the EditText for the output of the file. Thanks.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.external_linear1), "External Storage is not Readable/Writable at the moment. Please try after some time.", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onDisplayShared(View view) {
        getText();
        if (text_filename.length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", text_filename)) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (isExternalStorageReadable()) {
            File file1, file2;
            FileInputStream inputStream;
            file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), shared_dir);
            if (!file1.exists()) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
            }
            if (text_filename.length() == 0) {
                file2 = new File(file1.getPath() + "/anshul.txt");
            } else {
                file2 = new File(file1.getPath() + "/" + text_filename);
            }

            if (!file2.exists()) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            try {
                inputStream = new FileInputStream(file2.getAbsolutePath());
                int nRead;
                String data_file = "";

                while ((nRead = inputStream.read()) != -1) {
                    data_file = data_file + Character.toString((char) nRead);
                }

                data.setText(data_file);
            } catch (FileNotFoundException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem with accessing your External Storage. Please try after some time.", Snackbar.LENGTH_LONG).show();
                return;
            } catch (IOException e) {
                Snackbar.make(this.findViewById(R.id.external_linear1), "Problem in reading/writing the File. Please try after some time.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            Snackbar.make(this.findViewById(R.id.external_linear1), "Check the EditText for the output of the file. Thanks.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.external_linear1), "External Storage is not Readable/Writable at the moment. Please try after some time.", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void onDeletePrivate(View view) {
        getText();
        if (text_filename.length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", text_filename)) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (isExternalStorageWritable()) {
            File file;
            if (text_filename.length() == 0) {
                file = new File(getExternalFilesDir(null).getPath() + File.separatorChar + private_dir + File.separatorChar + "anshul.txt");
                if (!file.exists()) {
                    Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                file.delete();
            } else {
                file = new File(getExternalFilesDir(null).getPath() + File.separatorChar + private_dir + File.separatorChar + text_filename);
                if (!file.exists()) {
                    Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                file.delete();
            }
            Snackbar.make(this.findViewById(R.id.external_linear1), "Your file has been deleted successfully. Thanks for your patience.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.external_linear1), "External Storage is not Writable at the moment. Please try after some time.", Snackbar.LENGTH_SHORT).show();
        }
        clearText();
    }

    public void onDeleteShared(View view) {
        getText();
        if (text_filename.length() > 0 && !Pattern.matches("[a-zA-Z][a-zA-Z0-9]*(\\.txt)", text_filename)) {
            Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename.", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (isExternalStorageWritable()) {
            File file;
            if (text_filename.length() == 0) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + File.separatorChar + shared_dir + File.separatorChar + "anshul.txt");
                if (!file.exists()) {
                    Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                file.delete();
            } else {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + File.separatorChar + shared_dir + File.separatorChar + text_filename);
                if (!file.exists()) {
                    Snackbar.make(this.findViewById(R.id.external_linear1), "Please enter a valid .txt filename. Make sure the file already exists.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                file.delete();
            }
            Snackbar.make(this.findViewById(R.id.external_linear1), "Your file has been deleted successfully. Thanks for your patience.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(this.findViewById(R.id.external_linear1), "External Storage is not Writable at the moment. Please try after some time.", Snackbar.LENGTH_SHORT).show();
        }
        clearText();
    }

}
