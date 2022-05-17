package com.example.calculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CalculateHelper calculateHelper;

    private boolean isDot, isBracket, isPreview;

    private TextView textView, textView2, textView3;

    private int size;

    private String result;

    private View view;

    private Runnable runnable_up, runnable_down;

    private Handler handler_up, handler_down;

    Button[] button = new Button[10];

    private Button add, sub, mul, div, clear, bracket, backBtn, dot, equal, hexa, binary, sqr, root, sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
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
        add.setOnClickListener(markClickListener);
        sub.setOnClickListener(markClickListener);
        mul.setOnClickListener(markClickListener);
        div.setOnClickListener(markClickListener);
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
                    textView.append("0");
                    break;
                case R.id.numBtn1:
                    textView.append("1");
                    break;
                case R.id.numBtn2:
                    textView.append("2");
                    break;
                case R.id.numBtn3:
                    textView.append("3");
                    break;
                case R.id.numBtn4:
                    textView.append("4");
                    break;
                case R.id.numBtn5:
                    textView.append("5");
                    break;
                case R.id.numBtn6:
                    textView.append("6");
                    break;
                case R.id.numBtn7:
                    textView.append("7");
                    break;
                case R.id.numBtn8:
                    textView.append("8");
                    break;
                case R.id.numBtn9:
                    textView.append("9");
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
                case R.id.add:
                    textView3.setText(" ");
                    textView.append(" + ");
                    textView3.append(" + ");
                    isPreview = true;
                    break;

                case R.id.sub:
                    textView3.setText(" ");
                    textView.append(" - ");
                    textView3.append(" - ");
                    isPreview = true;
                    break;

                case R.id.mul:
                    textView3.setText(" ");
                    textView.append(" * ");
                    textView3.append(" * ");
                    isPreview = true;
                    break;

                case R.id.div:
                    textView3.setText(" ");
                    textView.append(" / ");
                    textView3.append(" / ");
                    isPreview = true;
                    break;

                case R.id.clear:
                    textView.setText("");
                    textView2.setText("");
                    textView3.setText("");

                    calculateHelper = new CalculateHelper();

                    isPreview = false;

                    break;

                case R.id.bracket:
                    if (!isBracket) {
                        textView.append("( ");
                        isBracket = true;
                    } else {
                        textView.append(" )");
                        isBracket = false;
                    }

                    isPreview = true;

                    break;

                case R.id.backBtn:
                    size = textView.getText().length();

                    if (size != 0)
                        textView.setText(textView.getText().toString().substring(0, size - 1));

                    if (size > 1) {
                        if (calculateHelper.checkNumber(textView.getText().toString().substring(size - 2)))
                            preview();
                        else {
                            isPreview = false;
                            textView2.setText("");
                        }
                    }

                    break;

                case R.id.dot:
                    textView.append(".");
                    isDot = true;
                    break;

                case R.id.sqr:
                    result = textView.getText().toString();
                    int resultSqr = Integer.parseInt(result);
                    double sqr = resultSqr * resultSqr;

                    if (!isDot) {
                        textView2.setText(String.valueOf((int) sqr));
                        textView.setText(result + " * " + result);
                    } else {
                        textView2.setText(String.valueOf(sqr));
                        textView.setText(result + " * " + result);
                    }

                    isDot = false;
                    isPreview = false;
                    break;

                case R.id.root:
                    result = textView.getText().toString();
                    int resultRoot = Integer.parseInt(result);
                    double root = resultRoot * resultRoot;

                    if (!isDot)
                        textView.setText(String.valueOf((int) root));
                    else
                        textView.setText(String.valueOf(root));

                    textView2.setText("");
                    isDot = false;
                    isPreview = false;
                    break;

                case R.id.sort:
                    String sortStr = textView.getText().toString();
                    String sortresult = calculateHelper.sorted(sortStr);
                    textView.setText(sortresult);
                    break;

                case R.id.equal:
                    result = textView.getText().toString();
                    double r = calculateHelper.process(result);

                    if (!isDot)
                        textView.setText(String.valueOf((int) r));
                    else
                        textView.setText(String.valueOf(r));

                    textView2.setText("");
                    textView3.setText("");
                    isDot = false;
                    isPreview = false;
                    break;

                case R.id.binary:
                    Intent intent = new Intent(getApplicationContext(), BinaryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;

                case R.id.hexa:
                    Intent intentHe = new Intent(getApplicationContext(), HexaActivity.class);
                    intentHe.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentHe);
                    break;
            }
        }
    };

    private void preview() {
        if (isPreview) {
            result = textView.getText().toString();
            double r = calculateHelper.process(result);

            if (!isDot)
                textView2.setText(String.valueOf((int) r));
            else
                textView2.setText(String.valueOf(r));
        }
    }

    private void setTextView() {
        textView = findViewById(R.id.first_textView);
        textView2 = findViewById(R.id.second_textView);
        textView3 = findViewById(R.id.third_textView);
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
                        textView2.append(st);
                        textView.append(st);
                    }
                }
                handler_up.postDelayed(this, 100);
            }
        };
        runnable_down = new Runnable() {
            @Override
            public void run() {
                int size = textView2.getText().length();
                int size1 = textView.getText().length();
                if (size >= 1) {
                    textView2.setText(textView2.getText().toString().substring(0, size - 1));
                }
                if(size1 >=1){
                    textView.setText(textView.getText().toString().substring(0, size1 - 1));
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