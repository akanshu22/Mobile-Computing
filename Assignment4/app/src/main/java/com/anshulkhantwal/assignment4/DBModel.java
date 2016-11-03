package com.anshulkhantwal.assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anshul on 16/10/16.
 */
public class DBModel extends SQLiteOpenHelper {

    protected static final String DB_NAME = "ToDoList.db";
    protected static final String TABLE_NAME = "Tasks";
    protected static final String TABLE_ID = "ID";
    protected static final String TABLE_TITLE = "Title";
    protected static final String TABLE_DESC = "Description";


    public DBModel(Context context, int version) {
        super(context,context.getExternalFilesDir(null).getAbsolutePath().toString()+"/"+ DB_NAME, null, 1);
    }

    public SQLiteDatabase returnDatabase(){
        return this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        initializeDatabase(sqLiteDatabase);
    }

    public void initializeDatabase(SQLiteDatabase sqLiteDatabase){
        //Table Creation
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_TITLE+" VARCHAR, "+TABLE_DESC+" VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertData(ContentValues cv, String table_name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.insert(table_name, null, cv);
    }

    public Cursor getData(String query) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(query, null);
        return result;
    }

    public int numberOfRows(String table_name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, table_name);
        return numRows;
    }

    public int deleteTask(long id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ? ", new String[]{String.valueOf(id)});
    }

    public long updateData(ContentValues cv, String table_name, String where_clause, String[] parameter){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.update(table_name,cv,where_clause,parameter);
    }
}
