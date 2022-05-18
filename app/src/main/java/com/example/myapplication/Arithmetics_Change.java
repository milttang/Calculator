package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Arithmetics_Change extends AppCompatActivity implements View.OnClickListener {
    private final ImageView[] binaryView = new ImageView[10];               // 계산 결과 값 Image 표시
    private View selectView;                                                // 선택된 버튼 표시
    private TextView process, operator;
    private Button numBtn0, numBtn1, addBtn, subBtn, mulBtn, divBtn, remainBtn, equalBtn, backBtn, clearBtn, homeBtn, andBtn, orBtn, xorBtn, empty;
    private int count = 0;                                                  // Image View 를 위한 Count
    private int result = 0;
    private int firstBinary;
    private int firstLength;
    private int secondBinary;
    private int secondLength;
    public String firstNum = "";
    public String secondNum = "";
    public String firstResultNum = "";
    public String tefirstResultNum = "";
    public String operaTor = "";
    public String resultNum = "";
    public String teresultNum = "";
    public String firstNumNo = "";
    private Toolbar mainToolBar;
    private ActionBarDrawerToggle drawerToggle;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetics_change);

        // toolbar
        mainToolBar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(mainToolBar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolBar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        MenuBarEvent menuBarEvent = new MenuBarEvent(this);
        navigationView.setNavigationItemSelectedListener(menuBarEvent);

        operator = findViewById(R.id.operator);
        process = findViewById(R.id.process);
        numBtn0 = findViewById(R.id.numBtn0);
        numBtn1 = findViewById(R.id.numBtn1);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        divBtn = findViewById(R.id.divBtn);
        remainBtn = findViewById(R.id.remainBtn);
        mulBtn = findViewById(R.id.mulBtn);
        equalBtn = findViewById(R.id.equalBtn);
        backBtn = findViewById(R.id.backBtn);
        clearBtn = findViewById(R.id.clearBtn);
        homeBtn = findViewById(R.id.homeBtn);
        andBtn = findViewById(R.id.andBtn);
        orBtn = findViewById(R.id.orBtn);
        xorBtn = findViewById(R.id.xorBtn);
        empty = findViewById(R.id.empty);

        operator.setOnClickListener(this);
        process.setOnClickListener(this);
        numBtn0.setOnClickListener(this);
        numBtn1.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        remainBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        equalBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        andBtn.setOnClickListener(this);
        orBtn.setOnClickListener(this);
        xorBtn.setOnClickListener(this);
        empty.setOnClickListener(this);

        selectView = null;

        Integer[] res = {R.id.result0, R.id.result1, R.id.result2,
                R.id.result3, R.id.result4, R.id.result5,
                R.id.result6, R.id.result7, R.id.result8, R.id.result9};

        for (int i = 0; i < binaryView.length; i++) {
            binaryView[i] = findViewById(res[i]);
        }
    }

    // Activity 종료 시 효과 제거
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        select(view);
        resultNum = "";
        teresultNum = "";
        switch (view.getId()) {
            case R.id.numBtn0:
                if (count < 10) {                                            //10자리까지만 이미지 나열
                    binaryView[count].setImageResource(R.drawable.zero);
                    count++;
                }
                process.append("0");
                break;

            case R.id.numBtn1:
                if (count < 10) {                                            //10자리까지만 이미지 나열
                    binaryView[count].setImageResource(R.drawable.one);
                    count++;
                }
                process.append("1");
                break;

            case R.id.andBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "AND";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "AND";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("AND");
                }
                operator.setText("AND");
                clear();
                break;

            case R.id.orBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "OR";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "OR";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("OR");
                }
                operator.setText("OR");
                clear();
                break;

            case R.id.xorBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "XOR";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "XOR";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("XOR");
                }
                operator.setText("XOR");
                clear();
                break;

            case R.id.addBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "+";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "+";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("+");
                }
                operator.setText("+");
                clear();
                break;

            case R.id.subBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "-";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "-";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("-");
                }
                operator.setText("-");
                clear();
                break;

            case R.id.mulBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "*";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "*";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("*");
                }
                operator.setText("*");
                clear();
                break;

            case R.id.divBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "/";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "/";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("/");
                }
                operator.setText("/");
                clear();
                break;

            case R.id.remainBtn:
                firstNum = process.getText().toString();
                if (firstNum.equals("")) {
                    if (firstResultNum.equals("")) {
                        firstNum = "0";
                        firstNumNo = firstNum + "%";
                        process.setText(firstNumNo);
                    } else {
                        firstNum = firstResultNum;
                        firstNumNo = firstResultNum + "%";
                        process.setText(firstNumNo);
                    }
                } else {
                    process.append("%");
                }
                operator.setText("%");
                clear();
                break;

            case R.id.backBtn:
                int size = process.getText().length();
                if (count > 0) {
                    binaryView[count - 1].setImageResource(0);
                    count--;
                }
                if (size >= 1) {
                    process.setText(process.getText().toString().substring(0, size - 1));
                }
                break;

            case R.id.clearBtn:
                clear();
                firstResultNum = "";
                tefirstResultNum = "";
                process.setText("");
                operator.setText("");
                break;

            case R.id.hexBtn:
                break;

            case R.id.homeBtn:
                Intent homeIntent = new Intent(getApplicationContext(), Arithmetics.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);     // Activity 전환 시 효과 제거
                startActivity(homeIntent);
                break;

            case R.id.equalBtn:
                clear();
                String extraNum = "";
                String[] processArray = process.getText().toString().split(("\\+|-|\\*|/|AND|OR|XOR|%"));
                Log.v("processArray", "processArray = " + Arrays.toString(processArray));
                Log.v("firstNum", "firstNum = " + firstNum);

                if (processArray.length == 1) {
                    if (firstResultNum.equals("")) {
                        secondNum = firstNum;
                        Log.v("secondNum1", "secondNum1 = " + secondNum);
                    } else {
                        secondNum = firstResultNum;
                        Log.v("secondNum2", "secondNum2 = " + secondNum);
                    }
                    Log.v("secondNum3", "secondNum3 = " + secondNum);
                    extraNum = secondNum;
                } else {
                    secondNum = processArray[processArray.length - 1];
                }

                Log.v("secondNum", "secondNum = " + secondNum);

                if (firstResultNum.equals("")) {
                    firstNum = processArray[0];
                } else {
                    firstNum = firstResultNum;
                }

                firstBinary = Integer.parseInt(firstNum, 2);                               // 2진수 문자열을 10진수로 변환
                firstLength = firstNum.length();                                                // 2진수 문자열의 길이 계산
                secondBinary = Integer.parseInt(secondNum, 2);                             // 2진수 문자열을 10진수로 변환
                secondLength = secondNum.length();                                              // 2진수 문자열의 길이 계산
                firstResultNum = "";                                                            // 저장되어있는 계산 결과 값 초기화
                operaTor = operator.getText().toString();

                switch (operaTor) {
                    case "":
                        result = firstBinary;
                        break;
                    case "+":
                        result = firstBinary + secondBinary;
                        break;
                    case "-":
                        result = firstBinary - secondBinary;
                        break;
                    case "*":
                        result = firstBinary * secondBinary;
                        break;
                    case "/":
                        result = firstBinary / secondBinary;
                        // 2진수 계산 값이 소수값으로 나올 때, 계산 불가 및 Reset 처리
                        if (result == 0) {
                            AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                            myAlertBuilder.setTitle("Alert");
                            myAlertBuilder.setMessage("Cannot Calculate Double Data.");
                            myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // OK 버튼을 눌렸을 경우
                                    Toast.makeText(getApplicationContext(), "Double", Toast.LENGTH_SHORT).show();
                                    clear();
                                    process.setText("");
                                    operator.setText("");
                                }
                            });
                            myAlertBuilder.show();
                        }
                        break;
                    case "%":
                        result = firstBinary % secondBinary;
                        break;
                    case "AND":
                        result = firstBinary & secondBinary;
                        break;
                    case "OR":
                        result = firstBinary | secondBinary;
                        break;
                    case "XOR":
                        result = firstBinary ^ secondBinary;
                        break;
                }
                // 2진수 계산 값이 소수값으로 나올 때, 계산 불가 및 Reset 처리
                if (result < 0) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Arithmetics_Change.this);
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("Cannot Calculate Negative Data.");
                    myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            clear();
                            process.setText("");
                            operator.setText("");
                            Toast.makeText(getApplicationContext(), "Negative Number", Toast.LENGTH_SHORT).show();
                        }
                    });
                    myAlertBuilder.show();
                }
                Log.v("result", "result = " + result);
                toBinary(result);                                 //10진수를 2진수로 바꾸는 메소드
                Log.v("resultNum", "resultNum = " + resultNum);
                Log.v("firstResultNum", "firstResultNum = " + firstResultNum);
                // 계산 결과 값 ImageView 표현(2진수)
                String[] binaryArray = resultNum.split("");

                Log.v("binaryArray", "binaryArray : " + Arrays.toString(binaryArray));
                // 배열의 Empty Data 지우는 함수 호출
                String[] resultArray = deleteEmpty(binaryArray);
                Log.v("resultArray", "resultArray : " + Arrays.toString(resultArray));

                String[] tebinaryArray = teresultNum.split("");

                Log.v("tebinaryArray", "tebinaryArray : " + Arrays.toString(tebinaryArray));
                // 배열의 Empty Data 지우는 함수 호출
                String[] teresultArray = deleteEmpty(tebinaryArray);
                Log.v("teresultArray", "teresultArray : " + Arrays.toString(teresultArray));

                for (count = 0; count < resultArray.length; count++) {
                    if (teresultArray[count].equals("0")) {
                        binaryView[9-count].setImageResource(R.drawable.zero);
                    } else {
                        binaryView[9-count].setImageResource(R.drawable.one);
                    }
                }
                String resultProcess = process.getText().toString();
                String resultOperator = resultProcess + extraNum;
                operator.setText(resultOperator);
                process.setText("");
                break;
        }
    }

    public void clear() {                                        // Image 초기화
        for (int i = 0; i <= 9; i++) {
            binaryView[i].setImageResource(0);
        }
        count = 0;
    }

    public void toBinary(int n) {                                // 10진수를 2진수로 변환
        String num = "";
        int c = 0;
        do {
            num += String.valueOf(n % 2);
            n = n / 2;
            c++;
        } while (n > 0);
        for (int i = 0; i < num.length(); i++) {
            teresultNum += num.charAt(i);
            tefirstResultNum += num.charAt(i);
        }
        for (int i = num.length() - 1; i >= 0; i--) {                 // 값을 거꾸로 저장
            resultNum += num.charAt(i);
            firstResultNum += num.charAt(i);
        }
    }


    public void select(View view) {                                     // 버튼 style 유지 셀렉터
        if (selectView != null) {                                       //저장된 View 있을 시
            if (selectView.getId() != view.getId()) {                   //저장된 View 받은 View 비교
                selectView.setSelected(false);                          //다를시 이전 View false 변환
            }
        }
        view.setSelected(true);                                         //받은 View true 변환
        selectView = view;                                              //다음 View 받은 View 비교하기 위해 다시 저장
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    public void onConfigurationChanged(Configuration newConfig) {       // 화면 회전시 표시
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("방향: ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("방향: ORIENTATION_PORTRAIT");
        }
    }

    public static String[] deleteEmpty(final String[] array) {              // String[]의 Empty Data 삭제
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.removeAll(Collections.singleton(""));                          // list 내부 Data "" 모두 제거
        return list.toArray(new String[list.size()]);
    }
}