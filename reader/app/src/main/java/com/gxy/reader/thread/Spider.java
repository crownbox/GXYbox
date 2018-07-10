package com.gxy.reader.thread;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
        //name=bookName;
        name="神仙道";
        this.back=arg;
    }

    @Override
    public void run() {
        if(null!=name&&!name.equals("")) {
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

            Elements elements=document.getElementsByAttributeValue("class","type_show");


            for(Element e:elements){
                for(Element node:e.children()) {
                    String attrValue = node.attr("class");
                    if (attrValue.equals("bookbox")) {
                        for(Element end0:node.children()){
                            if(end0.attr("class").equals("p10")) {
                                for(Element end:end0.children()) {
                                    if (end.attr("class").equals("bookimg")) {
                                        Log.e("reader", "img:" + end.selectFirst("img").attr("src"));
                                    } else {
                                        StringBuilder sb=new StringBuilder();
                                        int j=0;
                                        for(Element e5:end.children()){

                                            switch (j){
                                                case 0:
                                                    sb.append("书名：");
                                                    sb.append(e5.text());
                                                    break;
                                                case 1:
                                                    sb.append(e5.text());
                                                    break;
                                                case 2:
                                                    sb.append(e5.text());
                                                    break;
                                                case 3:
                                                    break;
                                                case 4:
                                                    break;
                                            }
                                            j++;

                                        }
                                        Log.e("reader",  sb.toString());
                                    }
                                }
                            }

                        }

                    } else {
                        Log.e("reader", "attrs:" + attrValue);
                    }
                }
            }

        }catch (IOException e){
            Log.e("reader",e.getLocalizedMessage());
        }
    }
}
