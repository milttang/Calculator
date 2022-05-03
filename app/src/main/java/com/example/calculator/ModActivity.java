package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ModActivity extends AppCompatActivity implements View.OnClickListener {

    private Button  btnHome, btnBinary, btnOct;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mod);

        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void init(){
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnBinary = findViewById(R.id.btnBinary);
        btnBinary.setOnClickListener(this);
        btnOct = findViewById(R.id.btnOct);
        btnOct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        double num;
        select(v);
        switch (v.getId()) {
            case R.id.btnHome :
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;

            case R.id.btnBinary:
                Intent intent1 = new Intent(getApplicationContext(), BinaryActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                break;

            case R.id.btnOct:
                Intent intent2 = new Intent(getApplicationContext(), OctaActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent2);
                break;
        }
    }

    private void select(View view2) {
        if(view != null){
            if(view.getId() != view2.getId()){
                view.setSelected(false);
            }
        }
        view2.setSelected(true);
        view = view2;
    }
}
