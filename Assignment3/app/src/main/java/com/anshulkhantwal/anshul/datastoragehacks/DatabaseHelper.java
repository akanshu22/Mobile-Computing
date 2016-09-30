package com.anshulkhantwal.anshul.datastoragehacks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anshul on 29/9/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DatabaseHack.db";
    public static final String TABLE_NAME = "students";
    public static final String STUDENTS_NAME = "Name";
    public static final String STUDENTS_ROLLNO = "Roll_No";
    public static final String STUDENTS_EMAILID = "Email_Address";
    public static final String STUDENTS_MOBILENO = "Mobile_No";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE students (Name TEXT, Roll_No TEXT PRIMARY KEY, Email_Address TEXT UNIQUE NOT NULL, Mobile_No INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS students");
        onCreate(sqLiteDatabase);
    }

    public long insertStudentRecord(String name, String rollno, String email, long mobno) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENTS_NAME, name);
        cv.put(STUDENTS_EMAILID, email);
        cv.put(STUDENTS_MOBILENO, mobno);
        cv.put(STUDENTS_ROLLNO, rollno);
        return sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    public Cursor getCompleteData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM students", null);
        return result;
    }

    public Cursor getSearchData(String rollno) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM students WHERE Roll_No = '" + rollno + "'", null);
        return result;
    }

    public int deleteSearchData(String rollno) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "Roll_No = ? ", new String[]{rollno});
    }

    public int deleteCompleteData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, null, null);
    }

    public int numberOfRows() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_NAME);
        return numRows;
    }

}