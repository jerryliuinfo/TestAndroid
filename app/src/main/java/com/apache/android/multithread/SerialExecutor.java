package com.apache.android.multithread;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/**
 * Created by 01370340 on 2018/4/21.
 */

public class SerialExecutor implements Executor {
    private ArrayDeque<Runnable> mTasks = new ArrayDeque<>();

    public static Executor THREAD_POOL_EXECUTOR;
    Runnable mActive;
    @Override
    public synchronized void execute(@NonNull final Runnable r) {

        mTasks.offer(new Runnable() {
            @Override
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });
        scheduleNext();
    }

    protected synchronized void scheduleNext(){
        if ((mActive = mTasks.poll()) != null){
            THREAD_POOL_EXECUTOR.execute(mActive);
        }
    }
}
