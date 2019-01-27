package com.socket.an2t.pagination.pagination.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.socket.an2t.pagination.pagination.callback.RetryClick;
import com.socket.an2t.pagination.pagination.model.Article;

public class PAdapter extends PagedListAdapter<Article,RecyclerView.ViewHolder> {


    private int DATA_VIEW_TYPE = 1;
    private int FOOTER_VIEW_TYPE = 2;

    private State state = State.LOADING;

    private RetryClick mL;
    private Context mC;

    public PAdapter(RetryClick mL, Context mC){
        super(DIFF_CALLBACK);
        this.mL = mL;
        this.mC = mC;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(position < super.getItemCount()){
            return DATA_VIEW_TYPE;
        }else {
            return FOOTER_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DATA_VIEW_TYPE) {
            return NVHolder.create(parent);
        }else {
            return LFVHolder.create(mL, parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



        if (getItemViewType(position) == DATA_VIEW_TYPE)
            ((NVHolder)holder).bind(getItem(position), mC);
        else ((LFVHolder)holder).bind(state);
    }



    @Override
    public int getItemCount() {
        if(hasFooter()){
            return super.getItemCount() + 1;
        }else {
            return super.getItemCount();
        }
    }

    private Boolean hasFooter(){

        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR);
    }

    private static DiffUtil.ItemCallback<Article> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Article>() {
                @Override
                public boolean areItemsTheSame(Article oldItem, Article newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @Override
                public boolean areContentsTheSame(Article oldItem, Article newItem) {
                    return oldItem.equals(newItem);
                }
            };


    public void setState(State state) {
        this.state = state;
        notifyItemChanged(super.getItemCount());
    }

}
