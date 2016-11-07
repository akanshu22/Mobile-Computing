package com.anshulkhantwal.assignment4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class ShowToDoTask extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_to_do_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_showtodotask);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Tasks> list_of_tasks = MainActivity.fetchTasks();
        mPager = (ViewPager) findViewById(R.id.showtask_viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),list_of_tasks);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(getIntent().getIntExtra("TASK_POSITION",0));
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        List<Tasks> task_list;
        public ScreenSlidePagerAdapter(FragmentManager fm, List<Tasks> task_list) {
            super(fm);
            this.task_list = task_list;
        }

        @Override
        public Fragment getItem(int position) {
            return showTaskFragment.create(position,task_list);
        }

        @Override
        public int getCount() {
            return task_list.size();
        }
    }

}
