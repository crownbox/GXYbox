package com.gxy.reader.thread;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gxy.reader.adapter.BooklistAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guxiye on 2018/7/8.
 * 笔趣看 http://www.biqukan.com
 */

public class Spider extends BaseThread {
    public final static String URL="http://www.biqukan.com";
    private String name;
    private SpiderCallBack back;
    private List list;
    private HashMap<String,Object> map;
    public Spider(@NonNull String bookName, SpiderCallBack arg) {
        name=bookName;
        this.back=arg;
        list=new ArrayList();
    }

    @Override
    public void run() {
        if(null!=name&&!name.equals("")) {
            init(name);
            back.success(list);
        }else{
            back.failed();
            throw new NullPointerException("参数为空");
        }
    }

    private void init(String bookName){
        try {
            URL url=new URL("http://www.biqukan.com/s.php?ie=gbk&s=2758772450457967865&q="+bookName);
            Document document = getDocument(url.toString(),0);
            Elements element=document.getElementsByAttributeValue("class","type_show");
            praseElements(element.first());

        }catch (Exception e){
            back.failed();
        }
    }

    //迭代解析
    private void praseElements(Element elements){
        for(Element element:elements.children()) {
            if (element.attr("class").equals("p10")) {
                map=new HashMap<>();
                for (Element end : element.children()) {
                    if (end.attr("class").equals("bookimg")) {
                        map.put(BooklistAdapter.IMG,URL+end.selectFirst("img").attr("src"));
                        Log.e("reader", "img:" + end.selectFirst("img").attr("src"));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        int j = 0;
                        for (Element e5 : end.children()) {
                            switch (j) {
                                case 0:
                                    sb.append("书名：");
                                    map.put(BooklistAdapter.BOOKNAME,e5.text());
                                    map.put(BooklistAdapter.URL,e5.selectFirst("a").attr("href"));
                                    sb.append(e5.text());
                                    sb.append("链接：");
                                    sb.append(e5.selectFirst("a").attr("href"));
                                    break;
                                case 1:
                                    map.put(BooklistAdapter.KIND,e5.text());
                                    sb.append(e5.text());
                                    break;
                                case 2:
                                    map.put(BooklistAdapter.AUTHOR,e5.text());
                                    sb.append(e5.text());
                                    break;
                                case 3:
                                    break;
                                default:
                                    break;
                            }
                            j++;
                        }
                        Log.e("reader", sb.toString());
                    }
                }
                list.add(map);
            }else{
                praseElements(element);
            }
        }
    }
}
