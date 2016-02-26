package com.example.cattinder.activity.fragment;

import com.example.cattinder.R;
import com.example.cattinder.activity.view.CatCardView;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements ICatRequester{

    private static final int INITIAL_CAT_INDEX = 1;
    private static final String KEY_NEXT_CAT = "NextCat";

    // Injectables
    private CatSwipeAdapter mCatAdapter;
    private SwipeFlingAdapterView.onFlingListener mFlingListener;
    private SwipeFlingAdapterView mSwipeFlingAdapterView;
    private List<CatServiceResponse.Cat> mCatList;
    private CatFetchTaskFactory mCatFetchTaskFactory;

    private int nextCatIndex = INITIAL_CAT_INDEX;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.nextCatIndex = savedInstanceState.getInt(KEY_NEXT_CAT, INITIAL_CAT_INDEX);

        inject();
    }

    protected void inject() {
        CatService catService = CatService.RestClient.createService().getService();
        mCatFetchTaskFactory = new CatFetchTaskFactory(catService);
        mCatAdapter = new CatSwipeAdapter(this.getActivity(), Picasso.with(getActivity()));
        mCatList = new ArrayList<>();
        mFlingListener = new SwipeFlingListener(mCatList, mCatAdapter, mSwipeFlingAdapterView, this);
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

        this.mCatAdapter.setData(mCatList);
        getMoreCats();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_NEXT_CAT, this.nextCatIndex);
    }

    public void getMoreCats() {
        mCatFetchTaskFactory.getCatFetchTask(new ICatFetchCallback(){
                                                 @Override
                                                 public void onFetched(List<CatServiceResponse.Cat> list, int index) {
                                                     mCatList = list;
                                                     mCatAdapter.notifyDataSetChanged();
                                                     nextCatIndex = index;
                                                 }
                                             },
                                             nextCatIndex)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    static class CatFetchTaskFactory {

        private CatService mCatService;

        CatFetchTaskFactory(CatService catService) {
            mCatService = catService;
        }

        CatFetchTask getCatFetchTask(ICatFetchCallback callback, int initialIndex) {
            return new CatFetchTask(mCatService, callback, initialIndex);
        }
    }

    interface ICatFetchCallback {
        void onFetched(List<CatServiceResponse.Cat> list, int index);
    }

    static class CatFetchTask extends AsyncTask<Void, Void, List<CatServiceResponse.Cat>> {

        private CatService mCatService;
        private int mIndex;
        private ICatFetchCallback mCallback;

        CatFetchTask(CatService catService, ICatFetchCallback callback, int initialIndex) {
            mCatService = catService;
            mIndex = initialIndex;
            mCallback = callback;
        }


        @Override
        protected List<CatServiceResponse.Cat> doInBackground(Void... params) {
            CatServiceResponse response = mCatService.getCats(mIndex);
            List<CatServiceResponse.Cat> cats = response.getCats();
            mIndex += cats.size();
            return cats;
        }

        @Override
        protected void onPostExecute(List<CatServiceResponse.Cat> cats) {
            mCallback.onFetched(cats, mIndex);
        }
    }

    // https://github.com/Diolor/Swipecards
    static class SwipeFlingListener implements SwipeFlingAdapterView.onFlingListener {

        private List<CatServiceResponse.Cat> mCatList;
        private CatSwipeAdapter mCatAdapter;
        private SwipeFlingAdapterView mAdapterView;
        private WeakReference<ICatRequester> mCatRequestor;

        SwipeFlingListener(List<CatServiceResponse.Cat> catList, CatSwipeAdapter catAdapter, SwipeFlingAdapterView adapterView, ICatRequester catRequestor) {
            mCatList = catList;
            mCatAdapter = catAdapter;
            mAdapterView = adapterView;
            mCatRequestor = new WeakReference<>(catRequestor);
        }

        @Override
        public void removeFirstObjectInAdapter() {
            mCatList.remove(0);
            mCatAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLeftCardExit(Object o) {

        }

        @Override
        public void onRightCardExit(Object o) {

        }

        @Override
        public void onAdapterAboutToEmpty(int i) {
            ICatRequester catRequestor = mCatRequestor.get();
            if(catRequestor != null) {
                catRequestor.getMoreCats();
            }
        }

        @Override
        public void onScroll(float v) {
            if(v > 0) {
                ((CatCardView)mAdapterView.getSelectedView()).likeCat(v);
            }
            else if(v < 0) {
                ((CatCardView)mAdapterView.getSelectedView()).dislikeCat(v);
            }
        }
    }
}