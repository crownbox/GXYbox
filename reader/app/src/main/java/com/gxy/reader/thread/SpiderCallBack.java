package com.gxy.reader.thread;

import java.util.List;

/**
 * Created by guxiye on 2018/7/8.
 */

public abstract class SpiderCallBack {
    public abstract void success(List list);
    public abstract void failed();
}
