package com.example.marc.syncdemo.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marc.syncdemo.API.API_SUBMITINFO;
import com.example.marc.syncdemo.API.URL.URL;
import com.example.marc.syncdemo.Model.Info;
import com.example.marc.syncdemo.R;
import com.example.marc.syncdemo.Tools.TimeID;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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

public class AddInfoActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.add_submit)
    Button add_submit;

    @BindView(R.id.add_cancel)
    Button add_cannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

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
        final API_SUBMITINFO mApi = retrofit.create(API_SUBMITINFO.class);

        final TimeID timeID = new TimeID();


        add_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消添加，返回上一级界面
                finish();
            }
        });

        //向数据库中添加新的数据
        //测试数据
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Info info = new Info();
                //infoList初始化
                List<Info> infoList = new ArrayList<Info>();
                //获取用户填写的数据
                info.setName(et_name.getText().toString());
                info.setPhone(et_phone.getText().toString());
                //新增数据 状态默认为0
                info.setState(0);
                //时间tid标识
                info.setTid(timeID.generateId());
                //时间戳
                info.setAnchor(new Timestamp(System.currentTimeMillis()).toString());
                Log.d("timestamp",new Timestamp(System.currentTimeMillis()).toString());
                //执行插入操作
                info.save();

                infoList.add(info);

                //对发送请求进行封装
                Call<ResponseBody> result = mApi.addInfo("application/json",infoList);
                //发送网络请求（异步）
                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(),"suceess",Toast.LENGTH_SHORT).show();
                        try {
                            String responseData = response.body().string();
                            Log.d("result",responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        Log.d("result",t.toString());
                    }

                });





                et_name.setText("");
                et_name.clearFocus();
                et_phone.setText("");
                et_phone.clearFocus();

            }
        });


    }
}
