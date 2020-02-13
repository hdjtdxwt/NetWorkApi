package com.epsit.network;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static ThreadManager threadManager = new ThreadManager();

    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;
    private ThreadManager(){
        threadPoolExecutor = new ThreadPoolExecutor(5, 10, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });
        threadPoolExecutor.execute(runnable);//开始一直执行，从等待队列取runnable然后执行
    }

    public static ThreadManager getInstance() {
        return threadManager;
    }

    public void addTask(Runnable runnable){
        if(runnable == null){
            return ;
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
}
