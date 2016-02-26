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

    private ImageView mCatImage;
    private TextView mCatName;

    private View mNoView;
    private View mYesView;


    public CatCardView(Context context) {
        super(context);
    }


    public void setCat(CatServiceResponse.Cat cat) {
        this.mCat = cat;
        configureViewForCat();
    }


    public CatServiceResponse.Cat getCat() {
        return this.mCat;
    }


    private void referenceWidgets() {
        this.mCatImage = (ImageView)findViewById(R.id.image);
        this.mCatName = (TextView)findViewById(R.id.snippet);

        this.mNoView = findViewById(R.id.no);
        this.mYesView = findViewById(R.id.yes);
    }


    private void configureViewForCat() {
        Picasso.with(getContext()).cancelRequest(this.mCatImage);
    }
}
