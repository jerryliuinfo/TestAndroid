package com.apache.android;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.apache.android.aidl.BookManagerActivity;
import com.apache.android.download.emoji.EmojiBean;
import com.apache.android.download.emoji.EmojiDownloaer;
import com.apache.android.multithread.MyWorkTask;
import com.apache.android.multithread.TaskException;
import com.apache.android.util.Logger;
import com.apache.android.util.NLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NLog.setDebug(true, Logger.DEBUG);
        setContentView(R.layout.activity_main);
        Button btn_md5 = (Button) findViewById(R.id.btn_md5);
        btn_md5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // LeakActivityForInnerClass.launch(MainActivity.this);
                KnowledgeActivity.launch(MainActivity.this);
            }
        });

        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmojiDownloaer downloaer = new EmojiDownloaer();
                EmojiBean bean = new EmojiBean();
                bean.setEmojiId(String.valueOf(1000));
                List<String> urls = new ArrayList<>();
                for (int i = 0; i < 10; i++){
                    urls.add("www.baidu.com "+i );
                }
                bean.setEmojiUrls(urls);
                downloaer.startDownload(bean);
            }
        });

        findViewById(R.id.btn_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookManagerActivity.launch(MainActivity.this);
            }
        });


        findViewById(R.id.btn_thread_pool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor executor = Executors.newFixedThreadPool(1);


                for (int i = 0; i < 100; i ++){
                    executor.execute(new MyRunnable(i));
                }

            }
        });

        new LoadDataTask().execute();


        findViewById(R.id.btn_custom_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomWorkTask().executor("zhangsan");


            }
        });





    }


    class CustomWorkTask extends MyWorkTask<String,Void,String>{

        @Override
        protected void onPrepare() {
            super.onPrepare();
            NLog.d(TAG, "onPrepare thread = %s", Thread.currentThread().getName());
        }

        @Override
        public String workInBackground(String... params) throws TaskException {
            String param1 = params[0];
            NLog.d(TAG, "workInBackground thread = %s, param1 = %s", Thread.currentThread().getName(), param1);

            return "haha";
        }

        @Override
        protected void onSuccess(String s) {
            super.onSuccess(s);
            NLog.d(TAG, "onSuccess thread = %s, result = %s", Thread.currentThread().getName(), s);

        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            NLog.d(TAG, "onFailure thread = %s", Thread.currentThread().getName());

        }

        @Override
        protected void onFinished() {
            super.onFinished();
            NLog.d(TAG, "onFinished thread = %s", Thread.currentThread().getName());

        }
    }

    class LoadDataTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

    private static class MyRunnable implements Runnable{

        private int index;

        public MyRunnable(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            SystemClock.sleep(50);
            NLog.d(TAG, "thread %s run", String.valueOf(index));
        }
    }






}
