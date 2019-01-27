package com.socket.an2t.pagination.pagination.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;


import com.socket.an2t.pagination.pagination.ServiceAPI;
import com.socket.an2t.pagination.pagination.model.Article;

import io.reactivex.disposables.CompositeDisposable;

public class PFactory extends DataSource.Factory<Integer, Article>{

    private CompositeDisposable mCD;
    private ServiceAPI mS;

    MutableLiveData<PDSource> mDSLive = new MutableLiveData<PDSource>();

    public LiveData<PDSource> getmDSLive() {
        return mDSLive;
    }

    public PFactory(CompositeDisposable mCD, ServiceAPI mS) {
        this.mCD = mCD;
        this.mS = mS;
    }

    @Override
    public DataSource<Integer, Article> create() {
        PDSource mPDS = new PDSource(mS, mCD);
        mDSLive.postValue(mPDS);
        return mPDS;
    }
}
