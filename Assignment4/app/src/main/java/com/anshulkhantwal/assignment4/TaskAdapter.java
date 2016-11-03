package com.anshulkhantwal.assignment4;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 1/11/16.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<Tasks> tasksList;

    public TaskAdapter(List<Tasks> tasksList){
        this.tasksList = tasksList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Tasks ci = tasksList.get(position);
        holder.task_title.setText(ci.title);
        holder.task_description.setText(ci.description);
    }

    public void addData(Tasks t){
        tasksList.add(t);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public void setData(Tasks tasks) {
        tasksList = new ArrayList<Tasks>();
        tasksList.add(tasks);
        notifyDataSetChanged();
    }

    public void deleteTask(int id){
        tasksList.remove(id);
        if(tasksList.size()==0)
            tasksList=MainActivity.fetchTasks();
        notifyDataSetChanged();
    }

    public Tasks getTask(int id){
        return tasksList.get(id);
    }

    public List<Tasks> getTasksList(){
        return tasksList;
    }
}
