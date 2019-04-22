package com.example.marc.syncdemo.API;

import com.example.marc.syncdemo.Model.Info;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

//描述：Retrofit的实现接口

public interface API_SYNCINFO {

    //发送json数据形式的post请求，把网络请求接口的后半部分openapi/api/v2写在里面
    @POST("SyncInfo")
    Call<ResponseBody>  addInfo(@Header("Content-type") String type, @Body List<Info> infoList);
}
