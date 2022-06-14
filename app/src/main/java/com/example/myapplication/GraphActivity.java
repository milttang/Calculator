package com.example.myapplication;

import static android.content.ContentValues.TAG;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

    private TextView functionOne, functionTwo, functionThree, emptyOne, emptyTwo, emptyThree;
    private LineChart chart;
    private Button graph;
    private String function1, function2, function3, empty1, empty2, empty3;
    private static final String xString = "x";
    private static final String yString = "y";
    private static final float startNum = -10.00f;
    private static final float range = 20;
    private static final float step = 0.01f;
    private String xStringMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        chart = findViewById(R.id.linechart);
        graph = findViewById(R.id.graph);
        functionOne = findViewById(R.id.functionOne);
        functionTwo = findViewById(R.id.functionTwo);
        functionThree = findViewById(R.id.functionThree);
        emptyOne = findViewById(R.id.emptyOne);
        emptyTwo = findViewById(R.id.emptyTwo);
        emptyThree = findViewById(R.id.emptyThree);

        graph.setOnClickListener(this);

        // 전달 받은 Data 확인
        Bundle extras = getIntent().getExtras();
        if (extras.getString("function1").equals("")) {
            function1 = "";
        } else {
            function1 = extras.getString("function1");
        }
        if (extras.getString("empty1").equals("")) {
            empty1 = "";
        } else {
            empty1 = extras.getString("empty1");
        }
        if (extras.getString("function2").equals("")) {
            function2 = "";
        } else {
            function2 = extras.getString("function2");
        }
        if (extras.getString("empty2").equals("")) {
            empty2 = "";
        } else {
            empty2 = extras.getString("empty2");
        }
        if (extras.getString("function3").equals("")) {
            function3 = "";
        } else {
            function3 = extras.getString("function3");
        }
        if (extras.getString("empty3").equals("")) {
            empty3 = "";
        } else {
            empty3 = extras.getString("empty3");
        }

        // 각각의 항목에 Data Setting
        functionOne.setText(function1);
        emptyOne.setText(empty1);
        functionTwo.setText(function2);
        emptyTwo.setText(empty2);
        functionThree.setText(function3);
        emptyThree.setText(empty3);

        // function2,3이 ""일 떄, 조건 추가하여 Data표시 안되도록
        ArrayList<Entry> xValues = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Entry> firstValues = new ArrayList<>();
        ArrayList<Entry> secondValues = new ArrayList<>();
        ArrayList<Entry> thirdValues = new ArrayList<>();

        // function1
        if (!function1.equals("")) {

            /* 전체 함수 읽어온 String */
            String firstFunction = functionOne.getText().toString();
            Log.v("firstFunction", "firstFunction : " + firstFunction);

            /* 전체 함수에서 y=을 제거한 String */
            String firstFunctionDeleteEqualY = firstFunction.replaceFirst("y=", "");
            Log.v("deleteEqualY", "firstFunctionDeleteEqualY : " + firstFunctionDeleteEqualY);

            String firstfunctionOperator = firstFunctionDeleteEqualY.substring(1, 2);
            Log.v("firstfunctionOperator", "firstfunctionOperator : " + firstfunctionOperator);
            String firstNum = firstFunctionDeleteEqualY.substring(2);
            Log.v("firstNum", "firstNum : " + firstNum);

            /* 전체 함수에서 y=을 제거 후, x의 index 확인 */
            int firstFindXTest = firstFunctionDeleteEqualY.indexOf(xString);
            Log.v("firstFindXTest", "firstFindXTest : " + firstFindXTest);

            /* x앞에 붙어 있는 숫자 및 문자열 확인 */
            String xFirstFront = firstFunctionDeleteEqualY.substring(0, firstFindXTest);
            Log.v("xFirstFront", "xFirstFront : " + xFirstFront);

            /* x뒤에 붙어 있는 계산 및 연산 String 확인 */
            String xFirstBehind = firstFunctionDeleteEqualY.substring(firstFindXTest + 1);
            String xFirstBehindNumber = xFirstBehind.replaceAll("[^0-9]", "");
            Log.v("xFirstBehind", "xFirstBehind : " + xFirstBehind);
            Log.v("xFirstBehindNumber", "xFirstBehindNumber : " + xFirstBehindNumber);

            /* 연산 String 을 Parsing 하여 계산 */
            String[] xFirstBehindArray = xFirstBehind.split("");
            String xFirstBehindCalculate = "";
            int behindCalculateResult = 0;
            if (xFirstBehindArray.length >= 4) {
                switch (xFirstBehindArray[3]) {
                    case "+":
                        behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) + Integer.parseInt(xFirstBehindArray[4]);
                        break;
                    case "-":
                        behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) - Integer.parseInt(xFirstBehindArray[4]);
                        break;
                    case "*":
                        behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) * Integer.parseInt(xFirstBehindArray[4]);
                        break;
                    case "/":
                        behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]) / Integer.parseInt(xFirstBehindArray[4]);
                        break;
                }
            } else if (xFirstBehindArray.length >= 3) {
                behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]);
            }
            String behindCalculate = Integer.toString(behindCalculateResult);

            /* x앞에 오는 값이 숫자인지 문자인지 확인 */
            boolean xFront = numberCheck(xFirstFront);

            String operatorCheck = firstFunctionDeleteEqualY.substring(firstFindXTest + 1, firstFindXTest + 2);
            Log.v("operatorCheck", "operatorCheck : " + operatorCheck);
            int xIntFront = 0;
            if (!xFirstFront.equals("")) {
                Log.d("xFirstFront : ", xFirstFront);
                xIntFront = Integer.parseInt(xFirstFront);
            }
            if (xFront) { /* y = 정수 , x = 정수 , x항과 숫자항이 존재할 때, x항이 뒤로가는 경우는 아직 생각 안함 */
                if (operatorCheck.equals("+")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i + Integer.parseInt(behindCalculate);
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("-")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i - Integer.parseInt(behindCalculate);
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("*")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i * Integer.parseInt(behindCalculate);
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("/")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i / Integer.parseInt(behindCalculate);
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                }
            } else {
                if (xFirstFront.equals("-")) {
                    xStringMinus = xFirstFront.substring(1);
                    if (numberCheck(xStringMinus)) {
                        if (operatorCheck.equals("+")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i + 2;
                                float x = i;
                                firstValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("-")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i - Integer.parseInt(behindCalculate);
                                float x = i;
                                firstValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("*")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i * Integer.parseInt(behindCalculate);
                                float x = i;
                                firstValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("/")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                float x = i;
                                firstValues.add(new Entry(x, y));
                            }
                        } else if ("1" == "2") {
                            // 부호다음에 cos sin 같은 문자 오는거 생각
                        }
                    } else {
                        if (xFirstFront.charAt(1) == 's') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) + 2;
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (behindCalculate.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (behindCalculate.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) / Integer.parseInt(xFirstBehindNumber);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            }
                        } else if (xFirstFront.charAt(1) == 'c') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) + 2;
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) - Integer.parseInt(xFirstBehindNumber);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) * Integer.parseInt(xFirstBehindNumber);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            }
                        } else if (xFirstFront.charAt(1) == 't') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) + 2;
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    firstValues.add(new Entry(x, y));
                                }
                            }
                        }
                    }
