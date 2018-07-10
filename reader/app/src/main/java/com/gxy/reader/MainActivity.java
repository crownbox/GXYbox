package com.gxy.reader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.gxy.reader.thread.Spider;
import com.gxy.reader.thread.SpiderCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText searchView;
    private ListView listView;
    private String bookName;
    private Spider spider;
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

                    }
                    @Override
                    public void failed() {

                    }
                });
                spider.start();
                break;
            case R.id.edittext_searchview:
                break;
        }
    }
}
