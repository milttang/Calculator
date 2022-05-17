package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HexaActivity extends AppCompatActivity  {

    private CalculateHelper calculateHelper;

    private boolean isDot, isBracket, isPreview;

    private TextView textView, textView2;

    private int size;

    private String result;

    private View view;

    Button[] button = new Button[10];

    private Button add, sub, mul, div, clear, bracket, mod, back, dot, equal;

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

    private void setButton() {

        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
        clear = findViewById(R.id.clear);
        bracket = findViewById(R.id.bracket);
        back = findViewById(R.id.backBtn);
        dot = findViewById(R.id.dot);
        equal = findViewById(R.id.equal);

        // number ClickListener
        Integer[] btn ={R.id.numBtn0, R.id.numBtn1, R.id.numBtn2,
                R.id.numBtn3, R.id.numBtn4, R.id.numBtn5,
                R.id.numBtn6, R.id.numBtn7, R.id.numBtn8, R.id.numBtn9};

        for(int i = 0; i<button.length; i++) {
            button[i] = findViewById(btn[i]);
            button[i].setOnClickListener(numClickListener);
        }

        // mark ClickListener
        add.setOnClickListener(markClickListener);
        sub.setOnClickListener(markClickListener);
        mul.setOnClickListener(markClickListener);
        div.setOnClickListener(markClickListener);
        clear.setOnClickListener(markClickListener);
        bracket.setOnClickListener(markClickListener);
        mod.setOnClickListener(markClickListener);
        back.setOnClickListener(markClickListener);
        dot.setOnClickListener(markClickListener);
        equal.setOnClickListener(markClickListener);
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
                    textView.append(" + ");
                    isPreview = true;
                    break;

                case R.id.sub:
                    textView.append(" - ");
                    isPreview = true;
                    break;

                case R.id.mul:
                    textView.append(" * ");
                    isPreview = true;
                    break;

                case R.id.div:
                    textView.append(" / ");
                    isPreview = true;
                    break;

                case R.id.clear:
                    textView.setText("");
                    textView2.setText("");

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

                case R.id.equal:
                    result = textView.getText().toString();
                    double r = calculateHelper.process(result);

                    if (!isDot)
                        textView.setText(String.valueOf((int) r));
                    else
                        textView.setText(String.valueOf(r));

                    textView2.setText("");
                    isDot = false;
                    isPreview = false;
                    break;

                case R.id.binary :
                    Intent intent = new Intent(getApplicationContext(), BinaryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
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