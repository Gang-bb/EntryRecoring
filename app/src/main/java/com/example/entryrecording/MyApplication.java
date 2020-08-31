package com.example.entryrecording;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.entryrecording.greendao.DaoMaster;
import com.example.entryrecording.greendao.DaoSession;

public class MyApplication extends Application {
    private static DaoSession daoSession;

    private static  MyApplication mInstance;


    public static  MyApplication getInstance(){

        return  mInstance;
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }


    @Override
    public void onCreate(){
        super.onCreate();
         setupDatabase();

    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Entry0.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

}
