package com.pengjf.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pengjf.myapp.R;
import com.pengjf.myapp.bean.NewsData;
import com.pengjf.myapp.listeneners.OnItemClickListener;
import com.pengjf.myapp.utils.ConstantUtil;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<NewsData> mListData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewAdapter(Context context, List<NewsData> datas) {
        this.mListData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(v);
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(mListData.get(position).getTitle());
        holder.mDate.setText(mListData.get(position).getPublishTime() +"发布");
        Glide.with(mContext)
                .load(ConstantUtil.IMG_URL + mListData.get(position).getCoverId())
                .placeholder(R.mipmap.news_default_s)
                .error(R.mipmap.news_default_s)
                .dontAnimate()
                .into(holder.mimg);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mDate;
        ImageView mimg;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDate = (TextView) itemView.findViewById(R.id.tv_publish_time);
            mimg = (ImageView) itemView.findViewById(R.id.iv_news_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v,getAdapterPosition());
                    }
                }
            });
        }
    }
}
