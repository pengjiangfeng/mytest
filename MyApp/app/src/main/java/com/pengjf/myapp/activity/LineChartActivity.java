package com.pengjf.myapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pengjf.myapp.R;
import com.pengjf.myapp.view.ChartEntity;
import com.pengjf.myapp.view.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        LineChartView lineChart = (LineChartView) findViewById(R.id.line_chart);
        List<ChartEntity> data = new ArrayList<>();
        for(int i =0;i<20;i++){
            data.add(new ChartEntity(String.valueOf(i), (float) (Math.random()*1000)));
        }
        lineChart.setData(data);
    }
}
