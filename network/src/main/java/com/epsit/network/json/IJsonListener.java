package com.epsit.network.json;

/**
 * 回调接口，给外部调用者的
 * @param <T>
 */
public interface IJsonListener<T> {
    void onSuccess(T t);

    void onFailed();
}
