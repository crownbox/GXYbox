package com.gxy.reader;

import android.app.Application;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.gxy.reader.Data.Const;

/**
 * Created by guxiye on 2018/7/9.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        FileUtils.createOrExistsDir(Const.PATH_MAIN);

    }
}
