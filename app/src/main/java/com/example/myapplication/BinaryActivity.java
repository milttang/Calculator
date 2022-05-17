package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BinaryActivity extends AppCompatActivity {

    private CalculateHelper calculateHelper;

    private boolean isDot, isBracket, isPreview;

    private TextView textView, textView2, textView3;

    private int size;

    private String result;

    private View view;

    private Button backBtn, clearBtn, homeBtn, hexaBtn, andBtn, orBtn, xorBtn, numBtn1
            , bracketBtn, addBtn, mulBtn, numBtn0, equalBtn, subBtn, divBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_binary);

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

    private void setButton() {
        numBtn0 = findViewById(R.id.numBtn0);
        numBtn1 = findViewById(R.id.numBtn1);

        backBtn = findViewById(R.id.backBtn);
        clearBtn = findViewById(R.id.clearBtn);
        homeBtn = findViewById(R.id.homeBtn);
        hexaBtn = findViewById(R.id.hexaBtn);
        andBtn = findViewById(R.id.andBtn);
        orBtn = findViewById(R.id.orBtn);
        xorBtn = findViewById(R.id.xorBtn);
        bracketBtn = findViewById(R.id.bracketBtn);
        addBtn = findViewById(R.id.addBtn);
        mulBtn = findViewById(R.id.mulBtn);
        equalBtn = findViewById(R.id.equalBtn);
        subBtn = findViewById(R.id.subBtn);
        divBtn = findViewById(R.id.divBtn);


        // number ClickListener
        numBtn0.setOnClickListener(numClickListener);
        numBtn1.setOnClickListener(numClickListener);

        // mark ClickListener
        backBtn.setOnClickListener(markClickListener);
        clearBtn.setOnClickListener(markClickListener);
        homeBtn.setOnClickListener(markClickListener);
        hexaBtn.setOnClickListener(markClickListener);
        andBtn.setOnClickListener(markClickListener);
        orBtn.setOnClickListener(markClickListener);
        xorBtn.setOnClickListener(markClickListener);
        bracketBtn.setOnClickListener(markClickListener);
        addBtn.setOnClickListener(markClickListener);
        mulBtn.setOnClickListener(markClickListener);
        equalBtn.setOnClickListener(markClickListener);
        subBtn.setOnClickListener(markClickListener);
        divBtn.setOnClickListener(markClickListener);
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
            }
            preview();
        }
    };

    //기호 버튼이 눌렸을 경우
    private final Button.OnClickListener markClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
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

                case R.id.clearBtn:
                    textView.setText("");
                    textView2.setText("");

                    calculateHelper = new CalculateHelper();

                    isPreview = false;

                    break;

                case R.id.homeBtn:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    break;

                case R.id.hexaBtn:
                    Intent intentHe = new Intent(getApplicationContext(), HexaActivity.class);
                    intentHe.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentHe);
                    break;

                case R.id.andBtn:
                    textView.setText("AND");
                    textView2.setText("AND");

                    calculateHelper = new CalculateHelper();

                    isPreview = false;

                    break;

                case R.id.orBtn:
                    if (!isBracket) {
                        textView.append("( ");
                        isBracket = true;
                    } else {
                        textView.append(" )");
                        isBracket = false;
                    }

                    isPreview = true;

                    break;

                case R.id.xorBtn:
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

                case R.id.bracketBtn:
                    if (!isBracket) {
                        textView.append("( ");
                        isBracket = true;
                    } else {
                        textView.append(" )");
                        isBracket = false;
                    }

                    isPreview = true;

                    break;

                case R.id.addBtn:
                    textView3.setText(" ");
                    textView.append(" + ");
                    textView3.append(" + ");
                    isPreview = true;
                    break;

                case R.id.mulBtn:
                    textView3.setText(" ");
                    textView.append(" * ");
                    textView3.append(" * ");
                    isPreview = true;
                    break;

                case R.id.subBtn:
                    textView3.setText(" ");
                    textView.append(" - ");
                    textView3.append(" - ");
                    isPreview = true;
                    break;

                case R.id.divBtn:
                    textView3.setText(" ");
                    textView.append(" / ");
                    textView3.append(" / ");
                    isPreview = true;
                    break;

                case R.id.equalBtn:
                    result = textView.getText().toString();
                    double r = calculateHelper.process(result);
                    String binaryResult = Double.toString(r);

                    textView.setText(binaryResult);

                    textView2.setText("");
                    textView3.setText("");
                    isPreview = false;
                    break;
            }
        }
    };

    private void preview() {
        if (isPreview) {
            result = textView.getText().toString();
            double r = calculateHelper.process(result);
            String binaryResult = Double.toString(r);

            textView2.setText(binaryResult);
        }
    }

    private void setTextView() {
        textView = findViewById(R.id.first_textView);
        textView2 = findViewById(R.id.second_textView);
        textView3 = findViewById(R.id.third_textView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
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
