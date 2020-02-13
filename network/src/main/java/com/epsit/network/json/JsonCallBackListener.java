package com.epsit.network.json;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.epsit.network.CallBackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 内部回调接口实现类，将inputStream解析成需要的数据类型
 * @param <T>
 */
public class JsonCallBackListener<T> implements CallBackListener {
    //结果的类
    private Class<T> response;

    private IJsonListener jsonListener;

    Handler handler = new Handler(Looper.getMainLooper());
    /**
     *
     * @param responseBean 返回类型的Class对象，比如要接受的是User对象，那就传User.class
     * @param iJsonListener
     */
    public JsonCallBackListener(Class<T>responseBean, IJsonListener<T> iJsonListener){
        this.response = responseBean;
        this.jsonListener = iJsonListener;
    }


    @Override
    public void onSuccess(InputStream inputStream) {
        String jsonStr = getContent(inputStream);
        jsonStr = jsonStr.replace("@","");//根据这个请求，发现有需要返回为String类型数据的需求，这个替换的操作就放到应用层自己处理好了
        Log.e("jsonCallBack","onSuccess-->"+jsonStr);
        final T t = JSON.parseObject(jsonStr, response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                jsonListener.onSuccess(t);
            }
        });


    }

    @Override
    public void onFailed() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jsonListener.onFailed();
            }
        });

    }

    public String getContent(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
