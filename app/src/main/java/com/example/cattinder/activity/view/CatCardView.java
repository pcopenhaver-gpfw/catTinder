package com.example.cattinder.activity.view;

import com.example.cattinder.R;
import com.example.cattinder.data.CatServiceResponse;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by doddy on 2/19/16.
 */
public class CatCardView extends View{

    private CatServiceResponse.Cat mCat;

    ImageView mCatImage;
    TextView mCatName;

    private View mNoView;
    private View mYesView;

    Picasso mPicasso;


    public CatCardView(Context context) {
        super(context);

        inject();
    }


    void inject() {
        this.mPicasso = Picasso.with(getContext());
    }


    public void setCat(CatServiceResponse.Cat cat) {
        this.mCat = cat;
        configureViewForCat();
    }


    public CatServiceResponse.Cat getCat() {
        return this.mCat;
    }


    public void likeCat(double percentage) {

    }


    public void dislikeCat(double percentage) {

    }


    private void referenceWidgets() {
        this.mCatImage = (ImageView)findViewById(R.id.image);
        this.mCatName = (TextView)findViewById(R.id.snippet);

        this.mNoView = findViewById(R.id.no);
        this.mYesView = findViewById(R.id.yes);
    }


    private void configureViewForCat() {
        this.mCatName.setText(this.mCat.getDescription());
        this.mPicasso.load(this.mCat.getImageUri()).into(this.mCatImage);
    }
}
