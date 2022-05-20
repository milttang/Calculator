package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView functionTest, functionTest1, functionTest2, functionTest3, functionTest4, functionTest5;
    private LineChart chart;
    private Button graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        chart = findViewById(R.id.linechart);
        graph = findViewById(R.id.graph);
        functionTest = findViewById(R.id.functionTest);
        functionTest1 = findViewById(R.id.functionTest1);
        functionTest2 = findViewById(R.id.functionTest2);
        functionTest3 = findViewById(R.id.functionTest3);
        functionTest4 = findViewById(R.id.functionTest4);
        functionTest5 = findViewById(R.id.functionTest5);

        graph.setOnClickListener(this);


        ArrayList<Entry> values = new ArrayList<>();

        for (float i = -10.000f; i < 10; i += 0.001f) {
//            String a = "i*i";
////            String[] c = a.split("y=");
////            Log.v("c Value","c %f" + Arrays.toString(c));
////            Log.v("c Value","c %f" + a);
////            int w = Integer.parseInt(a);
////            Log.v("w Value","w %f" + w);
////            float z = (float) w;
////            float c =  Float.parseFloat(a);
            float y = i*i;
////            Log.v("z Value","c %f" + z);
////            Log.v("c Value","c %f" + z);
            float x = i;
            values.add(new Entry(x, y));
        }

//        for (float i = -10.000f; i < 10; i += 0.001f) {
//            String a = "i*i";
//            float y = (float) Math.cos(i);
//            float x = i;
//            values.add(new Entry(x, y));
//        }

        LineDataSet set1;
        set1 = new LineDataSet(values, "y=x+2");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // set data
        chart.setData(data);

        // 전달 받은 Data 확인
        Bundle extras = getIntent().getExtras();
        String function1 = extras.getString("function1");
        String empty1 = extras.getString("empty1");
        functionTest.setText(function1);
        functionTest1.setText(empty1);
    }

    // Activity 종료 시 효과 제거
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.graph:
                Intent homeIntent = new Intent(getApplicationContext(), Arithmetics_Graph.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
//                if(!functionTest.getText().toString().equals("")) {
//                    homeIntent.putExtra("functionOne", functionTest.getText().toString());
//                    homeIntent.putExtra("emptyOne", functionTest1.getText().toString());
//                } else {
//                    homeIntent.putExtra("functionOne", "");
//                    homeIntent.putExtra("emptyOne", "");
//                }
//                if(!functionTest.getText().toString().equals("")) {
//                    homeIntent.putExtra("functionTwo", functionTest2.getText().toString());
//                    homeIntent.putExtra("emptyTwo", functionTest3.getText().toString());
//                } else {
//                    homeIntent.putExtra("functionTwo", "");
//                    homeIntent.putExtra("emptyTwo", "");
//                }
//                if(!functionTest.getText().toString().equals("")) {
//                    homeIntent.putExtra("functionThr", functionTest4.getText().toString());
//                    homeIntent.putExtra("emptyThr", functionTest5.getText().toString());
//                } else {
//                    homeIntent.putExtra("functionThr", "");
//                    homeIntent.putExtra("emptyThr", "");
//                }
                startActivity(homeIntent);
                break;
        }
    }
}