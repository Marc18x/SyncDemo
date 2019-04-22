package com.example.marc.syncdemo.Activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.dialogloadding.WeiboDialogUtils;
import com.example.marc.syncdemo.API.API_SYNCINFO;
import com.example.marc.syncdemo.API.URL.URL;
import com.example.marc.syncdemo.Database.MyDatabaseHelper;
import com.example.marc.syncdemo.Model.Info;
import com.example.marc.syncdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends AppCompatActivity {


    private List<Info> infoList = new ArrayList<>();
    private List<Info> infoList_response = new ArrayList<>();

    private Dialog mWeiboDialog;

    @BindView(R.id.button_create)
    Button button_create;

    @BindView(R.id.button_insert)
    Button button_insert;

    @BindView(R.id.button_update)
    Button button_update;

    @BindView(R.id.button_show)
    Button button_show;

    @BindView(R.id.button_delete)
    Button button_delete;

    @BindView(R.id.button_submit)
    Button button_submit;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //关闭loading图
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化绑定ButterKinife
        ButterKnife.bind(this);

        //OkHttpClient初始化
        OkHttpClient okHttpClient = new OkHttpClient();

        //Retrofit初始化
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        //创建网络请求接口的实例 mApi
        final API_SYNCINFO mApi = retrofit.create(API_SYNCINFO.class);


        //创建数据库
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自动创建数据库
                Connector.getDatabase();
                Toast.makeText(getApplicationContext(),"LitePal数据库创建成功",Toast.LENGTH_SHORT).show();
            }
        });

        //模拟写入数据
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //跳转到插入数据页面
                Intent intent = new Intent(MainActivity.this,AddInfoActivity.class);
                startActivity(intent);



            }
        });

        //显示所有数据
        button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到页面显示所有数据
                Intent intent = new Intent(MainActivity.this,ShowItemActivity.class);
                startActivity(intent);
            }
        });

        //更新数据库
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //更新数据库操作----当前version版本号高于本地已存版本号时自动调用更新数据库
                //执行更新操作的同事，覆盖原有的数据， 不用担心数据丢失的问题
                Connector.getDatabase();
            }
        });

        //还原数据库
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //删除数据库
                LitePal.deleteAll(Info.class);
                Toast.makeText(getApplicationContext(),"还原数据库成功",Toast.LENGTH_SHORT).show();
            }
        });

        //同步数据库
        button_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //获取所有数据
                infoList = LitePal.findAll(Info.class);
                //对发送请求进行封装
                Call<ResponseBody> result = mApi.addInfo("application/json",infoList);
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(MainActivity.this, "加载中...");
                //发送网络请求（异步）
                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            String responseData = response.body().string();

                            Gson gson = new Gson();

                            //实现类型转换 将responseData转换成infoList  ---Token后接解析对象，此处为List<Info>
                            infoList_response = gson.fromJson(responseData,new TypeToken<List<Info>>(){}.getType());

                            LitePal.deleteAll(Info.class);

                            //存入新数据
                            LitePal.saveAll(infoList_response);

                            Log.d("result",responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(),"suceess",Toast.LENGTH_SHORT).show();
                        //关闭加载动画
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        Log.d("result",t.toString());
                        WeiboDialogUtils.closeDialog(mWeiboDialog);
                    }

                });
            }
        });
    }
}
