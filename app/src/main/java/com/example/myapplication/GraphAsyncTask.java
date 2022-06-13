package com.example.myapplication;

import android.os.AsyncTask;

public class GraphAsyncTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPreExecute() {
        // 스레드가 시작하기 전에 수행할 작업(메인 스레드)
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... Params) {
        // 스레드가 수행할 작업(생성된 스레드)
        //여기서 publishProgress()를 사용하여 메인 스레드 접근가능
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... Params) {
        // 스레드가 수행되는 사이에 수행할 중간 작업(메인 스레드)
        // doInBackground()에서
        //publishProgress() 메소드를 호출하여 중간 작업 수행가능
    }

    @Override
    protected void onPostExecute(Void result) {
        // 스레드 작업이 모두 끝난 후에 수행할 작업(메인 스레드)
        super.onPostExecute(result);
    }

    @Override
    protected void onCancelled() {
        // cancel() 메소드가 호출되었을때 즉,
        // 강제로 취소하라는 명령이 호출되었을 때
        // 스레드가 취소되기 전에 수행할 작업(메인 스레드)
        super.onCancelled();
    }
}
