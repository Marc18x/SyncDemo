package com.example.marc.syncdemo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE = "create table list2 ("
            + "[id] integer primary key autoincrement,"
            + "[name] varchar(20) not null,"
            + "[phone] varchar(11) not null,"
            + "[status] interger(1) not null,"
            + "[anchor] timestamp NOT NULL DEFAULT(datetime('now','localtime')));";

    private Context mContext;

    public MyDatabaseHelper(Context context ,String name,
                            SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mContext,"Create Database Success!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库迭代操作（版本号高于当前现有版本）
        db.execSQL("drop table if exists list2");
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mContext,"更新数据库",Toast.LENGTH_SHORT).show();

    }
}
