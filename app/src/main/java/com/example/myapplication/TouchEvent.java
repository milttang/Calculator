package com.example.calculator;

import android.view.MotionEvent;
import android.view.View;

public class TouchEvent implements View.OnTouchListener {
    private MainActivity mainActivity;

    public TouchEvent(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
        }
        if(motionEvent.getAction() == MotionEvent.ACTION_UP){                               //handler_down이 runnable_down을 removeCallbacks 함
            switch (view.getId()) {
                case R.id.backBtn:
                    mainActivity.getHandler_down().removeCallbacks(mainActivity.getRunnable_down());
                    break;
                case R.id.numBtn0:
                case R.id.numBtn1:
                case R.id.numBtn2:
                case R.id.numBtn3:
                case R.id.numBtn4:
                case R.id.numBtn5:
                case R.id.numBtn6:
                case R.id.numBtn7:
                case R.id.numBtn8:
                case R.id.numBtn9:
                    mainActivity.getHandler_up().removeCallbacks(mainActivity.getRunnable_up());
                    break;
            }
        }
        return false;
    }
}