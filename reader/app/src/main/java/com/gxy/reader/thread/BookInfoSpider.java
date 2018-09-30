package com.gxy.reader.thread;

import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guxiye on 2018/9/29.
 */

public class BookInfoSpider extends BaseThread {
    public static final String URL="url";
    public static final String CHAPTER="chapter";
    private String bookUrl;
    private SpiderCallBack callBack;
    private HashMap map_new;
    private HashMap map_all;
    private String intro;
    private List list_new;
    private List list_all;
    private List list;
    public BookInfoSpider(@NonNull String book_url,SpiderCallBack back) {
        this.bookUrl=book_url;
        this.callBack=back;
        list_all=new ArrayList();
        list_new=new ArrayList();
        list=new ArrayList();
    }

    @Override
    public void run() {
        if(null!=bookUrl||"".equals(bookUrl)){
            try {
                initUrl();
                list.add(list_new);
                list.add(list_all);
                list.add(intro);
                callBack.success(list);
            }catch (Exception e){
                callBack.failed();
            }
        }else{
            callBack.failed();
        }
        super.run();
    }

    private void initUrl() throws Exception {
        URL url=new URL(bookUrl);
        Document document = getDocument(url.toString(),0);
        getInfo(document);
        getBookCharacterList(document);
    }

    /***
     * 获取书的一些基本信息
     * @param document
     */
    private void getInfo(Document document) {
        Element elements=document.getElementsByAttributeValue("class","info").first();

        for(Element element:elements.children()){
            if("intro".equals(element.attr("class"))){
                intro=element.text();
                LogUtils.e(intro);
            }
        }
    }

    /***
     * 解析目录章节
     * @param document
     */
    private void getBookCharacterList(Document document){
        Element elements=document.getElementsByAttributeValue("class","listmain").first();
        for (Element element:elements.children()){
            int flag=0;
            for(Element e:element.children()) {
                if(e.is("dt")){
                    String text=e.text();
                //    LogUtils.e(text);
                    flag++;
                }
                if(flag==1){
                    //这里的是最新章节
                    map_new=new HashMap();
                    Elements node=e.select("a");
                    String url = node.attr("href");
                    String chapter=node.text();
                    map_new.put(URL,url);
                    map_new.put(CHAPTER,chapter);
                    list_new.add(map_new);
                  //  LogUtils.e(chapter+":"+url);
                }
                if(flag==2) {
                    //这里的是所有章节
                    map_all=new HashMap();
                    Elements node=e.select("a");
                    String url = node.attr("href");
                    String chapter=node.text();
                    map_all.put(URL,url);
                    map_all.put(CHAPTER,chapter);
                    list_all.add(map_all);
                  //  LogUtils.e(chapter+":"+url);
                }
            }
        }
    }
}
