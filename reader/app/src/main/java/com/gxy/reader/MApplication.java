package com.gxy.reader;

import android.Manifest;
import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.Utils;
import com.gxy.reader.data.Const;

import java.io.File;
import java.util.List;

/**
 * Created by guxiye on 2018/7/9.
 */

public class MApplication extends Application {
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LogUtils.getConfig().setGlobalTag("gxyReader");
        if(PermissionUtils.isGranted(PERMISSIONS_STORAGE)) {

            FileUtils.createOrExistsDir(Const.PATH_MAIN);
            try {
                FileIOUtils.writeFileFromIS(new File(Const.PATH_MAIN + "/book.txt"), getAssets().open("book.txt"));
            } catch (Exception e) {

            }
        }else{
            PermissionUtils.permission(PERMISSIONS_STORAGE).callback(new PermissionUtils.FullCallback() {
                @Override
                public void onGranted(List<String> permissionsGranted) {//获取到了权限
                    for(String s:permissionsGranted){
                        //TODO 完善判断逻辑
                        Log.d("gxy","granted:"+s);
                    }
                }

                @Override
                public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                    for(String s:permissionsDeniedForever){//没获取到权限
                        Log.d("gxy","den1:"+s);
                    }
                    for(String s:permissionsDenied){
                        Log.d("gxy","den2:"+s);
                    }
                }
            }).request();
        }

    }
}
