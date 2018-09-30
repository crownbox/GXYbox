package com.gxy.reader.data;

import android.os.Environment;

import java.io.File;

/**
 * Created by guxiye on 2018/7/9.
 */

public class Const {
    public static final String PATH_MAIN= Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.gxy.reader";
    public static final String PATH_BOOK=PATH_MAIN+ File.separator+"book";

}
