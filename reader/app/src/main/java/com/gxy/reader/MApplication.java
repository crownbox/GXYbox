package com.gxy.reader;

import android.app.Application;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.gxy.reader.Data.Const;

import java.io.File;

/**
 * Created by guxiye on 2018/7/9.
 */

public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        FileUtils.createOrExistsDir(Const.PATH_MAIN);

        try {
            FileIOUtils.writeFileFromIS(new File(Const.PATH_MAIN+"book.txt"),getAssets().open("book.txt"));
        }catch (Exception e){

        }

    }
}
