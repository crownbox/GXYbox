package com.gxy.reader.book;


/**
 * @author Created by guxiye on 2018/9/19.
 */
public class Book {
    /***
     * 本地路径
     */
    public String local_path;
    /***
     * 书名
     */
    public String bookName;
    /***
     * 书本封面图片
     */
    public String img_url;
    /***
     * 书本作者
     */
    public String author;
    /***
     * 书本的网络路径，可能多个网站都存在这本书
     */
    public String[] urls;
    /***
     * 书本总字数
     */
    public int textNum;
    /***
     *  内容
     */
    public String content;
    /***内容
     *
     */
    public byte[] contentArray;

    public Book(String bookName, String img_url, String author, String[] urls) {
        this.bookName = bookName;
        this.img_url = img_url;
        this.author = author;
        this.urls = urls;
    }

    public String getLocal_path() {
        return local_path;
    }

    public void setLocal_path(String local_path) {
        this.local_path = local_path;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public int getTextNum() {
        return textNum;
    }

    public void setTextNum(int textNum) {
        this.textNum = textNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getContentArray() {
        return contentArray;
    }

    public void setContentArray(byte[] contentArray) {
        this.contentArray = contentArray;
    }
}
