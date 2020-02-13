package com.epsit.network.json;

import android.util.Log;

import com.epsit.network.CallBackListener;
import com.epsit.network.IHttpRequest;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 返回值是json对象的请求
 */
public class JsonHttpRequest implements IHttpRequest {
    String TAG ="JsonHttpRequest";
    private String url;
    private byte[] data;
    private CallBackListener callBackListener;
    private HttpURLConnection httpURLConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    public void execute() {
        URL url = null;
        try {
            url = new URL(this.url);
            Log.e(TAG,"this.url= "+this.url+"   ==="+url.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setUseCaches(false);//不适用缓存
            httpURLConnection.setConnectTimeout(6000);//连接超时时间
            httpURLConnection.setInstanceFollowRedirects(true);//是成员变量，仅仅作用于当前函数，设置当前这个对象
            httpURLConnection.setReadTimeout(3000);//响应超时时间
            httpURLConnection.setDoInput(true);//是否可以写入数据
            httpURLConnection.setDoOutput(true);//是否可以输出数据
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.connect();//进行连接,连接之后才拿得到输入输出流
            if (data != null) {
                OutputStream out = httpURLConnection.getOutputStream();
                //缓冲字节流，包装字节流
                BufferedOutputStream bos = new BufferedOutputStream(out);
                bos.write(data);
                bos.flush();
                bos.close();
                out.close();
            }
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream in = httpURLConnection.getInputStream();
                Log.e(TAG,"200----请求成功了！！！");
                callBackListener.onSuccess(in);
            } else {
                throw new RuntimeException("请求失败:"+code);//按理这里要进行重试几次的
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");//按理这里要进行重试几次的
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
