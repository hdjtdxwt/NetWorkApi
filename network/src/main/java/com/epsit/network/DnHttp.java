package com.epsit.network;

import com.epsit.network.common_type.HttpStringTask;
import com.epsit.network.common_type.IStringListener;
import com.epsit.network.common_type.StringCallBackListener;
import com.epsit.network.json.HttpJsonTask;
import com.epsit.network.json.IJsonListener;
import com.epsit.network.json.JsonCallBackListener;
import com.epsit.network.json.JsonHttpRequest;

/**
 * 网络请求框架层，这是一个给应用层调用的类
 */
public class DnHttp {
    //网络请求主要包括：请求路径
    //请求的参数
    //回调接口
    //接收结果的类型

    public static<T,M> void sendRequest(String url, T requestData, Class<M> response, IJsonListener<M> jsonListener){
        IHttpRequest iHttpRequest = new JsonHttpRequest();

        CallBackListener callBackListener = new JsonCallBackListener(response, jsonListener);

        HttpJsonTask httpTask = new HttpJsonTask(url, iHttpRequest, callBackListener,  requestData);

        ThreadManager.getInstance().addTask(httpTask);
    }

    //后面自己加上的
    public static<T> void sendStringRequest(String url, T requestData, IStringListener stringListener){
        IHttpRequest iHttpRequest = new JsonHttpRequest();

        CallBackListener callBackListener = new StringCallBackListener(stringListener);

        HttpStringTask<T> httpTask = new HttpStringTask(url, iHttpRequest, callBackListener,  requestData);

        ThreadManager.getInstance().addTask(httpTask);
    }

}
