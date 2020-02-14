package com.epsit.network;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static ThreadManager threadManager = new ThreadManager();

    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;

    private DelayQueue<Delayed> failedQueue = new DelayQueue<>();

    private ThreadManager() {
        threadPoolExecutor = new ThreadPoolExecutor(5, 10, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });
        threadPoolExecutor.execute(runnable);//开始一直执行，从等待队列取runnable然后执行
        threadPoolExecutor.execute(failedRunnable);
    }

    public static ThreadManager getInstance() {
        return threadManager;
    }

    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        mQueue.add(runnable);
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = mQueue.take();
                    threadPoolExecutor.execute(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public Runnable failedRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    HttpTask httpTask = (HttpTask) failedQueue.take();
                    if (httpTask.getFailedNum() < 3) {
                        httpTask.setFailedNum(httpTask.getFailedNum()+1);
                        threadPoolExecutor.execute(httpTask);
                        Log.e("failedRunnable", "我是次数：" + httpTask.getFailedNum());

                    } else { //超过3次，可以通知应用层说失败了
                        if( httpTask.iHttpRequest!=null && httpTask.iHttpRequest.getCallBackListener()!=null){
                            httpTask.iHttpRequest.getCallBackListener().onFailed();
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}
