package com.gxy.reader.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gxy.reader.R;
import com.gxy.reader.adapter.BooklistAdapter;
import com.gxy.reader.book.Book;
import com.gxy.reader.thread.BookInfoSpider;
import com.gxy.reader.thread.DownLoaderThread;
import com.gxy.reader.thread.Spider;
import com.gxy.reader.thread.SpiderCallBack;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by guxiye on 2018/9/29.
 */

public class BookInfoActivity extends BaseActivity implements View.OnClickListener{
    private Book book;
    private Button btn;
    private ImageView imageView;
    private TextView bookName,intro;
    private List chapterList;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String src=(String)chapterList.get(2);
                    intro.setText(src);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bookinfo);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        final HashMap map=(HashMap) getIntent().getSerializableExtra("bookinfo");
        String bookUrl= Spider.URL+(String) map.get(BooklistAdapter.URL);
        String imgUrl=(String)map.get(BooklistAdapter.IMG);
        String author=(String)map.get(BooklistAdapter.AUTHOR);
        String bookname=(String)map.get(BooklistAdapter.BOOKNAME);
        book=new Book(bookname,imgUrl,author,new String[]{bookUrl});
        BookInfoSpider spider=new BookInfoSpider(bookUrl, new SpiderCallBack() {
            @Override
            public void success(List list) {
                chapterList=list;
                Message message=new Message();
                message.what=0;
                message.obj=chapterList;
                handler.sendMessage(message);
            }

            @Override
            public void failed() {

            }
        });
        spider.start();
    }

    @Override
    public void initView() {
        super.initView();
        imageView=findViewById(R.id.bookinfo_imageView);
        bookName=findViewById(R.id.bookinfo_textView3);
        bookName.setText(book.getBookName());
        btn=findViewById(R.id.bookinfo_button);
        btn.setOnClickListener(this);
        intro=findViewById(R.id.bookinfo_textView4);
        Picasso.get().load(book.getImg_url()).resize(250,300).into(imageView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bookinfo_button:
                downLoadBook(book,chapterList);
                break;
            default:
                break;
        }
    }

    //下载功能
    private void downLoadBook(Book book, List chapterList) {
        DownLoaderThread thread=new DownLoaderThread(book,chapterList);
        thread.start();
    }
}
