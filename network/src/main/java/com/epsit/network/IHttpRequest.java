package com.epsit.network;

public interface IHttpRequest {

     void setUrl(String url);

     //请求数据转换成了数据流，json字符串转换成了byte[]
     void setData(byte[] data);

     //设置回调接口
     void setListener(CallBackListener callBackListener);

     //开始执行
     void execute();

     //上面的void setListener(CallBackListener callBackListener)设置的什么，这里就返回什么
     CallBackListener getCallBackListener();
}
