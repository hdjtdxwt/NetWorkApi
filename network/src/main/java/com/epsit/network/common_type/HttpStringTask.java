package com.epsit.network.common_type;

import com.alibaba.fastjson.JSON;
import com.epsit.network.CallBackListener;
import com.epsit.network.HttpTask;
import com.epsit.network.IHttpRequest;

import java.io.UnsupportedEncodingException;

public class HttpStringTask<T> extends HttpTask {

    public HttpStringTask(String url, IHttpRequest iHttpRequest, CallBackListener callBackListener, T requestData){
        this.iHttpRequest = iHttpRequest;
        this.iHttpRequest.setUrl(url);
        this.iHttpRequest.setListener(callBackListener);
        if(requestData != null){
            String jsonStr = JSON.toJSONString(requestData);
            try {
                this.iHttpRequest.setData(jsonStr.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

}
