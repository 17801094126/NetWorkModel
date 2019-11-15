package com.jxd.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLTransactionRollbackException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Manager {

    //正面
    public static void startOcrType(String institutionCode, String password,
                                    int type, String className,
                                    final Activity activity, final NetCallback ocrCallback) {
        RequestBean requestBean=new RequestBean();
        requestBean.setInstitutionCode(institutionCode);
        requestBean.setPassword(password);
        requestBean.setType(type);
        requestBean.setClassName(className);

        OkHttpClient client=new OkHttpClient();
        MediaType MEDIA_TYPE_JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody=RequestBody.create(new Gson().toJson(requestBean),MEDIA_TYPE_JSON);
        Request request = new Request.Builder()
                .url("http:// 192.168.1.116:19610/sdkValid/checkAuthority")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(final Call call, Response response)  {
                try {
                    final String  string = response.body().string();
                    Log.e("QQQQQ",string);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ocrCallback.onSuccess(string);
                            Gson gson = new Gson();
                            ReturnBean returnBean = gson.fromJson(string, ReturnBean.class);
                            activity.startActivity(new Intent(activity,NetWorkActivity.class));
                        }
                    },0);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
