package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;


public class Arithmetics extends AppCompatActivity {  //터치따로
    private CalculateHelper calculateHelper;

    private boolean isDot, isBracket, isPreview;

    private TextView edit_result, edit_process, edit_arith;

    private int size;

    private String result;

    private View view;

    private Runnable runnable_up, runnable_down;

    private Handler handler_up, handler_down;

    Button[] button = new Button[10];

    private Button addBtn, subBtn, mulBtn, divBtn, clear, bracket, backBtn, dot, equal, hexa, binary, sqr, root, sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics);

        calculateHelper = new CalculateHelper();

        view = null;
        size = 0;
        int number = 25;
        int t = String.valueOf(Math.sqrt(number)).length();
        Log.d("test", "" + t + " ? " + String.valueOf(Math.sqrt(number)));

        isPreview = false;
        isBracket = false;
        isDot = false;

        int[][] test = new int[5][4];
        setButton();
        setTextView();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("방향: ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("방향: ORIENTATION_PORTRAIT");
        }
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    private void setButton() {

        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        mulBtn = findViewById(R.id.mulBtn);
        divBtn = findViewById(R.id.divBtn);
        sqr = findViewById(R.id.sqr);
        root = findViewById(R.id.root);
        equal = findViewById(R.id.equal);
        clear = findViewById(R.id.clear);
        bracket = findViewById(R.id.bracket);
        binary = findViewById(R.id.binary);
        hexa = findViewById(R.id.hexa);
        sort = findViewById(R.id.sort);
        backBtn = findViewById(R.id.backBtn);
        dot = findViewById(R.id.dot);

        // number ClickListener
        Integer[] btn ={R.id.numBtn0, R.id.numBtn1, R.id.numBtn2,
                R.id.numBtn3, R.id.numBtn4, R.id.numBtn5,
                R.id.numBtn6, R.id.numBtn7, R.id.numBtn8, R.id.numBtn9};
        // 제 3 클래스로 이벤트 구현
        LongClickEvent longClickEvent = new LongClickEvent(this);
        TouchEvent touchEvent = new TouchEvent(this);
        setHandler(null); // 핸들러 초기 세팅
        for(int i = 0; i<button.length; i++) {
            button[i] = findViewById(btn[i]);
            button[i].setOnClickListener(numClickListener);
            button[i].setOnLongClickListener(longClickEvent);
            button[i].setOnTouchListener(touchEvent);
        }
        backBtn.setOnLongClickListener(longClickEvent);
        backBtn.setOnTouchListener(touchEvent);

        // mark ClickListener
        addBtn.setOnClickListener(markClickListener);
        subBtn.setOnClickListener(markClickListener);
        mulBtn.setOnClickListener(markClickListener);
        divBtn.setOnClickListener(markClickListener);
        sqr.setOnClickListener(markClickListener);
        root.setOnClickListener(markClickListener);
        equal.setOnClickListener(markClickListener);
        clear.setOnClickListener(markClickListener);
        bracket.setOnClickListener(markClickListener);
        binary.setOnClickListener(markClickListener);
        hexa.setOnClickListener(markClickListener);
        sort.setOnClickListener(markClickListener);
        backBtn.setOnClickListener(markClickListener);
        dot.setOnClickListener(markClickListener);
    }

    // 숫자 버튼이 눌렸을 경우
    private final Button.OnClickListener numClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            select(view);
            switch (view.getId()) {
                case R.id.numBtn0:
                    edit_process.append("0");
                    break;
                case R.id.numBtn1:
                    edit_process.append("1");
                    break;
                case R.id.numBtn2:
                    edit_process.append("2");
                    break;
                case R.id.numBtn3:
                    edit_process.append("3");
                    break;
                case R.id.numBtn4:
                    edit_process.append("4");
                    break;
                case R.id.numBtn5:
                    edit_process.append("5");
                    break;
                case R.id.numBtn6:
                    edit_process.append("6");
                    break;
                case R.id.numBtn7:
                    edit_process.append("7");
                    break;
                case R.id.numBtn8:
                    edit_process.append("8");
                    break;
                case R.id.numBtn9:
                    edit_process.append("9");
                    break;
            }

            preview();
        }
    };


    //기호 버튼이 눌렸을 경우
    private final Button.OnClickListener markClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addBtn:
                    edit_arith.setText(" ");
                    edit_process.append(" + ");
                    edit_arith.append(" + ");
                    isPreview = true;
                    break;

                case R.id.subBtn:
                    edit_arith.setText(" ");
                    edit_process.append(" - ");
                    edit_arith.append(" - ");
                    isPreview = true;
                    break;

                case R.id.mulBtn:
                    edit_arith.setText(" ");
                    edit_process.append(" * ");
                    edit_arith.append(" * ");
                    isPreview = true;
                    break;

                case R.id.divBtn:
                    edit_arith.setText(" ");
                    edit_process.append(" / ");
                    edit_arith.append(" / ");
                    isPreview = true;
                    break;

                case R.id.clear:
                    edit_process.setText("");
                    edit_result.setText("");
                    edit_arith.setText("");

                    calculateHelper = new CalculateHelper();

                    isPreview = false;

                    break;

                case R.id.bracket:
                    if (!isBracket) {
                        edit_process.append("( ");
                        isBracket = true;
                    } else {
                        edit_process.append(" )");
                        isBracket = false;
                    }

                    isPreview = true;

                    break;

                case R.id.backBtn:
                    size = edit_process.getText().length();

                    if (size != 0)
                        edit_process.setText(edit_process.getText().toString().substring(0, size - 1));

                    if (size > 1) {
                        if (calculateHelper.checkNumber(edit_process.getText().toString().substring(size - 2)))
                            preview();
                        else {
                            isPreview = false;
                            edit_result.setText("");
                        }
                    }

                    break;

                case R.id.dot:
                    edit_process.append(".");
                    isDot = true;
                    break;

                case R.id.sqr:
                    String[] sqa;
                    String sqb = "";
                    result = "";
                    sqa = edit_process.getText().toString().split(" ");
                    sqb = sqa[sqa.length-1];
                    double resultSqr = Double.parseDouble(sqb);
                    double sqr = resultSqr * resultSqr;
                    sqr = Math.floor(sqr*100)/100;
                    if (!isDot || !sqb.contains(".")) {
                        sqa[sqa.length-1] = String.valueOf((int) sqr);
                        isDot = false;
                    } else {
                        sqa[sqa.length-1] = String.valueOf(sqr);
                        isDot = true;
                    }
                    for(String date : sqa){
                        result += date + " ";
                    }
                    edit_process.setText(result);
                    isPreview = true;
                    preview();
                    break;

                case R.id.root:
                    String[] roa;
                    String rob = "";
                    result = "";
                    roa = edit_process.getText().toString().split(" ");
                    rob = roa[roa.length-1];
                    double root = Math.sqrt(Double.parseDouble(rob));
                    root = Math.floor(root*100)/100;
                    int root1 = (int) root;
                    double root2 = root - root1;
                    if (!isDot && !(root2 > 0.0)) {
                        roa[roa.length-1] = String.valueOf((int) root);
                    }else {
                        roa[roa.length-1] = String.valueOf(root);
                        isDot = true;
                    }
                    for(String date : roa){
                        result += date + " ";
                    }
                    edit_process.setText(result);
                    isPreview = true;
                    preview();
                    break;

                case R.id.sort:
                    String sortStr = edit_process.getText().toString();
                    String sortresult = calculateHelper.sorted(sortStr);
                    edit_process.setText(sortresult);
                    preview();
                    break;

                case R.id.equal:
                    result = edit_process.getText().toString();
                    double r = calculateHelper.process(result);

                    if (!isDot)
                        edit_process.setText(String.valueOf((int) r));
                    else
                        edit_process.setText(String.valueOf(r));

                    edit_result.setText("");
                    edit_arith.setText("");
                    isDot = false;
                    isPreview = false;
                    break;

                case R.id.binary:
                    Intent intent = new Intent(getApplicationContext(), Arithmetics_Change.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;

                /*case R.id.hexa:
                    Intent intentHe = new Intent(getApplicationContext(), HexaActivity.class);
                    intentHe.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentHe);
                    break;*/
            }
        }
    };

    private void preview() {
        if (isPreview) {
            result = edit_process.getText().toString();
            double r = calculateHelper.process(result);

            if (!isDot) {
                edit_result.setText(String.valueOf((int) r));
            }else {
                r = Math.floor(r * 100) / 100;
                edit_result.setText(String.valueOf(r));
            }
        }
    }

    private void setTextView() {
        edit_process = findViewById(R.id.edit_process);
        edit_result = findViewById(R.id.edit_result);
        edit_arith = findViewById(R.id.edit_arith);
    }

    private void select(View view2) {
        if (view != null) {
            if (view.getId() != view2.getId()) {
                view.setSelected(false);
            }
        }
        view2.setSelected(true);
        view = view2;
    }

    // 핸들러 세팅
    public void setHandler(Button button) {
        handler_up = new Handler();
        handler_down = new Handler();
        runnable_up = new Runnable() {
            @Override
            public void run() {
                String st = null;
                if(button != null) {
                    st = (String) button.getText();
                }
                if(st != null) {
                    st = st.trim();
                    if(st.length() != 0) {
                        edit_result.append(st);
                        edit_process.append(st);
                    }
                }
                handler_up.postDelayed(this, 100);
            }
        };
        runnable_down = new Runnable() {
            @Override
            public void run() {
                int size = edit_result.getText().length();
                int size1 = edit_process.getText().length();
                if (size >= 1) {
                    edit_result.setText(edit_result.getText().toString().substring(0, size - 1));
                }
                if(size1 >=1){
                    edit_process.setText(edit_process.getText().toString().substring(0, size1 - 1));
                }
                handler_down.postDelayed(this,100);
            }
        };
    }

    // getter
    public Handler getHandler_up() { return handler_up; }
    public Handler getHandler_down() { return handler_down; }
    public Runnable getRunnable_up() { return runnable_up; }
    public Runnable getRunnable_down() { return runnable_down; }
}