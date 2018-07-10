package com.gxy.reader;

import android.app.Application;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;

/**
 * Created by Administrator on 2018/7/9.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        FileUtils.createOrExistsDir(Const.PATH_MAIN);
    }
}
