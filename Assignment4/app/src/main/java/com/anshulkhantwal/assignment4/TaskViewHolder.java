package com.anshulkhantwal.assignment4;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

/**
 * Created by anshul on 1/11/16.
 */

public class TaskViewHolder extends ViewHolder {
    protected AppCompatTextView task_title;
    protected AppCompatTextView task_description;

    public TaskViewHolder(View itemView) {
        super(itemView);
        task_title = (AppCompatTextView) itemView.findViewById(R.id.list_title);
        task_description = (AppCompatTextView) itemView.findViewById(R.id.list_content);
    }
}
