package com.apache.android.download;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 01370340 on 2018/4/15.
 */

public class NormalDownloader implements IDownload {
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private IDownloadListener listener;
    private Executor mExecutor = Executors.newCachedThreadPool();
    private Random random = new Random();

    @Override
    public void setListener(IDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void startDownload(final String url, final String localPath,final Object contextObject) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(random.nextInt(3000));
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //模拟一个下载成功回调
                        if (listener != null){
                            try {
                                listener.onDownloadSuccess(url, localPath,contextObject);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}
