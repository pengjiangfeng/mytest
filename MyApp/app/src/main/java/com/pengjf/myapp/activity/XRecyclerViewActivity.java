package com.pengjf.myapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.pengjf.myapp.R;
import com.pengjf.myapp.adapter.RecyclerViewAdapter;
import com.pengjf.myapp.bean.NewsData;
import com.pengjf.myapp.retrofit.LoadImg;
import com.pengjf.myapp.retrofit.ProgressSubscriber;
import com.pengjf.myapp.retrofit.RetrofitUtil;
import com.pengjf.myapp.retrofit.listener.SubscriberOnNextListener;
import com.pengjf.myapp.utils.ConstantUtil;
import com.pengjf.myapp.utils.FileStorage;
import com.pengjf.myapp.utils.LogUtil;
import com.pengjf.myapp.view.DividerItemDecoration;
import com.pengjf.myapp.view.FlyBanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

public class XRecyclerViewActivity extends AppCompatActivity {
    private Context mContext;
    private int page = 1;
    private XRecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private boolean isLoadMore;
    private List<NewsData> mList = new ArrayList<>();
    private FlyBanner banner;
    private List<String> mPicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        mContext = this;
        initView();
    }

    private void initView() {
        mRecyclerView = (XRecyclerView) findViewById(R.id.xrv_refresh);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new RecyclerViewAdapter(mContext, mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new MyLoadingListener());
        mRecyclerView.refresh();
        banner = (FlyBanner) LayoutInflater.from(mContext).inflate(R.layout.header, (ViewGroup) findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(banner);
        getBanner();
        upLoadImage();
    }

    private void getBanner() {
        RetrofitUtil.getInstance().getNewsCorver(new ProgressSubscriber<Map<String, String>>(new SubscriberOnNextListener<Map<String, String>>() {
            @Override
            public void onNext(Map<String, String> map) {
                if (map == null) {
                    return;
                }
                mPicList.clear();

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    mPicList.add(ConstantUtil.IMG_URL + entry.getValue());
                }
                banner.setImagesUrl(mPicList);
            }

            @Override
            public void onFinish() {

            }
        }, mContext, true));
    }

    private void getDada() {
        ConstantUtil.BASE_URL = "https://fhs-test.yunext.com/";
        RetrofitUtil.getInstance().getNewsData(page, new ProgressSubscriber<>(new SubscriberOnNextListener<List<NewsData>>() {
            @Override
            public void onNext(List<NewsData> newsDatas) {
                if (newsDatas != null) {
                    if (!isLoadMore) {
                        mList.clear();
                    }
                    mList.addAll(newsDatas);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFinish() {
                if (isLoadMore) {
                    mRecyclerView.loadMoreComplete();
                } else {
                    mRecyclerView.refreshComplete();
                }
            }
        }, mContext, false));
    }

    @OnClick({R.id.search, R.id.connect})
    public void onViewClicked(View view) {

    }


    private class MyLoadingListener implements XRecyclerView.LoadingListener {

        @Override
        public void onRefresh() {
            isLoadMore = false;
            page = 1;
            getDada();
        }

        @Override
        public void onLoadMore() {
            page++;
            isLoadMore = true;
            getDada();
        }
    }

    public void upLoadImage() {
        FileStorage fileStorage = new FileStorage();
        String path = fileStorage.getDataDir().getAbsolutePath() + "/123.jpeg";
        File file = new File(path);
        RetrofitUtil.getInstance().upload(new ProgressSubscriber<String>(new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
                LogUtil.i("图片" + s);
            }

            @Override
            public void onFinish() {

            }
        }, mContext), LoadImg.getRequestBody("123.jpeg", file));
    }
}
