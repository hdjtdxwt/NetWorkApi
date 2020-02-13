package com.epsit.network.common_type;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.epsit.network.CallBackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringCallBackListener implements CallBackListener {
    private IStringListener iStrListener;

    Handler handler = new Handler(Looper.getMainLooper());

    public StringCallBackListener(IStringListener<String> iStrListener) {
        this.iStrListener = iStrListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        final String jsonStr = getContent(inputStream);
        //jsonStr = jsonStr.replace("@","");//根据这个请求，发现有需要返回为String类型数据的需求，这个替换的操作就放到应用层自己处理好了
        Log.e("jsonCallBack", "onSuccess-->" + jsonStr);
        handler.post(new Runnable() {
            @Override
            public void run() {
                iStrListener.onSuccess(jsonStr);
            }
        });
    }

    @Override
    public void onFailed() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                iStrListener.onFailed();
            }
        });
    }

    //这个类可以放到父类中
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
