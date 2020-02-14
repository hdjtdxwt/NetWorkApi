package com.epsit.network;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class HttpTask implements Runnable, Delayed {

    public IHttpRequest iHttpRequest; //请求的执行顶层接口引用

    //总共失败的次数
    protected int failedNum;

    //两次请求的时间间隔
    protected long delayTime;

    public int getFailedNum() {
        return failedNum;
    }

    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void run() {
        try{
            if(iHttpRequest!=null){
                iHttpRequest.execute();//执行IHttpRequest具体的引用的execute()方法
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(getDelayTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
