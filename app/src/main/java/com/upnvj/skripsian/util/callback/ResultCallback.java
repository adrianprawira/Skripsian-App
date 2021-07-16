package com.upnvj.skripsian.util.callback;

public interface ResultCallback<T> {

    void onSuccess(T data);

    void onError(String message);
}