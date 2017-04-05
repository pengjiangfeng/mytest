package com.pengjf.myapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.pengjf.myapp.R;
import com.pengjf.myapp.recyclerView.DataBean;
import com.pengjf.myapp.recyclerView.HeaderBean;
import com.pengjf.myapp.recyclerView.HomeCategory;
import com.pengjf.myapp.recyclerView.HomepagerRecycleAdapter;
import com.pengjf.myapp.recyclerView.MyStaggerGrildLayoutManger;
import com.pengjf.myapp.retrofit.ProgressSubscriber;
import com.pengjf.myapp.retrofit.RetrofitUtil;
import com.pengjf.myapp.retrofit.listener.SubscriberOnNextListener;
import com.pengjf.myapp.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {
    private Context mContext;
    @BindView(R.id.rv_mainactivity)
    RecyclerView rvMainactivity;
    @BindView(R.id.mrl_mainactivity)
    MaterialRefreshLayout mrlMainactivity;
    private HomepagerRecycleAdapter adapter;
    private RetrofitUtil retrofitUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        adapter = new HomepagerRecycleAdapter(mContext);
        rvMainactivity.setAdapter(adapter);
        rvMainactivity.setLayoutManager(new MyStaggerGrildLayoutManger(mContext, 2, StaggeredGridLayoutManager.VERTICAL));
        ConstantUtil.BASE_URL = "http://112.124.22.238:8081/course_api/";
        retrofitUtil = RetrofitUtil.getInstance();
        //头部数据源
        getHeaderData();
        getCategoryData();
        getCenter();
        initRefresh();
        //获取底部数据
        getRefreshData();
    }

    private void getRefreshData() {
        retrofitUtil.getCenter(new ProgressSubscriber<>(new SubscriberOnNextListener<List<DataBean>>() {

            @Override
            public void onNext(List<DataBean> dataBeen) {
                if (dataBeen.size() != 0) {
                    adapter.setRefreshBean(dataBeen, false);

                }
            }
        }, mContext));
    }

    private void getCenter() {
        retrofitUtil.getCenter(new ProgressSubscriber<>(new SubscriberOnNextListener<List<DataBean>>() {

            @Override
            public void onNext(List<DataBean> dataBeen) {
                if (dataBeen.size() != 0) {
                    adapter.setCenterBean(dataBeen);

                }
            }
        }, mContext));
    }

    private void getCategoryData() {

        ArrayList<HomeCategory> homeCategories = new ArrayList<>();
        HomeCategory c1 = new HomeCategory(R.mipmap.icon_cart, "购物车");
        HomeCategory c2 = new HomeCategory(R.mipmap.icon_discover, "发现");
        HomeCategory c3 = new HomeCategory(R.mipmap.icon_hot, "热门");
        HomeCategory c4 = new HomeCategory(R.mipmap.icon_user, "寻找");
        homeCategories.add(c1);
        homeCategories.add(c2);
        homeCategories.add(c3);
        homeCategories.add(c4);
        adapter.setCategoryBean(homeCategories);
    }

    private void getHeaderData() {

        retrofitUtil.getHeader(1, new ProgressSubscriber<>(new SubscriberOnNextListener<List<HeaderBean>>() {
            @Override
            public void onNext(List<HeaderBean> headerBeen) {
                if (headerBeen.size() != 0) {
                    adapter.setheaderbean(headerBeen);

                }
            }
        }, mContext));
    }

    private void initRefresh() {
        mrlMainactivity.setLoadMore(true);
        mrlMainactivity.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

            }
        });
    }


}
