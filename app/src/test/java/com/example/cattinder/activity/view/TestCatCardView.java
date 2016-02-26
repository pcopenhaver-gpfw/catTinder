package com.example.cattinder.activity.view;

import com.example.test.MockPicasso;

import android.content.Context;
/**
 * Created by doddy on 2/26/16.
 */
public class TestCatCardView extends CatCardView{

    public TestCatCardView(Context context) {
        super(context);
    }


    @Override
    void inject() {
        this.mPicasso = MockPicasso.create();
    }
}
