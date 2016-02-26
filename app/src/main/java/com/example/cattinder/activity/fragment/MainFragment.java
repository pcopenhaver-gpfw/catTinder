package com.example.cattinder.activity.fragment;

import com.example.cattinder.R;
import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatServiceResponse;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment{

    private static final int INITIAL_CAT_INDEX = 1;
    private static final String KEY_NEXT_CAT = "NextCat";

    // Injectables
    private CatService mCatService;
    private CatSwipeAdapter mCatAdapter;
    private SwipeFlingAdapterView.onFlingListener mFlingListener;


    private SwipeFlingAdapterView mSwipeFlingAdapterView;
    private List<CatServiceResponse.Cat> mCatList;

    private int nextCatIndex = INITIAL_CAT_INDEX;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.nextCatIndex = savedInstanceState.getInt(KEY_NEXT_CAT, INITIAL_CAT_INDEX);

        inject();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_hotornot, null);

        this.mSwipeFlingAdapterView = (SwipeFlingAdapterView)view.findViewById(R.id.kittyStack);
        this.mSwipeFlingAdapterView.setFlingListener(this.mFlingListener);
        this.mSwipeFlingAdapterView.setAdapter(this.mCatAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        this.mCatList = new ArrayList<>();
        this.mCatAdapter.setData(mCatList);

        getMoreCats();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_NEXT_CAT, this.nextCatIndex);
    }


    private void getMoreCats() {
        new AsyncTask<Void, Void, List<CatServiceResponse.Cat>>() {

            @Override
            protected List<CatServiceResponse.Cat> doInBackground(Void... params) {

                CatServiceResponse response = MainFragment.this.mCatService.getCats(MainFragment.this.nextCatIndex);
                List<CatServiceResponse.Cat> cats = response.getCats();
                MainFragment.this.nextCatIndex += cats.size();
                return cats;
            }


            @Override
            protected void onPostExecute(List<CatServiceResponse.Cat> cats) {

                MainFragment.this.mCatAdapter.addData(cats);
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }



    protected void inject() {
        this.mCatService = CatService.RestClient.createService().getService();
        this.mCatAdapter = new CatSwipeAdapter(this.getActivity(), Picasso.with(getActivity()));
        this.mFlingListener = new SwipeFlingListener();
    }



    // https://github.com/Diolor/Swipecards
    private class SwipeFlingListener implements SwipeFlingAdapterView.onFlingListener {

        @Override
        public void removeFirstObjectInAdapter() {
            mCatList.remove(0);
            MainFragment.this.mCatAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLeftCardExit(Object o) {

        }

        @Override
        public void onRightCardExit(Object o) {

        }

        @Override
        public void onAdapterAboutToEmpty(int i) {
            getMoreCats();
        }

        @Override
        public void onScroll(float v) {

        }
    }
}