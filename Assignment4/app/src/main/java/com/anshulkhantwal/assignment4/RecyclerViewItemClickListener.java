package com.anshulkhantwal.assignment4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anshul on 2/11/16.
 */

public class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemSingleClick(View view, int position);
        void onItemDoubleTap(View childView, int childAdapterPosition);
    }

    GestureDetector mGestureDetector;

    public RecyclerViewItemClickListener(Context context, OnItemClickListener listener, final RecyclerView rv) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            View childView;

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                childView = rv.findChildViewUnder(e.getX(), e.getY());
                mListener.onItemSingleClick(childView, rv.getChildAdapterPosition(childView));
                //System.out.println("TMA: Single Tap Up");
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                super.onDoubleTap(e);
                childView = rv.findChildViewUnder(e.getX(), e.getY());
                mListener.onItemDoubleTap(childView, rv.getChildAdapterPosition(childView));
                return true;
            }
        });
    }

    private void onSwipeTop() {

    }

    private void onSwipeBottom() {

    }

    private void onSwipeLeft() {

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
