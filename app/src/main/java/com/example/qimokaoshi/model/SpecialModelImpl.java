package com.example.qimokaoshi.model;

import com.example.qimokaoshi.api.MyServer;
import com.example.qimokaoshi.bean.SpecialBean;
import com.example.qimokaoshi.callback.SpecialCallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecialModelImpl implements SpecialModel {
    @Override
    public void getData(final SpecialCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyServer.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyServer myServer = retrofit.create(MyServer.class);
        Observable<SpecialBean> data = myServer.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SpecialBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SpecialBean specialBean) {
                        callBack.onSeccoss(specialBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFalis(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
