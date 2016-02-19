package com.example.cattinder.activity.fragment;

import com.example.cattinder.data.CatServiceResponse;

import android.content.Context;
import android.view.View;
/**
 * Created by doddy on 2/19/16.
 */
public class CatCardView extends View{

    private CatServiceResponse.Cat mCat;


    public CatCardView(Context context) {
        super(context);
    }


    public void setCat(CatServiceResponse.Cat cat) {
        this.mCat = cat;
    }


    public CatServiceResponse.Cat getCat() {
        return this.mCat;
    }
}
