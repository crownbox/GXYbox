package com.gxy.reader.thread;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created by guxiye on 2018/7/8.
 * 笔趣看 http://www.biqukan.com
 */

public class Spider extends Thread {
    private String name;
    private SpiderCallBack back;

    public Spider(String bookName,SpiderCallBack arg) {
        name=bookName;
        this.back=arg;
    }

    @Override
    public void run() {
        if(null!=name) {
            init(name);
            back.success();
        }else{
            back.failed();
            throw new NullPointerException("参数为空");
        }

    }

    private void init(String bookName){
        try {
            URL url=new URL("http://www.biqukan.com/s.php?ie=gbk&s=2758772450457967865&q="+bookName);
            Document document = Jsoup.connect(url.toString()).data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(30000)
                    .get();
            Elements elements=document.select("div");
            for (Element e:elements){
                if(e.hasClass("bookbox")){
                    Elements elements1=e.select("")
                }
            }
            String list=element.attr("div");
        }catch (IOException e){
            Log.e("gxy","error:"+e.getLocalizedMessage());
        }
    }
}
