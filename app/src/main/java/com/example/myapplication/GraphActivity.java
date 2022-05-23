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
import java.util.Collections;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

<<<<<<< HEAD
    private TextView functionTest, functionTest1;
=======
    private TextView functionTest, functionTest1, functionTest2, functionTest3, functionTest4, functionTest5;
>>>>>>> 35bfb4ac4f8e0aaa73391c9ba394e54e62f39207
    private LineChart chart;
    private Button graph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);



//        String function2 = extras.getString("function2");
//        String empty2 = extras.getString("empty2");
//        functionTest2.setText(function2);
//        functionTest3.setText(empty2);
//
//        String function3 = extras.getString("function3");
//        String empty3 = extras.getString("empty3");
//        functionTest4.setText(function3);
//        functionTest5.setText(empty3);

        chart = findViewById(R.id.linechart);
<<<<<<< HEAD
        home = findViewById(R.id.home);
        functionTest = findViewById(R.id.functionTest);
        functionTest1 = findViewById(R.id.functionTest1);
=======
        graph = findViewById(R.id.graph);
        functionTest = findViewById(R.id.functionTest);
        functionTest1 = findViewById(R.id.functionTest1);
        functionTest2 = findViewById(R.id.functionTest2);
        functionTest3 = findViewById(R.id.functionTest3);
        functionTest4 = findViewById(R.id.functionTest4);
        functionTest5 = findViewById(R.id.functionTest5);
>>>>>>> 35bfb4ac4f8e0aaa73391c9ba394e54e62f39207

        graph.setOnClickListener(this);

        // 전달 받은 Data 확인
        Bundle extras = getIntent().getExtras();
        String function1 = extras.getString("function1");
        String empty1 = extras.getString("empty1");
        functionTest.setText(function1);
        functionTest1.setText(empty1);

        String functionFirst = functionTest.getText().toString();
        String functionFirstRemove = functionFirst.replaceFirst("y=","");
        Log.v("functionFirst","functionFirst" + functionFirst);
        Log.v("functionFirstRemove","functionFirstRemove" + functionFirstRemove);
        String[] functionArray = functionFirstRemove.split("");
        Log.v("functionArray","functionArray" + Arrays.toString(functionArray));
        String[] functionResult = deleteEmpty(functionArray);
        Log.v("functionResult","functionResult" + Arrays.toString(functionResult));

        ArrayList<Entry> firstValues = new ArrayList<>();
        ArrayList<Entry> secondValues = new ArrayList<>();
        ArrayList<Entry> thirdsValues = new ArrayList<>();

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
            firstValues.add(new Entry(x, y));
        }

        for (float i = -10.000f; i < 10; i += 0.001f) {
            float y = (float) Math.cos(i);
            float x = i;
            secondValues.add(new Entry(x, y));
        }
        for (float i = -10.000f; i < 10; i += 0.001f) {
            float y = i + 2;
            float x = i;
            thirdsValues.add(new Entry(x, y));
        }
        LineDataSet set1, set2, set3;
        set1 = new LineDataSet(firstValues, "y=x+2");
        set2 = new LineDataSet(secondValues, "y=x+2");
        set3 = new LineDataSet(thirdsValues, "y=x+2");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets
        dataSets.add(set2); // add the data sets
        dataSets.add(set3); // add the data sets
        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set2.setColor(Color.GREEN);
        set2.setCircleColor(Color.GREEN);
        set3.setColor(Color.BLUE);
        set3.setCircleColor(Color.BLUE);
        // set data
        chart.setData(data);

<<<<<<< HEAD
        // 전달 받은 Data 확인
        Bundle extras = getIntent().getExtras();
        String function1 = extras.getString("function1");
        functionTest.setText(function1);

        Intent intent = getIntent(); // Data 전달 받을 Intent
        //text 키값으로 데이터를 받는다. String을 받아야 하므로 getStringExtra()를 사용함
        String text = intent.getStringExtra("function1");
        functionTest1.setText(text);
=======

>>>>>>> 35bfb4ac4f8e0aaa73391c9ba394e54e62f39207
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

    public static String[] deleteEmpty(final String[] array) {              // String[]의 Empty Data 삭제
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));                          // list 내부 Data "" 모두 제거
        return list.toArray(new String[list.size()]);
    }
}