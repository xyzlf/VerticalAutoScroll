package com.xyzlf.vertical.autoscroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        List<ItemData> datas = new ArrayList<>();
        datas.add(new ItemData("瑞士维氏军刀 新品满200-50","最新"));
        datas.add(new ItemData("家居家装焕新季，讲199减100！","最火爆"));
        datas.add(new ItemData("带上相机去春游，尼康低至477","HOT"));
        datas.add(new ItemData("价格惊呆！电信千兆光纤上市","new"));

        /** 自定义View 实现竖直自动轮播 **/
        customViewScrollView(datas);


        /** 使用ViewFlipper 实现竖直自动轮播 **/
        viewFlipperScrollView(datas);
    }

    private void customViewScrollView(List<ItemData> datas) {
        final MyScrollAdapter adapter = new MyScrollAdapter(datas);
        final VerticalScrollView tbView = (VerticalScrollView) findViewById(R.id.vertical_scroll_view);
        tbView.setAdapter(adapter);
        //开启线程滚东
        tbView.start();
    }

    private void viewFlipperScrollView(List<ItemData> datas) {
        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        for(int i = 0; i<datas.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_item, null);
            TextView tag = (TextView) view.findViewById(R.id.tag);
            TextView content = (TextView) view.findViewById(R.id.content);
            tag.setText(datas.get(i).tag);
            content.setText(datas.get(i).content);
            viewFlipper.addView(view);
        }
        viewFlipper.setAutoStart(true);
    }

}
