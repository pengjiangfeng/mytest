package com.pengjf.myapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pengjf.myapp.R;
import com.pengjf.myapp.view.BarChartView;
import com.pengjf.myapp.view.ChartEntity;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChartView barChartView = (BarChartView) findViewById(R.id.bar_chart);
        List<ChartEntity> data = new ArrayList<>();
        for(int i =0;i<20;i++){
            data.add(new ChartEntity(String.valueOf(i), (float) (Math.random()*1000)));
        }
        barChartView.setData(data);
    }
}
