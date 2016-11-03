package com.anshulkhantwal.assignment4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterViewFlipper;

import java.util.List;

public class showTask extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.showtask_toolbar);
        setSupportActionBar(toolbar);


        //Bundle bundle = getIntent().getBundleExtra("TASK_BUNDLE");

        List<Tasks> list_of_tasks = MainActivity.fetchTasks();
        mPager = (ViewPager) findViewById(R.id.showtask_viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),list_of_tasks);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(getIntent().getIntExtra("TASK_POSITION",0));
    }

    /**
     * A simple pager adapter that represents 5 { ScreenSlidePageFragment} objects, in
     * sequence.
     */
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_back:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
