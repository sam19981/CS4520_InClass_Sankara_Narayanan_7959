package com.example.inclass_sankara_narayanan_002787959.InClass06;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels.News;
import com.example.inclass_sankara_narayanan_002787959.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> news;

    private FragmentManager manager;
    private Context context;



    public NewsAdapter(ArrayList<News> news,FragmentManager context)
    {
        this.manager =context;
        this.news = news;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tittle,author;
        private final ImageView imgView;
        private final CardView cardview;
        private final View v;

        public TextView getTittle() {
            return tittle;
        }

        public TextView getAuthor() {
            return author;
        }

        public ImageView getImgView() {
            return imgView;
        }

        public View getView() {
            return v;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tittle = itemView.findViewById(R.id.Ntitle);
            this.author = itemView.findViewById(R.id.Nauthor);
            this.imgView = itemView.findViewById(R.id.NnewsImg);
            this.cardview = itemView.findViewById(R.id.main_container);
            this.v =itemView;
        }
    }


    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newslayout,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder,int position) {
            holder.getAuthor().setText(news.get(position).getAuthor());
            holder.getTittle().setText(news.get(position).getTittle());
            Glide.with(holder.getImgView().getContext()).load(news.get(position).getUrlToImage()).into(holder.getImgView());
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      manager.beginTransaction().replace(R.id.fragmentId,new newsDisplayFragment(news.get(position)),"currentNew").addToBackStack(null).commit();
                }
            });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


}