//                Log.v("xStringMinus", "xStringMinus : " + xStringMinus);

                } else if (xFirstFront.equals("")) {    //루트랑 스퀘어 추가 생각 필요
                    if (operatorCheck.equals("+")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i + 2;
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("-")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i - Integer.parseInt(behindCalculate);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("*")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i * Integer.parseInt(behindCalculate);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("/")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i / Integer.parseInt(behindCalculate);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    }
                }
            }
        }

        // function2
        if (!function2.equals("")) {

            /* 전체 함수 읽어온 String */
            String secondFunction = functionTwo.getText().toString();
            Log.v("secondFunction", "secondFunction : " + secondFunction);

            /* 전체 함수에서 y=을 제거한 String */
            String secondFunctionDeleteEqualY = secondFunction.replaceFirst("y=", "");
            Log.v("deleteEqualY", "secondFunctionDeleteEqualY : " + secondFunctionDeleteEqualY);

            String secondfunctionOperator = secondFunctionDeleteEqualY.substring(1, 2);
            Log.v("secondfunctionOperator", "secondfunctionOperator : " + secondfunctionOperator);
            String secondNum = secondFunctionDeleteEqualY.substring(2);
            Log.v("secondNum", "secondNum : " + secondNum);

            /* 전체 함수에서 y=을 제거 후, x의 index 확인 */
            int secondFindXTest = secondFunctionDeleteEqualY.indexOf(xString);
            Log.v("secondFindXTest", "secondFindXTest : " + secondFindXTest);

            /* x앞에 붙어 있는 숫자 및 문자열 확인 */
            String xSecondFront = secondFunctionDeleteEqualY.substring(0, secondFindXTest);
            Log.v("xSecondFront", "xSecondFront : " + xSecondFront);

            /* x뒤에 붙어 있는 계산 및 연산 String 확인 */
            String xSecondBehind = secondFunctionDeleteEqualY.substring(secondFindXTest + 1);
            String xSecondBehindNumber = xSecondBehind.replaceAll("[^0-9]", "");
            Log.v("xSecondBehind", "xSecondBehind : " + xSecondBehind);
            Log.v("xSecondBehindNumber", "xSecondBehindNumber : " + xSecondBehindNumber);

            /* 연산 String 을 Parsing 하여 계산 */
            String[] xSecondBehindArray = xSecondBehind.split("");
            String xSecondBehindCalculate = "";
            int behindCalculateResult = 0;
            if (xSecondBehindArray.length >= 4) {
                switch (xSecondBehindArray[3]) {
                    case "+":
                        behindCalculateResult = Integer.parseInt(xSecondBehindArray[2]) + Integer.parseInt(xSecondBehindArray[4]);
                        break;
                    case "-":
                        behindCalculateResult = Integer.parseInt(xSecondBehindArray[2]) - Integer.parseInt(xSecondBehindArray[4]);
                        break;
                    case "*":
                        behindCalculateResult = Integer.parseInt(xSecondBehindArray[2]) * Integer.parseInt(xSecondBehindArray[4]);
                        break;
                    case "/":
                        behindCalculateResult = Integer.parseInt(xSecondBehindArray[2]) / Integer.parseInt(xSecondBehindArray[4]);
                        break;
                }
            } else if (xSecondBehindArray.length >= 3) {
                behindCalculateResult = Integer.parseInt(xSecondBehindArray[2]);
            }
            String behindCalculate = Integer.toString(behindCalculateResult);

            /* x앞에 오는 값이 숫자인지 문자인지 확인 */
            boolean xFront = numberCheck(xSecondFront);

            String operatorCheck = secondFunctionDeleteEqualY.substring(secondFindXTest + 1, secondFindXTest + 2);
            Log.v("operatorCheck", "operatorCheck : " + operatorCheck);
            int xIntFront = 0;
            if (xSecondFront != "") {
                xIntFront = Integer.parseInt(xSecondFront);
            }
            if (xFront) { /* y = 정수 , x = 정수 , x항과 숫자항이 존재할 때, x항이 뒤로가는 경우는 아직 생각 안함 */
                if (operatorCheck.equals("+")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i + Integer.parseInt(behindCalculate);
                        float x = i;
                        secondValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("-")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i - Integer.parseInt(behindCalculate);
                        float x = i;
                        secondValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("*")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i * Integer.parseInt(behindCalculate);
                        float x = i;
                        secondValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("/")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i / Integer.parseInt(behindCalculate);
                        float x = i;
                        secondValues.add(new Entry(x, y));
                    }
                }
            } else {
                if (xSecondFront.charAt(0) == '-') {
                    xStringMinus = xSecondFront.substring(1);
                    if (numberCheck(xStringMinus)) {
                        if (operatorCheck.equals("+")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i + 2;
                                float x = i;
                                secondValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("-")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i - Integer.parseInt(behindCalculate);
                                float x = i;
                                secondValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("*")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i * Integer.parseInt(behindCalculate);
                                float x = i;
                                secondValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("/")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                float x = i;
                                secondValues.add(new Entry(x, y));
                            }
                        } else if ("1" == "2") {
                            // 부호다음에 cos sin 같은 문자 오는거 생각
                        }
                    } else {
                        if (xSecondFront.charAt(1) == 's') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) + 2;
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (behindCalculate.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (behindCalculate.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) / Integer.parseInt(xSecondBehindNumber);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            }
                        } else if (xSecondFront.charAt(1) == 'c') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) + 2;
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) - Integer.parseInt(xSecondBehindNumber);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) * Integer.parseInt(xSecondBehindNumber);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            }
                        } else if (xSecondFront.charAt(1) == 't') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) + 2;
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    secondValues.add(new Entry(x, y));
                                }
                            }
                        }
                    }
