package com.gxy.reader.book;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.gxy.reader.data.Const;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by guxiye on 2018/9/19.
 */
@Getter
@Setter
public class Book {
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
    public Book(String path) {
        contentArray=FileIOUtils.readFile2BytesByMap(path);
        textNum=contentArray.length;
        //content=new String(contentArray,)
    }
}
