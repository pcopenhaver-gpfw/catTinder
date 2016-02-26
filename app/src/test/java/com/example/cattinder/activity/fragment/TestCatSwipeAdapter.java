package com.example.cattinder.activity.fragment;

import com.example.cattinder.activity.view.CatCardView;
import com.example.cattinder.activity.view.TestCatCardView;

import android.content.Context;
/**
 * Created by doddy on 2/26/16.
 */
public class TestCatSwipeAdapter extends CatSwipeAdapter{

    public TestCatSwipeAdapter(Context context) {
        super(context);
    }


    @Override
    CatCardView createCatCardView(Context context) {
        return new TestCatCardView(context);
    }
}