//                Log.v("xStringMinus", "xStringMinus : " + xStringMinus);

                } else if (xSecondFront.equals("")) {    //루트랑 스퀘어 추가 생각 필요
                    if (operatorCheck.equals("+")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i + 2;
                            float x = i;
                            secondValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("-")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i - Integer.parseInt(behindCalculate);
                            float x = i;
                            secondValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("*")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i * Integer.parseInt(behindCalculate);
                            float x = i;
                            secondValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("/")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i / Integer.parseInt(behindCalculate);
                            float x = i;
                            secondValues.add(new Entry(x, y));
                        }
                    }
                }
            }
        }

        // function3
        if (!function3.equals("")) {

            /* 전체 함수 읽어온 String */
            String thirdFunction = functionThree.getText().toString();
            Log.v("thirdFunction", "thirdFunction : " + thirdFunction);

            /* 전체 함수에서 y=을 제거한 String */
            String thirdFunctionDeleteEqualY = thirdFunction.replaceFirst("y=", "");
            Log.v("deleteEqualY", "thirdFunctionDeleteEqualY : " + thirdFunctionDeleteEqualY);

            String thirdfunctionOperator = thirdFunctionDeleteEqualY.substring(1, 2);
            Log.v("thirdfunctionOperator", "thirdfunctionOperator : " + thirdfunctionOperator);
            String thirdNum = thirdFunctionDeleteEqualY.substring(2);
            Log.v("thirdNum", "thirdNum : " + thirdNum);

            /* 전체 함수에서 y=을 제거 후, x의 index 확인 */
            int thirdFindXTest = thirdFunctionDeleteEqualY.indexOf(xString);
            Log.v("thirdFindXTest", "thirdFindXTest : " + thirdFindXTest);

            /* x앞에 붙어 있는 숫자 및 문자열 확인 */
            String xThirdFront = thirdFunctionDeleteEqualY.substring(0, thirdFindXTest);
            Log.v("xThirdFront", "xThirdFront : " + xThirdFront);

            /* x뒤에 붙어 있는 계산 및 연산 String 확인 */
            String xThirdBehind = thirdFunctionDeleteEqualY.substring(thirdFindXTest + 1);
            String xThirdBehindNumber = xThirdBehind.replaceAll("[^0-9]", "");
            Log.v("xThirdBehind", "xThirdBehind : " + xThirdBehind);
            Log.v("xThirdBehindNumber", "xThirdBehindNumber : " + xThirdBehindNumber);

            /* 연산 String 을 Parsing 하여 계산 */
            String[] xThirdBehindArray = xThirdBehind.split("");
            String xThirdBehindCalculate = "";
            int behindCalculateResult = 0;
            if (xThirdBehindArray.length >= 4) {
                switch (xThirdBehindArray[3]) {
                    case "+":
                        behindCalculateResult = Integer.parseInt(xThirdBehindArray[2]) + Integer.parseInt(xThirdBehindArray[4]);
                        break;
                    case "-":
                        behindCalculateResult = Integer.parseInt(xThirdBehindArray[2]) - Integer.parseInt(xThirdBehindArray[4]);
                        break;
                    case "*":
                        behindCalculateResult = Integer.parseInt(xThirdBehindArray[2]) * Integer.parseInt(xThirdBehindArray[4]);
                        break;
                    case "/":
                        behindCalculateResult = Integer.parseInt(xThirdBehindArray[2]) / Integer.parseInt(xThirdBehindArray[4]);
                        break;
                }
            } else if (xThirdBehindArray.length >= 3) {
                behindCalculateResult = Integer.parseInt(xThirdBehindArray[2]);
            }
            String behindCalculate = Integer.toString(behindCalculateResult);

            /* x앞에 오는 값이 숫자인지 문자인지 확인 */
            boolean xFront = numberCheck(xThirdFront);

            String operatorCheck = thirdFunctionDeleteEqualY.substring(thirdFindXTest + 1, thirdFindXTest + 2);
            Log.v("operatorCheck", "operatorCheck : " + operatorCheck);
            int xIntFront = 0;
            if (xThirdFront != "") {
                xIntFront = Integer.parseInt(xThirdFront);
            }
            if (xFront) { /* y = 정수 , x = 정수 , x항과 숫자항이 존재할 때, x항이 뒤로가는 경우는 아직 생각 안함 */
                if (operatorCheck.equals("+")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i + Integer.parseInt(behindCalculate);
                        float x = i;
                        thirdValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("-")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i - Integer.parseInt(behindCalculate);
                        float x = i;
                        thirdValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("*")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i * Integer.parseInt(behindCalculate);
                        float x = i;
                        thirdValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("/")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i / Integer.parseInt(behindCalculate);
                        float x = i;
                        thirdValues.add(new Entry(x, y));
                    }
                }
            } else {
                if (xThirdFront.charAt(0) == '-') {
                    xStringMinus = xThirdFront.substring(1);
                    if (numberCheck(xStringMinus)) {
                        if (operatorCheck.equals("+")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i + 2;
                                float x = i;
                                thirdValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("-")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i - Integer.parseInt(behindCalculate);
                                float x = i;
                                thirdValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("*")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i * Integer.parseInt(behindCalculate);
                                float x = i;
                                thirdValues.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("/")) {
                            for (float i = startNum; i < range; i += step) {
                                float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                float x = i;
                                thirdValues.add(new Entry(x, y));
                            }
                        } else if ("1" == "2") {
                            // 부호다음에 cos sin 같은 문자 오는거 생각
                        }
                    } else {
                        if (xThirdFront.charAt(1) == 's') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) + 2;
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (behindCalculate.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (behindCalculate.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) sin(i) / Integer.parseInt(xThirdBehindNumber);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            }
                        } else if (xThirdFront.charAt(1) == 'c') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) + 2;
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) - Integer.parseInt(xThirdBehindNumber);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) * Integer.parseInt(xThirdBehindNumber);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) cos(i) / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            }
                        } else if (xThirdFront.charAt(1) == 't') {
                            if (operatorCheck.equals("+")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) + 2;
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = startNum; i < range; i += step) {
                                    float y = (float) tan(i) / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    thirdValues.add(new Entry(x, y));
                                }
                            }
                        }
                    }
