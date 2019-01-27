package com.socket.an2t.pagination.pagination.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socket.an2t.pagination.R;
import com.socket.an2t.pagination.pagination.model.Article;
import com.squareup.picasso.Picasso;


public class NVHolder extends RecyclerView.ViewHolder {


    private TextView txt_news_name;
    private ImageView img_news_banner;


    public NVHolder(View itemView) {
        super(itemView);
        txt_news_name = itemView.findViewById(R.id.txt_news_name);
        img_news_banner = itemView.findViewById(R.id.img_news_banner);
    }


    public void bind(Article a, Context mC) {
        if (a != null) {

            if (a.getTitle() != null) {
                txt_news_name.setText(a.getTitle());
            }
            if (a.getUrlToImage() != null) {
                if(!a.getUrlToImage().isEmpty()){
                    Picasso.get().load(a.getUrlToImage()).into(img_news_banner);
                }
            }
        }
    }


    public static NVHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NVHolder(view);
    }

}
