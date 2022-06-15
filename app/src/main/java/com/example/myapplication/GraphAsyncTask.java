package com.example.myapplication;

import static com.example.calculator.util.Constants.CHECK_LOG;
import static com.example.calculator.util.Constants.GRAPH_LOG_TAG;
import static com.example.calculator.util.Constants.FUNCTION_1;
import static com.example.calculator.util.Constants.FUNCTION_2;
import static com.example.calculator.util.Constants.FUNCTION_3;
import static com.example.calculator.util.Constants.RANGE;
import static com.example.calculator.util.Constants.START_NUM;
import static com.example.calculator.util.Constants.STEP;
import static com.example.calculator.util.Constants.X_String;
import static com.example.calculator.util.Constants.Y_LINE;
import static com.example.calculator.util.Constants.X_LINE;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 AsyncTask 는 비동기 작업을 수행하기위해 사용됩니다.
 AsyncTask 의 형태 == AsyncTask<Params, Progress, Result>
  1. Params: doInBackground 메서드의 파라미터 타입이 되며, execute 메서드 인자 값이 됩니다.
  2. progress: doInBackground 작업 시 진행 단위의 타입으로 onProgressUpdate 파라미터 타입입니다.
  3. Result: doInBackground 리턴값으로 onPostExecute 파라미터 타입입니다.
 */
public class GraphAsyncTask extends AsyncTask<String, Void, ArrayList<Entry>> {

    private String function;
    private Activity activity;
    private int position;
    private String xStringMinus;

