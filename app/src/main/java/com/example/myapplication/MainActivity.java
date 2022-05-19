package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_barChart = findViewById(R.id.btn_barChart);
        Button btn_pieChart = findViewById(R.id.btn_pieChart);
        Button btn_raderChart = findViewById(R.id.btn_raderChart);

        btn_barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BarChartActivity.class));
            }
        });

        // https://aries574.tistory.com/118 Graph 작성 참고 주소 //

//        btn_pieChart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), PieChartActivity.class));
//            }
//        });

//        btn_raderChart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), RadarChartActivity.class));
//            }
//        });
    }
}