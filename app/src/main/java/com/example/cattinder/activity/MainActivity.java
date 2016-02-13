package com.example.cattinder.activity;

import com.example.cattinder.R;
import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatDal;
import com.example.cattinder.data.CatServiceResponse;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatService.BASE_URL).build();

    Observable.create(new Observable.OnSubscribe<CatServiceResponse>(){
      @Override
      public void call(Subscriber<? super CatServiceResponse> subscriber) {
        CatService catService = adapter.create(CatService.class);
        try {
          subscriber.onNext(catService.getCats(1));
            SystemClock.sleep(3000);
            subscriber.onNext(catService.getCats(11));
        }
        catch(RetrofitError e) {
          subscriber.onError(e);
        }
      }
    })
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.newThread())
    .subscribe(new Observer<CatServiceResponse>(){
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(CatServiceResponse catServiceResponse) {
          Realm realm = Realm.getDefaultInstance();

          realm.beginTransaction();
          for(CatServiceResponse.Cat cat : catServiceResponse.getCats()) {

            realm.copyToRealm(new CatDal(cat.getImageUri(), cat.getDescription()));
          }
        realm.commitTransaction();
      }
    });

      Realm realm = Realm.getDefaultInstance();
      realm.where(CatDal.class).findAllAsync().asObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<RealmResults<CatDal>>(){
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {

              }

              @Override
              public void onNext(RealmResults<CatDal> catDals) {
                for(CatDal cat : catDals) {
                  Log.d("fromRealm", cat.getLink().toString());

                }
              }
            });
  }
}
