package com.gxy.reader;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.gxy.reader.adapter.BooklistAdapter;
import com.gxy.reader.thread.Spider;
import com.gxy.reader.thread.SpiderCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText searchView;
    private ListView listView;
    private String bookName;
    private Spider spider;
    private BooklistAdapter adapter;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    adapter=new BooklistAdapter(MainActivity.this,(List)msg.obj);
                    listView.setAdapter(adapter);
                    break;
                case 0:
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setNegativeButton("发生未知错误",null).show();
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.button_search);
        listView=findViewById(R.id.listview_booklist);
        searchView=findViewById(R.id.edittext_searchview);
        btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_search:
                bookName=searchView.getText().toString();
                spider=new Spider(bookName, new SpiderCallBack() {
                    @Override
                    public void success(List data) {

                        Message message=new Message();
                        message.what=1;
                        message.obj=data;
                        handler.sendMessage(message);
                    }
                    @Override
                    public void failed() {
                        Message message=new Message();
                        message.what=0;
                        handler.sendMessage(message);
                    }
                });
                spider.start();
                break;
            case R.id.edittext_searchview:
                break;
        }
    }
}
