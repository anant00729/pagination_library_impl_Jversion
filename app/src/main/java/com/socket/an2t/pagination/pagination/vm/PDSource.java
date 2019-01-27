package com.socket.an2t.pagination.pagination.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;


import com.socket.an2t.pagination.pagination.ServiceAPI;
import com.socket.an2t.pagination.pagination.adapter.State;
import com.socket.an2t.pagination.pagination.model.Article;
import com.socket.an2t.pagination.pagination.model.Response;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

public class PDSource extends PageKeyedDataSource<Integer, Article> {


    public MutableLiveData<State> state = new MutableLiveData<>();
    private Completable retryCompletable = null;
    private ServiceAPI mS;
    private CompositeDisposable mCD;

    private static final String TAG = PDSource.class.getSimpleName();


    public LiveData<State> getState() {
        return state;
    }

    public PDSource(ServiceAPI mS, CompositeDisposable mCD) {
        this.mS = mS;
        this.mCD = mCD;
    }

    LoadInitialCallback<Integer, Article> callback;
    LoadCallback<Integer, Article> callbackl;
    LoadInitialParams<Integer> params;
    LoadParams<Integer> paramsl;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Article> callback) {
        this.params = params;
        this.callback = callback;
        updateState(State.LOADING);
        mCD.add(mS.getNews(1, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlePResInit, this::handleErr));
    }

    private void handleErr(Throwable throwable) {

        updateState(State.ERROR);
        setRetry(() -> loadInitial(params, callback));
    }


    private void handleErrl(Throwable throwable) {

        updateState(State.ERROR);
        setRetry(() -> loadAfter(paramsl, callbackl));
    }




    private void setRetry(Action action) {
        if (action == null){
            retryCompletable =  null;
        }else{
            retryCompletable = Completable.fromAction(action);
        }
    }


    public void retry() {
        if (retryCompletable != null) {
            mCD.add(retryCompletable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }
    }



    private void handlePResInit(Response response) {

        updateState(State.DONE);
        callback.onResult(response.getArticles(), null, 2);
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        this.paramsl = params;
        this.callbackl = callback;
        updateState(State.LOADING);
        mCD.add(mS.getNews(params.key, params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlePRes, this::handleErrl));
    }

    private void handlePRes(Response response) {

        updateState(State.DONE);
        callbackl.onResult(response.getArticles(), paramsl.key + 1);
    }


    private void updateState(State state) {
        this.state.postValue(state);
    }
}
