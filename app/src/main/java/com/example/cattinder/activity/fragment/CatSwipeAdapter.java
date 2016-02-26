package com.example.cattinder.activity.fragment;

import com.example.cattinder.activity.view.CatCardView;
import com.example.cattinder.data.CatServiceResponse;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
/**
 * Created by doddy on 2/19/16.
 */
public class CatSwipeAdapter extends BaseAdapter {

    private Context mContext;
    private List<CatServiceResponse.Cat> mData;


    public CatSwipeAdapter(Context context) {
        this.mContext = context;
    }


    public void setData(List<CatServiceResponse.Cat> data) {
        this.mData = data;
    }


    @Override
    public int getCount() {
        return this.mData.size();
    }


    @Override
    public CatServiceResponse.Cat getItem(int position) {
        if(position < 0 || position >= this.mData.size())
            throw new IndexOutOfBoundsException();

        return this.mData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatCardView catCardView = (CatCardView)convertView;
        if (catCardView == null)
            catCardView = createCatCardView(this.mContext);

        catCardView.setCat(getItem(position));

        return catCardView;
    }


    public void addData(List<CatServiceResponse.Cat> newData) {
        this.mData.addAll(newData);
        notifyDataSetChanged();
    }


    CatCardView createCatCardView(Context context) {
        return new CatCardView(context);
    }
}
