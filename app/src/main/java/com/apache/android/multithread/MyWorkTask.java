package com.apache.android.multithread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.apache.android.util.NLog;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 01370340 on 2018/4/21.
 */

public abstract class MyWorkTask<Params,Progress,Result> {

    public static final String TAG = MyWorkTask.class.getSimpleName();
    public static final int CORE_POOL_SIZE = 5;

    public static final int MAX_POOL_SIZE = 10;

    private static final int KEEP_ALIVE_SECONDS = 30;

    /**
     * 加载图片默认是10个线程
     */
    private static final int CORE_IMAGE_POOL_SIZE = 10;

    public static ThreadFactory sThreadFactory = new ThreadFactory() {
        private AtomicInteger count = new AtomicInteger(1);
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "MyWorkTask # "+count.getAndIncrement());
        }
    };

    /**
     * 固定大小为{@link #CORE_IMAGE_POOL_SIZE}的线程池<br/>
     * 无界线程池，可以加载无限个线程
     */
    public static final Executor IMAGE_POOL_EXECUTOR = Executors.newFixedThreadPool(CORE_IMAGE_POOL_SIZE, sThreadFactory);

    private static BlockingQueue<Runnable> sPoolQueue = new LinkedBlockingDeque<>(128);

    static {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS,sPoolQueue,sThreadFactory);
        executor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = executor;
    }

    public static final Executor sSerialExecutor = new SerialExecutor();

    private static Executor sDefaultExecutor = sSerialExecutor;

    private static Executor THREAD_POOL_EXECUTOR ;


    public void setDefaultExecutor(Executor executor){
        sDefaultExecutor = executor;
    }


    public static class SerialExecutor implements Executor {
        private ArrayDeque<Runnable> mTasks = new ArrayDeque<>();

        public SerialExecutor() {
        }

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
            if (mActive == null){
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext(){
            if ((mActive = mTasks.poll()) != null){
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    public MyWorkTask<Params,Progress,Result> executor(Params... params){
        return executorOnExecutor(sDefaultExecutor,params);
    }

    private final WorkRunnable<Params,Result> mWork;
    private final FutureTask<Result> mFuture;

    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();

    public MyWorkTask(){
        mWork = new WorkRunnable<Params, Result>() {
            @Override
            public Result call() throws Exception {
                mTaskInvoked.set(true);
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                return postResult(doInBackground(mParams));
            }
        };

        mFuture = new FutureTask<Result>(mWork){
            @Override
            protected void done() {
                try {
                    Result result = get();
                    postResultIfNotInvoked(result);
                } catch (ExecutionException e) {
                    throw new RuntimeException("An error occured while executing doInBackground()", e.getCause());
                } catch (CancellationException e) {
                    postResultIfNotInvoked(null);
                }catch (Throwable t){
                    throw new RuntimeException("An error occured while executing " + "doInBackground()", t);
                }
            }
        };
    }




    private Result postResult(Result result){
        Message msg = getHandler().obtainMessage(MESSAGE_POST_RESULT, new ASyncTaskResult<Result>(this,result));
        msg.sendToTarget();
        return result;
    }

    private void postResultIfNotInvoked(Result result){
        final  boolean taskInvoked = mTaskInvoked.get();
        if (!taskInvoked){
            postResult(result);
        }
    }


    public MyWorkTask<Params,Progress,Result> executorOnSerialExecutor(Executor executor, Params... params){
        return executorOnExecutor(sSerialExecutor,params);
    }

    public MyWorkTask<Params,Progress,Result> executorOnExecutor(Executor executor, Params... params){

        if (mState != State.PENDING){
            switch (mState){
                case RUNNING:
                    throw new IllegalStateException("Can't execute mTask : the mTask is already running");
                case FINISHED:
                    throw new IllegalStateException("Can't execute mTask : the mTask has already been executed: a mTask can be executed only once");

            }
        }

        mState = State.RUNNING;

        if (Looper.myLooper() == Looper.getMainLooper()){
            onPreExecute();
        }else {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    onPreExecute();
                }
            });
        }

        mWork.mParams = params;

        executor.execute(mFuture);
        return this;
    }


    final protected void onPreExecute(){
        onPrepare();
    }

    final private void onTaskCanceled(){
        onCanceled();
        onFinished();
    }


    /**
     * 线程开始执行
     */
    protected void onPrepare() {

    }

    protected void onFinished(){

    }

    protected void onCanceled(){

    }

    protected final void onPostExecute(Result result){
        NLog.d(TAG, String.format("--->onPostExecute() run"));
        if (exception == null){
            onSuccess(result);
        }else{
            NLog.d(TAG, String.format("%s --->onPostExecute() execption"));
            onFailure(exception);
        }

        onFinished();

    }

    /**
     * {@link #workInBackground(Object...)} 发生异常
     */
    protected void onFailure(TaskException exception) {

    }

    /**
     * 没有抛出异常，且<tt>Result</tt>不为<tt>Null</tt>
     */
    protected void onSuccess(Result result) {

    }

    TaskException exception;

    public  Result doInBackground(Params... params){
        Result result = null;
        try {
            result = workInBackground(params);
        } catch (TaskException e) {
            e.printStackTrace();
            exception = e;
        }
        return result;
    }


    public abstract Result workInBackground(Params... params) throws TaskException;

    protected void onProgressUpdated(Progress... values){

    }

    private  State mState = State.PENDING;

    public enum State{
        PENDING,
        RUNNING,
        FINISHED
    }

    private static abstract class WorkRunnable<Params,Result> implements Callable<Result>{
        Params[] mParams;
    }

    private static class ASyncTaskResult<Date>{
        public MyWorkTask mTask;
        public Date[] mData;

        public ASyncTaskResult(MyWorkTask task, Date... data) {
            this.mTask = task;
            this.mData = data;
        }
    }

    public static final int MESSAGE_POST_RESULT = 0X01;

    public static final int MESSAGE_POST_PROGRESS = 0X02;


    private static Handler mHandler = new InternalHandler();

    private static class InternalHandler extends Handler{
        public InternalHandler() {
            //关联主线程
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ASyncTaskResult result = (ASyncTaskResult) msg.obj;
            switch (msg.what){
                case MESSAGE_POST_RESULT:

                    result.mTask.finish(result.mData[0]);
                    break;

                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdated();
                    break;
            }
        }
    }


    public Handler getHandler() {
        return mHandler;
    }



    private final void finish(Result result){
        if (isCanceled()){
            onTaskCanceled();
        }else {
            onPostExecute(result);
        }
    }


    protected boolean isCanceled(){
        return mFuture.isCancelled();
    }
}
