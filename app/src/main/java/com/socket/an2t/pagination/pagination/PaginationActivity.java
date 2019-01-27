package com.socket.an2t.pagination.pagination;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.socket.an2t.pagination.R;
import com.socket.an2t.pagination.pagination.adapter.PAdapter;
import com.socket.an2t.pagination.pagination.adapter.State;
import com.socket.an2t.pagination.pagination.callback.RetryClick;
import com.socket.an2t.pagination.pagination.model.Article;
import com.socket.an2t.pagination.pagination.vm.PViewModel;


public class PaginationActivity extends AppCompatActivity implements RetryClick {


    private RecyclerView rv_main;
    private Button txt_error;
    private ProgressBar progress_bar;
    private PAdapter mPA;

    private PViewModel mPVM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);
        setUpActivityViewModel();

        rv_main = findViewById(R.id.rv_main);
        txt_error = findViewById(R.id.txt_error);
        progress_bar = findViewById(R.id.progress_bar);




        initAdapter();


//        Retrofit mR = RetrofitSingleton.getRetrofitInstance();
//
//        ServiceAPI mS = mR.create(ServiceAPI.class);

        //mS.
    }

    private void initAdapter() {






        rv_main.setHasFixedSize(true);
        rv_main.setLayoutManager(new LinearLayoutManager(this));


        mPA = new PAdapter(this, this);
        rv_main.setAdapter(mPA);


        txt_error.setOnClickListener(v -> {
            mPVM.retry();
        });


        mPVM.getNewsList().observe(this, this::handleNewsData);



        mPVM.getState().observe(this, this::handleState);


    }



    private void handleNewsData(PagedList<Article> articles) {
        mPA.submitList(articles);
    }

    private void handleState(State state) {

        progress_bar.setVisibility((mPVM.listIsEmpty() && state == State.LOADING) ? View.VISIBLE : View.GONE);
        txt_error.setVisibility((mPVM.listIsEmpty() && state == State.ERROR) ? View.VISIBLE : View.GONE);

        if(!mPVM.listIsEmpty()){
            if(state != State.DONE){
                mPA.setState(state);
            }
        }

    }

    private void setUpActivityViewModel() {

        mPVM = ViewModelProviders.of(this)
                .get(PViewModel.class);
    }

    @Override
    public void onRetryClick() {
        mPVM.retry();
    }
}
