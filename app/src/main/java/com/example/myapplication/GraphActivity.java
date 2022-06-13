package com.example.myapplication;

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

    private TextView functionTest, functionTest1, functionTest2, functionTest3, functionTest4, functionTest5;
    private LineChart chart;
    private Button graph;
    private String function1, function2, function3, empty1, empty2, empty3;
    private static final String xString = "x";
    private static final String yString = "y";
    private static final float startNum = -10.00f;
    private static final float range = 20;
    private static final float step = 0.01f;

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
        graph = findViewById(R.id.graph);
        functionTest = findViewById(R.id.functionTest);
        functionTest1 = findViewById(R.id.functionTest1);
        functionTest2 = findViewById(R.id.functionTest2);
        functionTest3 = findViewById(R.id.functionTest3);
        functionTest4 = findViewById(R.id.functionTest4);
        functionTest5 = findViewById(R.id.functionTest5);

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

        functionTest.setText(function1);
        functionTest1.setText(empty1);
        functionTest2.setText(function2);
        functionTest3.setText(empty2);
        functionTest4.setText(function3);
        functionTest5.setText(empty3);

/*        String functionFirst = functionTest.getText().toString();
        Log.v("functionFirst", "functionFirst : " + functionFirst);
        String functionFirstRemove = functionFirst.replaceFirst("y=", "");
        Log.v("functionFirstRemove", "functionFirstRemove : " + functionFirstRemove);
        String functionOperator = functionFirstRemove.substring(1, 2);
        Log.v("functionOperator", "functionOperator : " + functionOperator);
        String functionNum = functionFirstRemove.substring(2);
        Log.v("functionNum", "functionNum : " + functionNum);*/

        // function2,3이 ""일 떄, 조건 추가하여 Data표시 안되도록
        ArrayList<Entry> xValues = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Entry> firstValues = new ArrayList<>();
        ArrayList<Entry> secondValues = new ArrayList<>();
        ArrayList<Entry> thirdsValues = new ArrayList<>();

        if (!function1.equals("")) {

            String firstFunction = functionTest.getText().toString();
            Log.v("firstFunction", "firstFunction : " + firstFunction);
            String firstFunctionDeleteEqualY = firstFunction.replaceFirst("y=", "");
            Log.v("deleteEqualY", "firstFunctionDeleteEqualY : " + firstFunctionDeleteEqualY);
            String functionFirstOperator = firstFunctionDeleteEqualY.substring(1, 2);
            Log.v("functionFirstOperator", "functionFirstOperator : " + functionFirstOperator);
            String functionFirstNum = firstFunctionDeleteEqualY.substring(2);
            Log.v("functionFirstNum", "functionFirstNum : " + functionFirstNum);
            int functionFindXTest = firstFunctionDeleteEqualY.indexOf(xString);
            Log.v("functionFindXTest", "functionFindXTest : " + functionFindXTest);

            String xStringFront = firstFunctionDeleteEqualY.substring(0, functionFindXTest);
            Log.v("xStringFront", "xStringFront : " + xStringFront);

            /*    x항이 뒤로 갈 경우를 생각     */
            String xStringBehind = firstFunctionDeleteEqualY.substring(functionFindXTest + 1);
            Log.v("xStringBehind", "xStringBehind : " + xStringBehind);

            String[] xStringBehindCal = xStringBehind.split("\\+|-|\\*|/");
            String[] xStringBehindCalTest = xStringBehind.split("");

            Log.v("xStringBehindCal", "xStringBehindCal : " + Arrays.toString(xStringBehindCal));
            Log.v("xStringBehindCalTest", "xStringBehindCalTest : " + Arrays.toString(xStringBehindCalTest));

            /*int xStringBehindCalculate =*/

            boolean xFront = numberCheck(xStringFront);
            String xStringMinus = "";
            if (xStringFront.equals("-")) {
                xStringMinus = xStringFront.substring(1);
                Log.v("xStringMinus", "xStringMinus : " + xStringMinus);
            }

            String operatorCheck = firstFunctionDeleteEqualY.substring(functionFindXTest + 1, functionFindXTest + 2);
            Log.v("operatorCheck", "operatorCheck : " + operatorCheck);

            if (xFront) { /* y = 정수 , x = 정수 , x항과 숫자항이 존재할 때, x항이 뒤로가는 경우는 아직 생각 안함 */
                int xIntFront = Integer.parseInt(xStringFront);
                if (operatorCheck.equals("+")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i + 2;
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("-")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i - Integer.parseInt(functionFirstNum);
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("*")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i * Integer.parseInt(functionFirstNum);
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                } else if (operatorCheck.equals("/")) {
                    for (float i = startNum; i < range; i += step) {
                        float y = xIntFront * i / Integer.parseInt(functionFirstNum);
                        float x = i;
                        firstValues.add(new Entry(x, y));
                    }
                }
            } else {
                if (xStringMinus.equals("-")) {
                    Log.v("xStringMinus", "xStringMinus + " + xStringMinus);
                    if (xStringMinus.equals("")) {

                    } else {

                    }
                    if (operatorCheck.equals("+")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = -1 * i + 2;
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("-")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = -1 * i - Integer.parseInt(functionFirstNum);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("*")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = -1 * i * Integer.parseInt(functionFirstNum);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("/")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = -1 * i / Integer.parseInt(functionFirstNum);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    }
                } else if (xStringFront.equals("")) {
                    if (operatorCheck.equals("+")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i + 2;
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("-")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i - Integer.parseInt(functionFirstNum);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("*")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i * Integer.parseInt(functionFirstNum);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    } else if (operatorCheck.equals("/")) {
                        for (float i = startNum; i < range; i += step) {
                            float y = i / Integer.parseInt(functionFirstNum);
                            float x = i;
                            firstValues.add(new Entry(x, y));
                        }
                    }
                } else {
                    /* cos sin tan loot square */
                }
            }
            Log.v("numberCheck", "numberCheck(xStringFront) : " + numberCheck(xStringFront));


        }
        if (!function2.equals("")) {
            String functionSecond = functionTest2.getText().toString();
            Log.v("functionSecond", "functionSecond : " + functionSecond);
            String functionSecondRemove = functionSecond.replaceFirst("y=", "");
            Log.v("functionSecondRemove", "functionSecondRemove : " + functionSecondRemove);
            String functionSecondOperator = functionSecondRemove.substring(1, 2);
            Log.v("functionSecondOperator", "functionSecondOperator : " + functionSecondOperator);
            String functionSecondNum = functionSecondRemove.substring(2);
            Log.v("functionSecondNum", "functionSecondNum : " + functionSecondNum);

            if (functionSecondOperator.equals("+")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i + Integer.parseInt(functionSecondNum);
                    float x = i;
                    secondValues.add(new Entry(x, y));
                }
            } else if (functionSecondOperator.equals("-")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i - Integer.parseInt(functionSecondNum);
                    float x = i;
                    secondValues.add(new Entry(x, y));
                }
            } else if (functionSecondOperator.equals("*")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i * Integer.parseInt(functionSecondNum);
                    float x = i;
                    secondValues.add(new Entry(x, y));
                }
            } else if (functionSecondOperator.equals("/")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i / Integer.parseInt(functionSecondNum);
                    float x = i;
                    secondValues.add(new Entry(x, y));
                }
            }
        }
        if (!function3.equals("")) {
            String functionThird = functionTest4.getText().toString();
            Log.v("functionThird", "functionThird : " + functionThird);
            String functionThirdRemove = functionThird.replaceFirst("y=", "");
            Log.v("functionThirdRemove", "functionThirdRemove : " + functionThirdRemove);
            String functionThirdOperator = functionThirdRemove.substring(1, 2);
            Log.v("functionThirdOperator", "functionThirdOperator : " + functionThirdOperator);
            String functionThirdNum = functionThirdRemove.substring(2);
            Log.v("functionThirdNum", "functionThirdNum : " + functionThirdNum);

            if (functionThirdOperator.equals("+")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i + Integer.parseInt(functionThirdNum);
                    float x = i;
                    thirdsValues.add(new Entry(x, y));
                }
            } else if (functionThirdOperator.equals("-")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i - Integer.parseInt(functionThirdNum);
                    float x = i;
                    thirdsValues.add(new Entry(x, y));
                }
            } else if (functionThirdOperator.equals("*")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i * Integer.parseInt(functionThirdNum);
                    float x = i;
                    thirdsValues.add(new Entry(x, y));
                }
            } else if (functionThirdOperator.equals("/")) {
                for (float i = startNum; i < range; i += step) {
                    float y = i / Integer.parseInt(functionThirdNum);
                    float x = i;
                    thirdsValues.add(new Entry(x, y));
                }
            }
        }
        for (float i = startNum; i < range; i += step) {
            float y = i;
            float x = 0;
            yValues.add(new Entry(x, y));

        }
        for (float i = startNum; i < range; i += step) {
            float y = 0;
            float x = i;
            xValues.add(new Entry(x, y));

        }

        // x와 y를 Array로 가져온 후, for 문을 통해 ArrayList 추가?
        LineDataSet set1, set2, set3, set4, set5;
        set1 = new LineDataSet(firstValues, function1);
        set2 = new LineDataSet(secondValues, function2);
        set3 = new LineDataSet(thirdsValues, function3);
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
        set4.setColor(Color.LTGRAY);
        set4.setCircleColor(Color.LTGRAY);
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