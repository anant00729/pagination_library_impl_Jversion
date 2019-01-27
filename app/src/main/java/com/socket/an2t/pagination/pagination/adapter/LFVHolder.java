package com.socket.an2t.pagination.pagination.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.socket.an2t.pagination.R;
import com.socket.an2t.pagination.pagination.callback.RetryClick;


public class LFVHolder extends RecyclerView.ViewHolder {

    private ProgressBar progress_bar;
    private TextView txt_error;

    public LFVHolder(View itemView) {
        super(itemView);
        txt_error = itemView.findViewById(R.id.txt_error);
        progress_bar = itemView.findViewById(R.id.progress_bar);
    }


    public void bind(State status) {
        progress_bar.setVisibility(status == State.LOADING ? View.VISIBLE : View.INVISIBLE);
        txt_error.setVisibility(status == State.ERROR ? View.VISIBLE : View.INVISIBLE);
    }

    public static LFVHolder create(RetryClick mR, ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_footer, parent, false);
        view.findViewById(R.id.txt_error).setOnClickListener(v -> mR.onRetryClick());
        return new LFVHolder(view);
    }


}
