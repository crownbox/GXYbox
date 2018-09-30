package com.gxy.reader.thread;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gxy.reader.book.Book;
import com.gxy.reader.data.Const;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guxiye on 2018/9/30.
 */

public class DownLoaderThread extends BaseThread{
    private Book book;
    private List list;
    private String bookPath,filePath;
    public DownLoaderThread(Book arg1, List arg2) {
        this.book=arg1;
        this.list=arg2;
    }

    @Override
    public void run() {
        try {
            createFile();
            parseHtmlAndDownload();
        }catch (Exception e){
            LogUtils.e("error:"+e.getLocalizedMessage());
        }
        super.run();
    }

    /***
     * 创建存放书的文件夹
     */
    private void createFile() {
        bookPath=Const.PATH_BOOK+ File.separator+book.getBookName();
        FileUtils.createOrExistsDir(bookPath);
    }
    /***
     * 解析网页并下载内容
     */
    private void parseHtmlAndDownload()throws Exception{
        List list_all=(ArrayList)list.get(1);
        Iterator iterator=list_all.iterator();
        while (iterator.hasNext()){
            HashMap book=(HashMap) iterator.next();
            String chaptername=(String) book.get(BookInfoSpider.CHAPTER);
            String url=(String) book.get(BookInfoSpider.URL);
            download(chaptername,url);
        }
    }

    private void download(String chapter,String url)throws Exception{
        if(null==chapter||"".equals(chapter)||null==url||"".equals(url)){
            return;
        }
        filePath=bookPath+File.separator+chapter+".txt";
        FileUtils.createOrExistsFile(filePath);
        Document document=getDocument(Spider.URL+url,15000);
        parseDocument(document);
    }
    private void parseDocument(Document document){
        Element elements=document.getElementsByAttributeValue("class","showtxt").first();
        String text=elements.text();
        FileIOUtils.writeFileFromString(filePath,text);
        LogUtils.e(text);
    }
}
