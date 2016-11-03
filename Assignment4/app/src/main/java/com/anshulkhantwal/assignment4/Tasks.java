package com.anshulkhantwal.assignment4;

/**
 * Created by anshul on 1/11/16.
 */

public class Tasks {
    protected String title;
    protected String description;
    protected long id;

    public Tasks(long index,String t, String desc){
        title=t;
        description=desc;
        id=index;
    }
}