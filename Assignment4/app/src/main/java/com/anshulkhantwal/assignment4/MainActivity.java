package com.anshulkhantwal.assignment4;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static DBModel db;
    protected RecyclerView recList;
    protected TaskAdapter adapter;
    protected static int list_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list_status=1;

        db = new DBModel(this,1);
        recList = (RecyclerView) findViewById(R.id.recycler_view);
        recList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        adapter = new TaskAdapter(fetchTasks());
        recList.setAdapter(adapter);

        ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        if(list_status==0){
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        Tasks t = adapter.getTask(viewHolder.getAdapterPosition());
                        if(db.deleteTask(t.id)==1){
                            adapter.deleteTask(viewHolder.getAdapterPosition());
                        }
                    }
                });
        mIth.attachToRecyclerView(recList);


        recList.addOnItemTouchListener(new RecyclerViewItemClickListener(this, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemSingleClick(View view, int position) {
                Intent intent = new Intent("com.anshulkhantwal.assignment4.showTask");
                intent.putExtra("TASK_POSITION",position);
                startActivity(intent);
            }

            @Override
            public void onItemDoubleTap(View childView, int position) {
                Intent intent = new Intent("com.anshulkhantwal.assignment4.ShowTaskDialog");
                intent.putExtra("TITLE",adapter.getTask(position).title);
                intent.putExtra("DESCRIPTION",adapter.getTask(position).description);
                startActivity(intent);
            }
        },recList));
    }

    protected static List<Tasks> fetchTasks() {
        List<Tasks> result = new ArrayList<Tasks>();
        if(db.numberOfRows(DBModel.TABLE_NAME)==0){
            result.add(new Tasks(1,"Welcome to ToDo List App","You may add a task by using the button given in action bar. Click on a task to view it's content. Double tap a task to view it's content in a dialog. Swipe right the task inorder to delete the task and mark it as done."));
            list_status=0;
            return result;
        }
        Cursor db_result = db.getData("SELECT ID,Title, Description FROM Tasks");
        if(db_result.moveToNext()==false)
            return null;
        db_result.moveToPrevious();
        while(db_result.moveToNext()!=false) {
            Tasks ci = new Tasks(db_result.getLong(0),db_result.getString(1),db_result.getString(2));
            result.add(ci);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addtask) {
            Intent intent = new Intent("com.anshulkhantwal.assignment4.AddTaskDialog");
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==1 && !data.getStringExtra("TITLE").isEmpty()){
            ContentValues cv = new ContentValues();
            cv.put("Title",data.getStringExtra("TITLE"));
            cv.put("Description",data.getStringExtra("DESCRIPTION"));
            long id=db.insertData(cv,DBModel.TABLE_NAME);
            if(id==-1)
                Snackbar.make(findViewById(R.id.content_main),"Problem while inserting the Task. Please try again after some time",Snackbar.LENGTH_SHORT);
            else {
                Snackbar.make(findViewById(R.id.content_main), "Your task has been inserted successfully.", Snackbar.LENGTH_SHORT);
                if(list_status==1)
                    adapter.addData(new Tasks(id,data.getStringExtra("TITLE"),data.getStringExtra("DESCRIPTION")));
                else{
                    adapter.setData(new Tasks(id,data.getStringExtra("TITLE"),data.getStringExtra("DESCRIPTION")));
                    list_status=1;
                }
            }
        }
    }
}