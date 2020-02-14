package com.epsit.networkapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.epsit.network.DnHttp;
import com.epsit.network.common_type.IStringListener;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG ="SecondActivity";
    TextView result;
    final int REQUEST_CODE_ASK_INTENT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.second_weather).setOnClickListener(this);
        result = findViewById(R.id.second_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.second_weather:{
                //Intent网络权限好像不需要动态申请的，直接在清单文件里申明了就可以的，6.0之后的手机好像会自动弹框提示需要这么一个权限
                //但是如果不进行权限判断直接调用请求，可能报错，因为如果用户拒绝了给予权限呢，所以这里最好还是要有INTERNET网络权限的判断，然后就会发现判断过好多回了，重复代码和MainActivity有很多
                //requestZhouWeatherReally();
                //但是我自己的oppo A9的手机（android版本是写的9，也就是android9.0,测试时发现即使没有动态权限申请普通的INTERNET网络权限，它自己在应用一打开也会弹框提示要怎么一个权限，拒绝后第二个(后面的SecondActivity)就不会弹框了，所以在这个界面还是要再判断下权限，即使普通的INTERNET网络权限）

                //没有动态提示要Intent权限，然后在SecondActivity请求数据成功了，然后就我就自己锁屏了，一会回来重新打开，就弹框提示要这么一个权限了
                for(int i=0;i<20;i++){
                    requestZhouWeather();//权限判断，可能一开始网络权限被用户拒绝了，不能因为拒绝了而崩溃了（代码从前面一个MainActivity复制来的）
                }
              }break;
        }
    }
    //权限检测
    private static boolean checkOpsPermission(Context context, String permission) {
        //6.0 api 23
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                String opsName = null;
                opsName = AppOpsManager.permissionToOp(permission);
                if (opsName == null) {
                    return true;
                }
                int opsMode = appOpsManager.checkOpNoThrow(opsName, android.os.Process.myUid(), context.getPackageName());
                return opsMode == AppOpsManager.MODE_ALLOWED;
            } catch (Exception ex) {
                return true;
            }
        }
        return true;
    }

    //请求权限返回的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_INTENT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted 授予权限
                    //requestWeather();
                    //requestNewsReally();
                    requestZhouWeatherReally();
                } else {
                    // Permission Denied 权限被拒绝
                    Toast.makeText(SecondActivity.this, "Permission Denied",
                            Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void requestZhouWeather(){
        int intentPermisssion = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        Log.e(TAG, "requestNews = "+intentPermisssion);

        //那就是29版本以上的了，那就需要看弹框的选择的结果了，还有M以下的版本的手机，仅仅需要注册了就可以
        //自己用androidStudio的模拟器6.0的，测试不会一打开应用就弹框提示权限，而直接跳到第二个界面来，上面判断也是有权限，直接就调用requestZhouWeatherReally()并且请求成功了
        if (intentPermisssion != PackageManager.PERMISSION_GRANTED || !checkOpsPermission(SecondActivity.this, Manifest.permission.INTERNET)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("应用需要网络请求权限，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SecondActivity.this, new
                                        String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            } else {
                //请求权限，结果直接就请求了
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
            }

        } else {
            Log.e(TAG,"有权限，直接 request()");
            requestZhouWeatherReally();
        }
    }
    //州讯天气接口的真正请求
    public void requestZhouWeatherReally(){
        Log.e(TAG,"request()方法里头");
        String url = null ;
        url ="http://zhouxunwang.cn/data/?id=7&key=VODPrYVgG9r+ipKA+4g7R2nAMgTgsJeZ/px1&name=jiangxi";//这个接口有300次免费调用的次数限制
        //String url = "http://t.weather.sojson.com/api/weather/city/101030100";//这个查询天气的接口用不了了，报错404，好像网上说某些ip格式的被禁止调用了
        DnHttp.sendStringRequest(url, null,  new IStringListener<String>() {
            @Override
            public void onSuccess(String weather) {
                if (weather != null) {//成功调用并且有数据
                    Log.e(TAG,"以String类型返回的，attributes属性带有@符号，要处理下");
                    String weatherStr = weather.replace("@","");
                    ZhouXunWeather zhouXunWeather = JSON.parseObject(weatherStr, ZhouXunWeather.class);
                    if(zhouXunWeather!=null && zhouXunWeather.getError_code()==0){ //请求成功了
                        if (zhouXunWeather.getCity()!=null && zhouXunWeather.getCity().size()>0) {
                            if (zhouXunWeather.getCity().get(0)!=null) {
                                result.setText("String格式返回的，显示部分结果:"+JSON.toJSONString(zhouXunWeather.getCity().get(0)));
                            } else {
                                result.setText("集合为空或者没有数据");
                            }
                        } else {
                            result.setText("返回的天气信息为null");
                        }
                    }

                } else {
                    result.setText("返回的weather信息为null");
                }
            }

            @Override
            public void onFailed() {
                result.setText("onFailed()");
            }
        });
    }
}
