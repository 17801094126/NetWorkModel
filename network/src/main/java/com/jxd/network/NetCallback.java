package com.jxd.network;

public interface NetCallback {

    void onSuccess(String message);
    void error(String message,String code);
}
