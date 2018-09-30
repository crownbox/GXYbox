package com.gxy.reader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.NetworkUtils;

/**
 * Created by guxiye on 2018/9/29.
 */

public class BaseActivity extends Activity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }
    public  void initData(){

    }

    public void initView(){

    }

    public boolean isNetConnect(){
        return NetworkUtils.isConnected();
    }
}
