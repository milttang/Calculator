package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class CalculateHelper {
    public static double num1;
    public static double num2;
    public static double resultNumber;

    //사용자의 input을 각각 구분하여 ArrayList에 저장하는 메소드
    private ArrayList splitTokens(String equation) {
        String[] inputData = equation.split(" "); //공백을 기준

        ArrayList inputList = new ArrayList();
        double number = 0;

        boolean flag = false;
        for (String data : inputData) {
            if (data.equals(" ")) {
                continue;
            }
            if (checkNumber(data)) {        //정수일 때
                number = number * 10 + Double.parseDouble(data);
                flag = true;
            } else {
                if (flag) {
                    inputList.add(number);
                    number = 0;
                }
                flag = false;
                inputList.add(data);
            }
        }

        if (flag) {
            inputList.add(number);
        }

        return inputList;
    }

    //후위 표기식으로 변형
    private ArrayList infixToPostfix(ArrayList inputData) {
        ArrayList result = new ArrayList();
        HashMap level = new HashMap();
        Stack stack = new Stack();

        //각 기호의 우선순위 레벨. 곱하기, 나누기 > 더하기, 빼기 > 기타
        level.put("*", 3);
        level.put("/", 3);
        level.put("+", 2);
        level.put("-", 2);
        level.put("(", 1);

        for (Object object : inputData) {
            if (object.equals("(")) {           //object가 괄호 일때
                stack.push(object);
            } else if (object.equals(")")) {
                while (!stack.peek().equals("(")) { //앞에 "("있는지 확인
                    Object val = stack.pop();
                    if (!val.equals("(")) {
                        result.add(val);
                    }
                }
                stack.pop();
            } else if (level.containsKey(object)) {     //object가 부호이면 put한것과 같은게 있는지 확인
                if (stack.isEmpty()) {                  //부호 우선순위 비교
                    stack.push(object);
                } else {
                    if (Double.parseDouble(level.get(stack.peek()).toString()) >= Double.parseDouble(level.get(object).toString())) {
                        result.add(stack.pop());
                        stack.push(object);
                    } else {
                        stack.push(object);
                    }
                }
            } else {                                    //object가 숫자면 리스트에 추가
                result.add(object);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    //후위 표기식을 계산
    private Double postFixEval(ArrayList expr) {
        Stack numberStack = new Stack();
        for (Object o : expr) {
            if (o instanceof Double) {
                numberStack.push(o);
            } else if (o.equals("+")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 + num1);
            } else if (o.equals("-")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 - num1);
            } else if (o.equals("*")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 * num1);
            } else if (o.equals("/")) {
                num1 = (Double) numberStack.pop();
                num2 = (Double) numberStack.pop();
                numberStack.push(num2 / num1);
            }
        }

        resultNumber = (Double) numberStack.pop();

        return resultNumber;
    }

    public Double process(String equation) {
        ArrayList postfix = infixToPostfix(splitTokens(equation));      // 후위표기식 변경 적용
        Double result = postFixEval(postfix);               //후위표기식 계산 적용
        return result;
    }

    public boolean checkNumber(String str) {        //부호인지 정수인지 실수인지 확인
        char check;

        if (str.equals(""))         //비어있는지 체크
            return false;

        for (int i = 0; i < str.length(); i++) {
            check = str.charAt(i);
            if (check < 48 || check > 58) {         //숫자 인지
                if (check != '.')                   //실수 인지
                    return false;
            }
        }
        return true;
    }

    public boolean checkError(String str) {        //부호인지 정수인지 실수인지 확인
        char check;

        if (str.equals(""))         //비어있는지 체크
            return false;

        for (int i = 0; i < str.length(); i++) {
            check = str.charAt(i);
            if (check < 48 || check > 58) {

            }
            if(!(check == '+' ||check == '-' ||check == '*' ||check == '/' ||check == '(' ||check == ')')){
                return false;
            }
        }
        return true;
    }



    public String sorted(String reNumber) {
        String[] arrresult = reNumber.split(" ");
        ArrayList<String> resultList = new ArrayList<>();
        ArrayList<String> resultList2 = new ArrayList<>();  //괄호 포함
        ArrayList<String> resultList3 = new ArrayList<>();  //* , /
        ArrayList<String> resultList4 = new ArrayList<>();  // + , -
        Collections.addAll(resultList, arrresult);
        String str = "";
        String str1 = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i).equals("(")) {
                if (resultList2.size() > 0) {
                    resultList2.add(resultList.get(i - 1));       //괄호가 2개 이상일 경우에 사이에 들어갈 부호도 추가      + 를 예시로>>> ex) ( 2 x 3 ) + ( 3 / 4 )
                }
                resultList2.add("(");
                while (!resultList.get(i).equals(")")) {        // 닫힘 괄호 나오기 전까지 모두담는다.
                    i++;
                    resultList2.add(resultList.get(i));
                }
                i++;
                if (i == resultList.size()) {                     //i가 최대치일 경우 break;
                    break;
                }
            }
            if (resultList.get(i).equals("*") || resultList.get(i).equals("/")) {               // * , / 만 분리
                if(i>1){
                    if(resultList.get(i-2).equals("+") || resultList.get(i-2).equals("-")){
                        resultList3.add(resultList.get(i));
                    }
                }
                resultList3.add(resultList.get(i - 1));
                boolean vo = true;
                while (vo) {
                    if (resultList.get(i).equals("+") || resultList.get(i).equals("-") || resultList.get(i).equals("(")) { //덧셈 뺄셈 괄호열기 부호 나올때까지 다 남아라
                        vo = false;
                        if (resultList.get(i).equals("(")) {
                            i = i-2;
                        }
                        i++;
                    } else {
                        resultList3.add(resultList.get(i));
                        i++;
                    }
                    if (i == resultList.size()) {             //i가 최대치일 경우 break;
                        break;
                    }
                }
                if (i == resultList.size()) {                //i가 최대치일 경우 break;
                    break;
                }
            }
            if (i > 0) {
                if(resultList4.size() == 0){
                    if (checkNumber(resultList.get(i - 1))) {
                        resultList4.add(resultList.get(i - 1));
                    }else if(checkNumber(resultList.get(i))){
                        resultList4.add(resultList.get(i));
                    }
                }
                if (resultList.get(i).equals("+") || resultList.get(i).equals("-")) {
                    int count;
                    if(resultList4.get(0).equals("+") || resultList4.get(0).equals("-")){
                        count = -1;
                    }else{
                        count = 0;
                    }
                    boolean bo = true;
                    while (bo) {
                        if (resultList.get(i).equals("*") || resultList.get(i).equals("/") || resultList.get(i).equals("(")) {  //곱셈 나눗셈 괄호열기 부호 나올때까지 다 남아라
                            bo = false;
                            if (resultList.get(i).equals("(")) {
                                i--;
                                resultList4.remove(count);
                            } else {
                                resultList4.remove(count);
                                resultList4.remove(count - 1);
                                i--;
                            }
                        } else {
                            resultList4.add(resultList.get(i));
                            i++;
                            count++;
                        }
                        if (i == resultList.size()) {                 //i가 최대치일 경우 break;
                            break;
                        }
                    }
                }
            }
        }
        if (resultList2.size() != 0) {
            ArrayList<String> resultList5 = new ArrayList<>();              //괄호 곱셈 나눗셈
            ArrayList<String> resultList6 = new ArrayList<>();              //괄호 덧셈 뺄셈
            if(resultList2.contains("*") || resultList2.contains("/")){                     //괄호 안에서 우선순위 적용해서 곱셈나눗셈, 뺄셈 덧셈 따로 나누기
                for(int i = 0; i<resultList2.size(); i++){
                    if(resultList2.get(i).equals("*") || resultList2.get(i).equals("/")){
                        resultList5.add(resultList2.get(i));
                        resultList5.add(resultList2.get(i+1));
                    }
                    if(resultList2.get(i).equals("+") || resultList2.get(i).equals("-")){
                        if(resultList2.get(i+2).equals("*") || resultList2.get(i+2).equals("/")){
                            resultList5.add(resultList2.get(i+2));
                            resultList5.add(resultList2.get(i+1));
                        }else{
                            resultList6.add(resultList2.get(i));
                            resultList6.add(resultList2.get(i+1));
                        }
                    }
                    if(i==1){
                        if(resultList2.get(2).equals("*") || resultList2.get(2).equals("/")){
                            resultList5.add(resultList2.get(1));
                        }else{
                            resultList6.add(resultList2.get(1));
                        }
                    }
                }
                str3 = merge(resultList5);                              //괄호안에 곱셈, 나눗셈 먼저 적용
                str4 = merge(resultList6);                              //괄호안에 덧셈, 뺄셈 적용
                str3 = "( "+ str3.substring(3);
                str4 = str4 + " )";
                str = str3 + str4;
            }else{
                str = merge(resultList2);
                str = "( "+ str.substring(3) + " )";                //괄호안에 곱셈 나눗셈이 없을 경우 숫자 우선순위만 적용한다.
            }
        }
        if (resultList3.size() != 0) {
            str1 = merge(resultList3);
            if(resultList2.size() != 0){
                str1 = str1.substring(3);
                str1 = " + "+ str1;
            }
        }
        if(resultList4.size() != 0){
            str2 = merge(resultList4);
        }
        String result = "";
        result = str + str1 + str2;
        if(!checkNumber(String.valueOf(result.charAt(0)))){
            if(result.charAt(0) != '('){
                result = result.substring(3);
            }
        }
        Log.d(result,"결과");
        return result;
    }

    public String merge(ArrayList b){
        String[] s = new String[10];
        Integer[] w = new Integer[10];
        ArrayList<String> z = new ArrayList<>();
        Integer[] x = new Integer[10];
        int count =0;
        for(int i =0; i<b.size(); i++){
            if(!(b.get(i).equals("(") || b.get(i).equals(")"))){
                if(!(b.get(i).equals("+") || b.get(i).equals("*") || b.get(i).equals("/"))){
                    if(b.get(i).equals("-")){
                        x[count] = count;
                        s[count] = "-" + b.get(i+1);
                        count++;
                        i++;
                    }else{
                        x[count] = count;
                        s[count] = String.valueOf(b.get(i));
                        count++;
                    }
                }
            }
        }
        int count1 = 0;
        if(checkNumber(String.valueOf(b.get(count1)))){
            if(b.size()>1){
            z.add(" " + b.get(count1+1) + " "+ b.get(count1));
            count1++;
            }else{
                z.add(" + "+ b.get(count1));
                count1++;
            }
        }
        while(count1 != b.size()){
            if (b.get(count1).equals("(")) {
                z.add(" + " + b.get(count1 + 1));
                count1++;
            }else if (checkNumber(String.valueOf(b.get(count1)))) {
                if(b.get(count1 - 1).equals("(")){
                    z.add(" + " + b.get(count1));
                }else{
                    z.add(" " + b.get(count1 - 1) + " "+b.get(count1));
                }
            }
            count1++;
        }
        for(int i =0; i<count; i++){
            w[i] = Integer.parseInt(s[i]);
        }
        for(int i =0; i<count; i++){
            for(int k =0; k<count; k++){
                if(w[i]>w[k]) {
                    int a = w[i];
                    w[i] = w[k];
                    w[k] = a;
                    int bx = x[i];
                    x[i] = x[k];
                    x[k] = bx;
                }
            }
        }
        String str = "";
        for(int i =0; i<count; i++){
            str += z.get(x[i]);
        }
        return str;
    }
}