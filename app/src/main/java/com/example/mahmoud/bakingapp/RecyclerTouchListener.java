package com.example.mahmoud.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}