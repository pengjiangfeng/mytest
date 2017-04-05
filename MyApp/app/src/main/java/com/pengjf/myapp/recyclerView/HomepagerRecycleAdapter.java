package com.pengjf.myapp.recyclerView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.pengjf.myapp.R;
import com.pengjf.myapp.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangfeng  on 2017/4/1 0001 10:57
 * 邮箱：pengjf@hadlinks.com
 */

public class HomepagerRecycleAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private Context mContext;
    private int count = 3;
    private int TYPE_TOP = 1;//头部布局
    private int TYPE_CENTER = 2;//
    private int TYPE_CATEGORY = 3;//中间的四个快速入口
    private int TYPE_HEADER = 4;//每个分类的head
    private int REFRESHPOSITION = 5;//下部head的位置
    private int CENTERPOSITION;//中间head的位置
    private int TYPE_REFRESH = 6;//最下面的布局

    private List<DataBean> refreshbean;
    private List<DataBean> centerBean;
    private List<HeaderBean> headList;
    private ArrayList<HomeCategory> mHomeCategories = new ArrayList<>();
    public HomepagerRecycleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        headList = new ArrayList<>();
        refreshbean = new ArrayList<>();
        centerBean = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_TOP) {
            //头部轮播图
            View viewtop = inflater.inflate(R.layout.adapter_slider, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
            params.setFullSpan(true);//最为重要的一个方法，相当于MATCH_PARENT,以下同理
            viewtop.setLayoutParams(params);
            return new TypeTopsliderHolder(viewtop);
        } else if (viewType == TYPE_HEADER) {

            View view2 = inflater.inflate(R.layout.item_homepagertypeheader_type, parent, false);

            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) view2.getLayoutParams();
            params.setFullSpan(true);
            view2.setLayoutParams(params);
            return new TypeheadHolder(view2);
        } else if (viewType == TYPE_CENTER) {
            //中间head下面的布局
            View view = inflater.inflate(R.layout.itam_homepageradapter_rv2, parent, false);
            StaggeredGridLayoutManager.LayoutParams params2 =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params2.setFullSpan(true);
            view.setLayoutParams(params2);
            return new TypetypeHolder2(view);

        } else if (viewType == TYPE_CATEGORY) {
            //四个快速入口的holder
            //这里为了简单，避免引起复用带来的问题，分开了
            View view = inflater.inflate(R.layout.itam_homepageradapter_rv2, parent, false);
            StaggeredGridLayoutManager.LayoutParams params2 =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params2.setFullSpan(true);
            view.setLayoutParams(params2);
            return new TypetypeHolder(view);

        } else if (viewType == TYPE_REFRESH) {
            return new TypeRefresh(inflater.inflate(R.layout.item_raiders2, parent, false));
        } else {

            View viewtop = inflater.inflate(R.layout.adapter_slider, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
            params.setFullSpan(true);
            viewtop.setLayoutParams(params);
            return new TypeTopsliderHolder(viewtop);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeTopsliderHolder && headList.size() != 0 && ((TypeTopsliderHolder) holder).linearLayout.getChildCount() == 0) {
            //如果是TypeTopsliderHolder， 并且header有数据，并且TypeTopsliderHolder的linearLayout没有子view（因为这个布局只出现一次，如果他没有子view，
            // 也就是他是第一次加载，才加载数据）
            initslider(((TypeTopsliderHolder) holder), headList);//加载头部数据源
        } else if (holder instanceof TypetypeHolder && centerBean.size() != 0) {
            initCategory(((TypetypeHolder) holder));//加载四个category数据源
        } else if (holder instanceof TypeheadHolder) {
            initTop(((TypeheadHolder) holder), position);//加载heade数据源（其实这里可以每个head单独设置，因为有的需求head去各式各样）
        } else if (holder instanceof TypetypeHolder2 && centerBean.size() != 0) {
            initCenterBean(((TypetypeHolder2) holder));//加载中间head下面的数据源
        } else if (holder instanceof TypeRefresh && refreshbean.size() != 0) {
            initrefreshdata(((TypeRefresh) holder), position - REFRESHPOSITION - 1);//加载瀑布流数据源
        }
    }

    private List<Integer> mHeights = new ArrayList<>();
    private void initrefreshdata(TypeRefresh holder, int position) {
        Log.e("position", "initrefreshdata: " + position);
        if (mHeights.size() <= getItemCount() + 2) {
            //这里只是随机数模拟瀑布流， 实际过程中， 应该根据图片高度来实现瀑布流
            mHeights.add((int) (500 + Math.random() * 400));
        }

        ViewGroup.LayoutParams layoutParams = holder.homeReadPivIv.getLayoutParams();
        if (mHeights.size() > position)
            //此处取得随机数，如果mheight里面有数则取， 没有则邹走else
            layoutParams.height = mHeights.get(position);
        else layoutParams.height = 589;
        holder.homeReadPivIv.setLayoutParams(layoutParams);

        holder.homeReadPivIv.setScaleType(ImageView.ScaleType.FIT_XY);
        if (refreshbean.size() > position) {
            ImageUtils.load(mContext, refreshbean.get(position).getCpOne().getImgUrl(), holder.homeReadPivIv);
        } else {
            ImageUtils.load(mContext, refreshbean.get(0).getCpTwo().getImgUrl(), holder.homeReadPivIv);
        }
    }

    private void initCenterBean(TypetypeHolder2 holder) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, 2));

        TypeHistoryAdapter centerAdapter = new TypeHistoryAdapter(mContext, centerBean);


        holder.rvtype.setAdapter(centerAdapter);
    }



    private void initCategory(TypetypeHolder holder) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, mHomeCategories.size()));

        TypeCategoryAdapter categoryAdapter = new TypeCategoryAdapter(mContext, mHomeCategories);

        holder.rvtype.setAdapter(categoryAdapter);
    }

    private void initTop(TypeheadHolder holder, int position) {
        if (position == CENTERPOSITION) {
            holder.hview.setTypeName("抢购");

        } else if (position == REFRESHPOSITION) {
            holder.hview.setTypeName("推荐");

        }

    }

    @Override
    public int getItemViewType(int position) {
        //此处是根据数据源有无数据来判定分类条的位置；可自行拓展，自由发挥
        CENTERPOSITION = mHomeCategories.size() == 0 ? 1 : 2;
        REFRESHPOSITION = centerBean.size() == 0 ? 3 : 4;

        Log.e("getItemViewType", "getItemViewType: " + CENTERPOSITION + ",:" + REFRESHPOSITION);

        if (position == 0) return TYPE_TOP;
        else if (position == CENTERPOSITION || position == REFRESHPOSITION) return TYPE_HEADER;
        else if (position == 1) return TYPE_CATEGORY;
        else if (position == CENTERPOSITION + 1) return TYPE_CENTER;
        else return TYPE_REFRESH;
    }

    private void initslider(TypeTopsliderHolder holder, List<HeaderBean> headList) {
        LinearLayout linearLayout = holder.linearLayout;
        for (int i = 0; i < headList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.rightMargin = 40;
            imageView.setLayoutParams(layoutParams);
            ImageUtils.load(mContext, headList.get(i).getImgUrl(), imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            linearLayout.addView(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }



    public void setheaderbean(List<HeaderBean> headerBeanList) {
        headList = headerBeanList;
        notifyDataSetChanged();
    }

    public void setCategoryBean(ArrayList<HomeCategory> homeCategories) {
        mHomeCategories = homeCategories;
        count++;
        notifyDataSetChanged();
    }

    public void setCenterBean(List<DataBean> dataBeen) {
        centerBean = dataBeen ;
        count++;
        notifyDataSetChanged();
    }

    public void setRefreshBean(List<DataBean> refList, boolean flagFirst) {
        refreshbean.addAll(refList);
//        int count1 = this.count;
        this.count += refList.size();
        notifyDataSetChanged();
//        if (!flagFirst) {
//            recyclerView.smoothScrollToPosition(count1 + 2);//加载完以后向上滚动3个条目
//        }


    }

    //头部Viewpager viewholder
    public class TypeTopsliderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_slider)
        LinearLayout linearLayout;

        public TypeTopsliderHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


    }

    public class TypeheadHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ashv_homepager)
        AsHomepageHeaderView hview;

        public TypeheadHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            hview.setMoreclicklistenser(new AsHomepageHeaderView.MoreclickListenser() {
                @Override
                public void setmoreclicklistenser() {
                }
            });
        }
    }

    public class TypetypeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_homepageradapter_artist)
        RecyclerView rvtype;

        public TypetypeHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class TypetypeHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_homepageradapter_artist)
        RecyclerView rvtype;

        public TypetypeHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class TypeRefresh extends RecyclerView.ViewHolder {
        @BindView(R.id.home_read_piv_iv)
        ImageView homeReadPivIv;
        TypeRefresh(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING){
                    Glide.with(mContext).pauseRequests(); //在滚动暂停加载
                }else {
                    Glide.with(mContext).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }
}
