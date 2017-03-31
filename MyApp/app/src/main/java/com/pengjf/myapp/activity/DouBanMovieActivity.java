package com.pengjf.myapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pengjf.myapp.R;
import com.pengjf.myapp.retrofit.ProgressSubscriber;
import com.pengjf.myapp.retrofit.RetrofitUtil;
import com.pengjf.myapp.retrofit.bean.Movie;
import com.pengjf.myapp.retrofit.listener.SubscriberOnNextListener;
import com.pengjf.myapp.utils.ToastUtil;
import com.pengjf.myapp.view.DividerItemDecoration;
import com.pengjf.myapp.view.ItemClickHelper;
import com.pengjf.myapp.view.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

public class DouBanMovieActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView ;
    private int page ;
    private MovieAdapter mAdapter;
    private List<Movie> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dou_ban_movie);
        initView();
        getData(true);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new ItemClickHelper(mRecyclerView, new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.LongToast(mList.get(position).getTitle());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        mAdapter = new MovieAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getData(boolean b) {
        RetrofitUtil.getInstance().getMovie(0,20,new ProgressSubscriber<>(new SubscriberOnNextListener<List<Movie>>() {
            @Override
            public void onNext(List<Movie> movies) {
                if (movies != null){
                    mList.clear();
                    mList.addAll(movies);
                    mAdapter.notifyDataSetChanged();
                }
            }
        },this));
    }


}