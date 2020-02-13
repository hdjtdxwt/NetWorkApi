package com.epsit.network;

import java.io.InputStream;

/**
 * 回调接口，线程池执行请求后，返回给网络请求框架的
 */
public interface CallBackListener {
    //请求成功
    void onSuccess(InputStream inputStream);

    //请求失败
    void onFailed();
}
