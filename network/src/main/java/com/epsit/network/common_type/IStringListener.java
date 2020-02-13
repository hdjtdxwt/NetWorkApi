package com.epsit.network.common_type;

/**
 * 内部接口，给返回的封装类DnHttp调用的
 * @param <String>
 */
public interface IStringListener<String> {
    void onSuccess(String str);

    void onFailed();
}
