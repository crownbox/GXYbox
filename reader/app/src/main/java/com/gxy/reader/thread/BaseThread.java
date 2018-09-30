package com.gxy.reader.thread;

import android.support.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by guxiye on 2018/9/29.
 */

public class BaseThread extends Thread {
    public Document getDocument(@NonNull String url,int timeout) throws Exception{
        Document document = Jsoup.connect(url).data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(0==timeout?5000:timeout)
                .get();
        return document;
    }
}