    public GraphAsyncTask(String function, Activity activity, int position) {
        this.function = function;
        this.activity = activity;
        this.position = position;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Entry> doInBackground(String... strings) {
        if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, "doInBackground() " + position);

        ArrayList<Entry> entryList = new ArrayList<>();
        if(function != null && activity != null && function.length() != 0) {
            if(position == X_LINE) {
                for (float i = -50.00f; i < 50; i += 0.01f) {
                    float y = i;
                    float x = 0;
                    entryList.add(new Entry(x, y));
                }
            }else if(position == Y_LINE) {
                for (float i = -50.00f; i < 50; i += 0.01f) {
                    float y = 0;
                    float x = i;
                    entryList.add(new Entry(x, y));

                }
            }else {
                if (function != null && function.length() != 0) {
                    /* 전체 함수에서 y=을 제거한 String */
                    String functionDeleteEqualY = function.replaceFirst("y=", "");
                    Log.v("deleteEqualY", "firstFunctionDeleteEqualY : " + functionDeleteEqualY);

                    String functionOperator = functionDeleteEqualY.substring(1, 2);
                    Log.v("functionOperator", "functionOperator : " + functionOperator);
                    String firstNum = functionDeleteEqualY.substring(2);
                    Log.v("firstNum", "firstNum : " + firstNum);

                    /* 전체 함수에서 y=을 제거 후, x의 index 확인 */
                    int firstFindXTest = functionDeleteEqualY.indexOf(X_String);
                    Log.v("firstFindXTest", "firstFindXTest : " + firstFindXTest);

                    /* x앞에 붙어 있는 숫자 및 문자열 확인 */
                    String xFirstFront = functionDeleteEqualY.substring(0, firstFindXTest);
                    Log.v("xFirstFront", "xFirstFront : " + xFirstFront);

                    /* x뒤에 붙어 있는 계산 및 연산 String 확인 */
                    String xFirstBehind = functionDeleteEqualY.substring(firstFindXTest + 1).trim();
                    String xFirstBehindNumber = xFirstBehind.replaceAll("[^0-9]", "");
                    Log.v(GRAPH_LOG_TAG, "xFirstBehind : " + xFirstBehind);
                    Log.v(GRAPH_LOG_TAG, "xFirstBehindNumber : " + xFirstBehindNumber);

                    /* 연산 String 을 Parsing 하여 계산 */
                    String[] xFirstBehindArray = xFirstBehind.split("");

                    // x 뒤에 오는 계산식 String 값을 배열로 만듭니다.
                    String[] strArray = functionStrSplit(xFirstBehind);
                    // 만든 배열을 계산합니다.
                    int behindCalculateResult = calculationStrArray(strArray);

                    String xFirstBehindCalculate = "";

                    for(int i = 0; i < xFirstBehindArray.length; i++) {
                        Log.i(GRAPH_LOG_TAG, "xFirstBehindArray["+ i + "] = " + xFirstBehindArray[i]);
                    }

                    Log.i(GRAPH_LOG_TAG, "xFirstBehindArray.length = " + xFirstBehindArray.length);

//                    if (xFirstBehindArray.length >= 4) {
//                        Log.i(GRAPH_LOG_TAG, "if(xFirstBehindArray.length >= 4)");
//                        Log.i(GRAPH_LOG_TAG, "if(xFirstBehindArray.length >= 4) ---> xFirstBehindArray[3] = " + xFirstBehindArray[3]);
//                        for(int i = 2; i < xFirstBehindArray.length; i++) {
//                            // 하나씩 분리된 숫자를 하나로 합치기 위한 변수 입니다.
//                            int firstAddNumber = 0;
//                            int lastAddNumber = 0;
//                            switch (xFirstBehindArray[3]) {
//                                case "+":
//                                    firstAddNumber += lastAddNumber;
//                                    // 계산후에는 다시 변수를 0으로 만들어줍니다.
//                                    lastAddNumber = 0;
//                                    break;
//                                case "-":
//                                    firstAddNumber -= lastAddNumber;
//                                    lastAddNumber = 0;
//                                    break;
//                                case "*":
//                                    firstAddNumber *= lastAddNumber;
//                                    lastAddNumber = 0;
//                                    break;
//                                case "/":
//                                    firstAddNumber /= lastAddNumber;
//                                    lastAddNumber = 0;
//                                    break;
//                                default:
//                                    // 아스키 코드 문자형식 0~9 는 48~57 이므로 i 번째가 숫자일 때의 조건문을 만듭니다.
//                                    if(xFirstBehindArray[i].charAt(0) > 47 &&  xFirstBehindArray[i].charAt(0) < 58) {
//                                        lastAddNumber += Integer.parseInt(xFirstBehindArray[i]);
//                                    }
//                            }
//                        }
//                    } else if (xFirstBehindArray.length >= 3) {
//                        behindCalculateResult = Integer.parseInt(xFirstBehindArray[2]);
//                    }
                    String behindCalculate = Integer.toString(behindCalculateResult);

                    /* x앞에 오는 값이 숫자인지 문자인지 확인 */
                    boolean xFront = GraphActivity.numberCheck(xFirstFront);

                    String operatorCheck = functionDeleteEqualY.substring(firstFindXTest + 1, firstFindXTest + 2);
                    Log.v("operatorCheck", "operatorCheck : " + operatorCheck);
                    int xIntFront = 0;
//                    if (xFirstFront != "") {
//
//                    }
                    if (xFront) { /* y = 정수 , x = 정수 , x항과 숫자항이 존재할 때, x항이 뒤로가는 경우는 아직 생각 안함 */
                        xIntFront = Integer.parseInt(xFirstFront);
                        if (operatorCheck.equals("+")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i + Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("-")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i - Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("*")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i * Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        } else if (operatorCheck.equals("/")) {
                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                float x = i;
                                entryList.add(new Entry(x, y));
                            }
                        }
                    } else {
                        if(xFirstFront != null && xFirstFront.length() != 0) {
                            if (xFirstFront.charAt(0) == '-') {
                                xStringMinus = xFirstFront.substring(1);
                                if (GraphActivity.numberCheck(xStringMinus)) {
                                    if (operatorCheck.equals("+")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = i + Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("-")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = i - Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("*")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = i * Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if (operatorCheck.equals("/")) {
                                        for (float i = START_NUM; i < RANGE; i += STEP) {
                                            float y = xIntFront * i / Integer.parseInt(behindCalculate);
                                            float x = i;
                                            entryList.add(new Entry(x, y));
                                        }
                                    } else if ("1" == "2") {
                                        // 부호다음에 cos sin 같은 문자 오는거 생각
                                    }
                                } else {
                                    if (xFirstFront.charAt(1) == 's') {
                                        if (operatorCheck.equals("+")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) sin(i) + Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (operatorCheck.equals("-")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) sin(i) - Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (behindCalculate.equals("*")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) sin(i) * Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (behindCalculate.equals("/")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) sin(i) / Integer.parseInt(xFirstBehindNumber);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        }
                                    } else if (xFirstFront.charAt(1) == 'c') {
                                        if (operatorCheck.equals("+")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) cos(i) + Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (operatorCheck.equals("-")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) cos(i) - Integer.parseInt(xFirstBehindNumber);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (operatorCheck.equals("*")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) cos(i) * Integer.parseInt(xFirstBehindNumber);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (operatorCheck.equals("/")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) cos(i) / Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        }
                                    } else if (xFirstFront.charAt(1) == 't') {
                                        if (operatorCheck.equals("+")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) tan(i) + Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (operatorCheck.equals("-")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) tan(i) - Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (operatorCheck.equals("*")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) tan(i) * Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        } else if (operatorCheck.equals("/")) {
                                            for (float i = START_NUM; i < RANGE; i += STEP) {
                                                float y = (float) tan(i) / Integer.parseInt(behindCalculate);
                                                float x = i;
                                                entryList.add(new Entry(x, y));
                                            }
                                        }
                                    }
                                }
                            }
//                Log.v("xStringMinus", "xStringMinus : " + xStringMinus);

                        } else if (xFirstFront.equals("")) {    //루트랑 스퀘어 추가 생각 필요
                            Log.i(GRAPH_LOG_TAG, "behindCalculate = " + behindCalculate);
                            if (operatorCheck.equals("+")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i + Integer.parseInt(behindCalculate);
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("-")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i - Integer.parseInt(behindCalculate);
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("*")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i * Integer.parseInt(behindCalculate);
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            } else if (operatorCheck.equals("/")) {
                                for (float i = START_NUM; i < RANGE; i += STEP) {
                                    float y = i / Integer.parseInt(behindCalculate);
                                    float x = i;
                                    entryList.add(new Entry(x, y));
                                }
                            }
                        }
                    }
                }
            }

        }
        return entryList;
    }

    @Override
    protected void onPostExecute(ArrayList<Entry> result) {
        if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, "onPostExecute() " + position);

        if(result != null) {
            LineDataSet lineDataSet = new LineDataSet(result, function);

            if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, position + " lineDataSet = " + lineDataSet);
            int setColor = -1;
            switch(position) {
                case X_LINE:
                case Y_LINE:
                    setColor = Color.LTGRAY;
                    break;
                case FUNCTION_1:
                    setColor = Color.DKGRAY;
                    break;
                case FUNCTION_2:
                    setColor = Color.GREEN;
                    break;
                case FUNCTION_3:
                    setColor = Color.BLUE;
                    break;
            }
            if(setColor != -1) {
                lineDataSet.setColor(setColor);
                lineDataSet.setCircleColor(setColor);
            }
            if(CHECK_LOG) Log.i(GRAPH_LOG_TAG, position + " setColor = " + setColor);
            GraphActivity graphActivity = null;
            if(activity != null) graphActivity = (GraphActivity) activity;
            List<ILineDataSet> dataSets = graphActivity.getDataSets();
            dataSets.add(lineDataSet);
            if(position == Y_LINE) graphActivity.setXYLineDone(true);
            if(position == FUNCTION_3) graphActivity.setDrawPlay(false);
        }
    }

    // (sbc) 문자열을 부호를 기준으로 잘라서 배열로 만드는 메서드
    private String[] functionStrSplit(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] strArray = null;
        if (str != null && str.length() != 0) {

            // 숫자를 하나로 합쳐서 저장할 변수
            StringBuffer addNumber = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                // 공백이 있는경우 스킵
                if(str.charAt(i) == ' ') continue;
                // 아스키 코드 문자형식 0~9 는 48~57 이므로 i 번째가 숫자일 때의 조건문을 만듭니다.
                if (str.charAt(i) > 47 && str.charAt(i) < 58) {
                    addNumber.append(str.charAt(i));
                }else {
                    // 숫자가 아니면 합친 숫자문자열을 배열에 저장합니다.
                    arrayList.add(addNumber.toString());
                    // StringBuffer 를 초기화 합니다.
                    addNumber.setLength(0);

                    // 아스키코드 '+' == 43
                    // 아스키코드 '-' == 45
                    // 아스키코드 '*' == 42
                    // 아스키코드 '/' == 47
                    // 아스키코드 소문자 'x' == 120
                    if (str.charAt(i) == 43 || str.charAt(i) == 45 || str.charAt(i) == 120 || str.charAt(i) == 47) {
                        String signStr = String.valueOf(str.charAt(i));
                        arrayList.add(signStr);
                    }
                }
                 if(i == str.length()-1) {
                    arrayList.add(addNumber.toString());
                }
            }

            // list 에서 공백을 모두 제거합니다
            /* ArrayList 의 removeAll 메서드는 파라미터값으로 넣은 컬렉션에 포함된 모든 요소를 삭제하는 메서드입니다.
                거기서 Arrays 의 간단하게 컬렉션을 만들어주는 asList 메서드를 사용해서 파라미터에 지우고 싶은 값들을 넣습니다.
             */
            arrayList.removeAll(Arrays.asList("", null, " "));

            strArray = new String[arrayList.size()];
            for(int i = 0; i < arrayList.size(); i++) {
                strArray[i] = arrayList.get(i);
            }
            arrayList = null;
        }
        return strArray;
    }

    // (sbc) 계산해주는 메서드
    private int calculationStrArray(String[] strArray) {
        int result = -1;

        for(int i = 0; i < strArray.length; i++) {

            // 첫번째 부호가 - 면 음수로 만듭니다.
            if(i == 0) {
                if(strArray[0].charAt(0) == 45) {
                    strArray[1] = strArray[0] + strArray[1];
                    continue;
                }else if(strArray[0].charAt(0) == 43 || strArray[0].charAt(0) == 120 || strArray[0].charAt(0) == 47){
                    continue;
                }
            }


            // result 값이 초기값일 때 i 번째가 숫자라면 result 에 값을 저장합니다.
            if(result == -1) {
                if(strArray[i].charAt(0) > 47 && strArray[i].charAt(0) < 58){
                    result = Integer.parseInt(strArray[i]);
                    continue;
                }
            }

            // 아스키코드 '+' == 43
            // 아스키코드 '-' == 45
            // 아스키코드 '*' == 42
            // 아스키코드 '/' == 47
            // 아스키코드 소문자 'x' == 120
            if (strArray[i].charAt(0) == 43) {
                result += Integer.parseInt(strArray[i+1]);
                i++;
            }else if(strArray[i].charAt(0) == 45) {
                result -= Integer.parseInt(strArray[i+1]);
                i++;
            }else if(strArray[i].charAt(0) == 120) {
                result *= Integer.parseInt(strArray[i+1]);
                i++;
            }else if(strArray[i].charAt(0) == 47) {
                result /= Integer.parseInt(strArray[i+1]);
                i++;
            }
        }
        return result;
    }
}
