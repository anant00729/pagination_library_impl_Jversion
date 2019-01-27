package com.socket.an2t.pagination.pagination.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;


import com.socket.an2t.pagination.pagination.RetrofitSingleton;
import com.socket.an2t.pagination.pagination.ServiceAPI;
import com.socket.an2t.pagination.pagination.adapter.State;
import com.socket.an2t.pagination.pagination.model.Article;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class PViewModel extends ViewModel {


    private ServiceAPI mS;
    private CompositeDisposable mCD = new CompositeDisposable();
    private int pageSize = 2;
    private PFactory mF;

    LiveData<PagedList<Article>> newsList;

    public LiveData<PagedList<Article>> getNewsList() {
        return newsList;
    }


    public PViewModel(){
        Retrofit mR = RetrofitSingleton.getRetrofitInstance();
        ServiceAPI mS = mR.create(ServiceAPI.class);
        this.mS = mS;

        mF = new PFactory(mCD, mS);
        PagedList.Config mC = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build();
        newsList = new LivePagedListBuilder<>(mF, mC).build();
    }



    public void retry() {
        mF.getmDSLive().getValue().retry();
    }

    public LiveData<State> getState() {
        return Transformations.switchMap(mF.mDSLive, PDSource::getState);
    }



    public Boolean listIsEmpty(){
        if (newsList.getValue() != null) {
            return newsList.getValue().isEmpty();
        }else {
            return true;
        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCD.dispose();
    }

    //    fun retry() {
//        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
//    }
//
//    fun listIsEmpty(): Boolean {
//        return newsList.value?.isEmpty() ?: true
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        compositeDisposable.dispose()
//    }



}