//                Log.v("xStringMinus", "xStringMinus : " + xStringMinus);

                } else if (xThirdFront.equals("")) {    //루트랑 스퀘어 추가 생각 필요
                    if (operatorCheck.equals("+")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i + 2;
                            float x = i;
                            thirdValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("-")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i - Integer.parseInt(behindCalculate);
                            float x = i;
                            thirdValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("*")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i * Integer.parseInt(behindCalculate);
                            float x = i;
                            thirdValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("/")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i / Integer.parseInt(behindCalculate);
                            float x = i;
                            thirdValues.add(new Entry(x, y));
                        }
                    }
                }
            }
        }

        xValues.add(new Entry(-10,0));
        xValues.add(new Entry(20,0));
        yValues.add(new Entry(0,-10));
        yValues.add(new Entry(0,20));

        // x와 y를 Array로 가져온 후, for 문을 통해 ArrayList 추가?
        LineDataSet set1, set2, set3, set4, set5;
        set1 = new LineDataSet(firstValues, function1);
        set2 = new LineDataSet(secondValues, function2);
        set3 = new LineDataSet(thirdValues, function3);
        set4 = new LineDataSet(xValues, "");
        set5 = new LineDataSet(yValues, "");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set4); // add the data sets
        dataSets.add(set5); // add the data sets
        dataSets.add(set1); // add the data sets
        dataSets.add(set2); // add the data sets
        dataSets.add(set3); // add the data sets


        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        set4.setLineWidth(8.0F);
        set4.setColor(Color.LTGRAY);
        set4.setCircleColor(Color.LTGRAY);
        set5.setLineWidth(8.0F);
        set5.setColor(Color.LTGRAY);
        set5.setCircleColor(Color.LTGRAY);
        set1.setColor(Color.DKGRAY);
        set1.setCircleColor(Color.DKGRAY);
        set2.setColor(Color.GREEN);
        set2.setCircleColor(Color.GREEN);
        set3.setColor(Color.BLUE);
        set3.setCircleColor(Color.BLUE);

        // set data
        chart.setData(data);
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
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }

    public static String[] deleteEmpty(final String[] array) {              // String[]의 Empty Data 삭제
        List<String> list = new ArrayList<>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));                          // list 내부 Data "" 모두 제거
        return list.toArray(new String[list.size()]);
    }

    public static boolean numberCheck(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}