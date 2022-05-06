package com.example.calculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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

    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, add, sub, mul, div, clear, bracket, back, dot, equal, hexa, binary, sqr, root, sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
//        int orientation = display.getRotation();
//
//        if(orientation== Surface.ROTATION_180)
//        {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//        }

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
        num0 = findViewById(R.id.num0);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);

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
        back = findViewById(R.id.back);
        dot = findViewById(R.id.dot);


        // number ClickListener
        num0.setOnClickListener(numClickListener);
        num1.setOnClickListener(numClickListener);
        num2.setOnClickListener(numClickListener);
        num3.setOnClickListener(numClickListener);
        num4.setOnClickListener(numClickListener);
        num5.setOnClickListener(numClickListener);
        num6.setOnClickListener(numClickListener);
        num7.setOnClickListener(numClickListener);
        num8.setOnClickListener(numClickListener);
        num9.setOnClickListener(numClickListener);

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
        back.setOnClickListener(markClickListener);
        dot.setOnClickListener(markClickListener);
    }

    // 숫자 버튼이 눌렸을 경우
    private final Button.OnClickListener numClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            select(view);
            switch (view.getId()) {
                case R.id.num0:
                    textView.append("0");
                    break;
                case R.id.num1:
                    textView.append("1");
                    break;
                case R.id.num2:
                    textView.append("2");
                    break;
                case R.id.num3:
                    textView.append("3");
                    break;
                case R.id.num4:
                    textView.append("4");
                    break;
                case R.id.num5:
                    textView.append("5");
                    break;
                case R.id.num6:
                    textView.append("6");
                    break;
                case R.id.num7:
                    textView.append("7");
                    break;
                case R.id.num8:
                    textView.append("8");
                    break;
                case R.id.num9:
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

                case R.id.back:
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
}