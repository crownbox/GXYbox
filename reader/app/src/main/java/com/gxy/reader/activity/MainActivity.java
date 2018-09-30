package com.gxy.reader.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.gxy.reader.R;
import com.gxy.reader.adapter.BooklistAdapter;
import com.gxy.reader.thread.Spider;
import com.gxy.reader.thread.SpiderCallBack;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
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
                    final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("发生未知错误").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                    break;
                default:
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
        listView.setOnItemClickListener(this);
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
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap map=(HashMap) adapter.getItem(i);
        Intent intent=new Intent(this,BookInfoActivity.class);
        intent.putExtra("bookinfo",map);
        startActivity(intent);
    }
}
