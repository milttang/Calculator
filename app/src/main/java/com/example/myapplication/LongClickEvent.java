package com.example.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LongClickEvent implements View.OnLongClickListener {
    private MainActivity mainActivity;


    public LongClickEvent(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onLongClick(View view) {
        Button button = (Button)view;
        switch (view.getId()) {
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
                mainActivity.setHandler(button);
                mainActivity.getHandler_up().post(mainActivity.getRunnable_up());
                break;
            case R.id.backBtn:
                mainActivity.setHandler(button);
                mainActivity.getHandler_down().post(mainActivity.getRunnable_down());
                break;
        }
        Toast.makeText(mainActivity,"LongClick", Toast.LENGTH_LONG).show();
        return false;
    }
}