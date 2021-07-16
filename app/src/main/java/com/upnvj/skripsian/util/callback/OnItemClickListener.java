package com.upnvj.skripsian.util.callback;

public abstract class OnItemClickListener<T> {
    public abstract void onClick(T data);

    public void onLongClick(T data) {
    }
}
