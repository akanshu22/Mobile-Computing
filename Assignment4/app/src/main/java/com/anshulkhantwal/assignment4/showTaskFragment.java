package com.anshulkhantwal.assignment4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class showTaskFragment extends Fragment {

    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "TASK_NO";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private static List<Tasks> tasks_list;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static showTaskFragment create(int pageNumber, List<Tasks> task_list) {
        showTaskFragment fragment = new showTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        tasks_list = task_list;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_show_task, container, false);

        // Set the title view to show the page number.
        ((AppCompatTextView) rootView.findViewById(R.id.showtask_title)).setText("TASK TITLE\n"+tasks_list.get(getPageNumber()).title);

        ((AppCompatTextView) rootView.findViewById(R.id.showtask_description)).setText("TASK DESCRIPTION\n"+tasks_list.get(getPageNumber()).description);

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
