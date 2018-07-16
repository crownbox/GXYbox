package com.gxy.reader.adapter;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxy.reader.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/7/10.
 */

public class BooklistAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<HashMap<String,String>> data;
    public  BooklistAdapter(Context mContext, List arg){
        inflater= LayoutInflater.from(mContext);
        data=arg;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_booklist,null);
            holder.imageView=convertView.findViewById(R.id.imageview_icon);
            holder.textView_name=convertView.findViewById(R.id.textview_bookname);
            holder.textView_author=convertView.findViewById(R.id.textview_author);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        String url=data.get(position).get("img");
        Picasso.get().load(url).resize(250,300).into(holder.imageView);
        holder.textView_name.setText(data.get(position).get("bookname"));
        holder.textView_author.setText(data.get(position).get("author"));
        Log.d("reader",url);
        return convertView;
    }

    private class ViewHolder{
        private ImageView imageView;
        private TextView textView_name,textView_author;
    }

}

