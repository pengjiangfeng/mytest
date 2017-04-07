package com.pengjf.myapp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pengjf.myapp.R;
import com.pengjf.myapp.retrofit.bean.Movie;

import java.util.List;

/**
 * Created by jiangfeng  on 2017/3/31 0031 16:53
 * 邮箱：pengjf@hadlinks.com
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder>{
    private List<Movie> mList ;
    private Context mContext ;
    public MovieAdapter(List<Movie> list,Context context){
        mList = list ;
        mContext = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Movie movie = mList.get(position);
        Glide.with(mContext)
                .load(movie.getImages().getSmall())
                .into(holder.imageView);
        holder.titile.setText(movie.getTitle() +"\t 主演："+ movie.getCasts().get(0).getName());
        holder.mark.setText("评分："+ movie.getRating().getAverage() + "\t 年份:"+movie.getYear()
                + "\t导演："+movie.getDirectors().get(0).getName());

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView titile ;
        TextView mark ;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            titile = (TextView) itemView.findViewById(R.id.title);
            mark = (TextView) itemView.findViewById(R.id.mark);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //如果快速滑动， 不加载图片
                if (newState == 2) {
                    Glide.with(mContext).pauseRequests();
                } else {
                    Glide.with(mContext).resumeRequests();

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
