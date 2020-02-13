package com.epsit.networkapi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.epsit.network.DnHttp;
import com.epsit.network.common_type.IStringListener;
import com.epsit.network.json.IJsonListener;

/**
 * 主页面这么多的接口应用点击按钮，如果都要请求网络，怎么操作呢，怎么知道请求的申请是点击的哪个按钮触发的最后要调用的是哪个方法？
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "MainActivity";
    final static int REQUEST_CODE_ASK_INTENT = 1;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.request).setOnClickListener(this);
        findViewById(R.id.requestNews).setOnClickListener(this);
        findViewById(R.id.weather).setOnClickListener(this);
        result = findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.request:{
                onCallAndCheckPermission();//有权限后会请求的
            }
                break;
            case R.id.requestNews:{
                requestNews();
            }
                break;
            case R.id.weather:{
                requestZhouWeather();
            }break;
        }

    }

    public void requestNews(){
        Log.e(TAG,"requestNews");
        //检测权限
        int checkCallPhonePermisssion = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        Log.e(TAG, "requestNews = "+checkCallPhonePermisssion);
        if (checkCallPhonePermisssion != PackageManager.PERMISSION_GRANTED || !checkOpsPermission(MainActivity.this, Manifest.permission.INTERNET)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("应用需要网络请求权限，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new
                                        String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            } else {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
            }

        } else {
            Log.e(TAG,"有权限，直接 request()");
            requestNewsReally();
        }
    }

    public void requestNewsReally(){
        Log.e(TAG, "requestNewsReally-->" );
        DnHttp.sendRequest("http://result.eolinker.com/k2BaduF2a6caa275f395919a66ab1dfe4b584cc60685573?uri=tt", null, News.class, new IJsonListener<News>() {
            @Override
            public void onSuccess(News news) {
                if (news != null && news.getError_code()==0) {
                    News.ResultBean resultBean = news.getResult();
                    if (resultBean != null) {
                        if (resultBean.getData() != null && resultBean.getData().size() > 0) {
                            result.setText("有查询到新闻数据，size大于0");
                        } else {
                            result.setText("新闻集合为空或者没有数据");
                        }
                    } else {
                        result.setText("返回的新闻信息为null");
                    }
                } else {
                    result.setText("onSuccess 返回的新闻对象为null"+(news != null) +"  "+(news!=null ? news.getError_code():"未知code"));
                }
            }

            @Override
            public void onFailed() {
                result.setText("onFailed()");
            }
        });
    }
    //请求天气的测试
    public void onCallAndCheckPermission() {
        Log.e(TAG,"onCallAndCheckPermission");
        //检测权限
        int checkCallPhonePermisssion = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        Log.e(TAG, "checkCallPhonePermisssion = "+checkCallPhonePermisssion);
        if (checkCallPhonePermisssion != PackageManager.PERMISSION_GRANTED || !checkOpsPermission(MainActivity.this, Manifest.permission.INTERNET)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("应用需要网络请求权限，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new
                                        String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            } else {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
            }

        } else {
            Log.e(TAG,"有权限，直接 requestWeather()");
            requestWeather();
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
                    Toast.makeText(MainActivity.this, "Permission Denied",
                            Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void requestWeather() {
        //https://www.sojson.com/blog/305.html 网页的说明，请求接口是：
        //101240701  代表赣州的cityCode
        //所以请求接口为： http://t.weather.sojson.com/api/weather/city/101240701   测试有效
        int checkCallPhonePermisssion = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        Log.e(TAG, "requestNews = "+checkCallPhonePermisssion);
        if (checkCallPhonePermisssion != PackageManager.PERMISSION_GRANTED || !checkOpsPermission(MainActivity.this, Manifest.permission.INTERNET)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("应用需要网络请求权限，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new
                                        String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            } else {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
            }

        } else {
            Log.e(TAG,"有权限，直接 request()");
            requestJsonWeather();
        }

    }

    public void requestJsonWeather(){
        Log.e(TAG,"以json返回，还要自己给");
    }

    //请求州讯天气的接口
    public void requestZhouWeather(){
        int checkCallPhonePermisssion = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        Log.e(TAG, "requestNews = "+checkCallPhonePermisssion);
        if (checkCallPhonePermisssion != PackageManager.PERMISSION_GRANTED || !checkOpsPermission(MainActivity.this, Manifest.permission.INTERNET)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("应用需要网络请求权限，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new
                                        String[]{Manifest.permission.INTERNET}, REQUEST_CODE_ASK_INTENT);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            } else {
                //请求权限
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
