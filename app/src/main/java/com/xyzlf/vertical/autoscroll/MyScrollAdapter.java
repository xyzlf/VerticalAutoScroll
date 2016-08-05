package com.xyzlf.vertical.autoscroll;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhanglifeng on 2016/8/5.
 */

public class MyScrollAdapter extends VerticalScrollAdapter<ItemData> {


    public MyScrollAdapter(List<ItemData> mDatas) {
        super(mDatas);
    }

    @Override
    public View getView(VerticalScrollView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
    }

    @Override
    public void setItem(final View view, final ItemData data) {
        TextView tv = (TextView) view.findViewById(R.id.content);
        tv.setText(data.content);
        TextView tag = (TextView) view.findViewById(R.id.tag);
        tag.setText(data.tag);
        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), data.tag, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
