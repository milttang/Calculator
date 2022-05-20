package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Arithmetics_Graph extends AppCompatActivity implements View.OnClickListener {

    private Button graph, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics_graph);

        graph = findViewById(R.id.graph);
        home = findViewById(R.id.home);

        graph.setOnClickListener(this);
        home.setOnClickListener(this);
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
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
                startActivity(intent);
                break;
            case R.id.home:
                Intent homeIntent = new Intent(getApplicationContext(), Arithmetics_Graph.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
                startActivity(homeIntent);
                break;
        }
    }
}