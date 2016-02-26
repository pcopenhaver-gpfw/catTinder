package com.example.cattinder.activity.view;

import com.example.cattinder.R;
import com.example.cattinder.data.CatServiceResponse;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by doddy on 2/19/16.
 */
public class CatCardView extends FrameLayout {

    private CatServiceResponse.Cat mCat;

    @Bind(R.id.image) ImageView mCatImage;
    @Bind(R.id.snippet) TextView mCatName;

    @Bind(R.id.no) View mNoView;
    @Bind(R.id.yes) View mYesView;

    Picasso mPicasso;


    public CatCardView(Context context) {
        super(context);

        inflate(context, R.layout.kitty_cat, this);
        ButterKnife.bind(this);

        inject();
    }


    void inject() {
        this.mPicasso = Picasso.with(getContext());
    }


    public void setCat(CatServiceResponse.Cat cat) {

        if (cat == null)
            throw new IllegalArgumentException();

        this.mCat = cat;
        configureViewForCat();
    }


    public CatServiceResponse.Cat getCat() {
        return this.mCat;
    }


    public void likeCat(float percentage) {
        if (percentage < 0 || percentage > 1)
            throw new IllegalArgumentException();


        this.mNoView.setAlpha(0);
        this.mYesView.setAlpha(percentage);
    }


    public void dislikeCat(float percentage) {
        if (percentage < 0 || percentage > 1)
            throw new IllegalArgumentException();


        this.mYesView.setAlpha(0);
        this.mNoView.setAlpha(percentage);
    }


    private void configureViewForCat() {
        this.mCatName.setText(this.mCat.getDescription());
        this.mPicasso.load(this.mCat.getImageUri()).into(this.mCatImage);
    }
}
