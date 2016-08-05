# VerticalAutoScroll
Vertical auto scroll View. 仿京东，淘宝，竖直自动轮播view。


# 效果
<img src="autoplay_view.gif" />
<img src="autoplay_view.png" />

# Gradle
	//使用 autoplay viewpager
	compile ('com.xyzlf.autoplay.view:verticalplay:0.0.1') {
	    exclude group: 'com.android.support', module: 'appcompat-v7'
	}

# 使用方式

1、在xml中使用，如下：
	<com.xyzlf.vertical.autoscroll.VerticalScrollView
        android:id="@+id/vertical_scroll_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        custom:intervalDuration="4000"
        custom:animDuration="1000"/>


2、代码中使用：
	List<ItemData> datas = new ArrayList<>();
        datas.add(new ItemData("瑞士维氏军刀 新品满200-50","最新"));
        datas.add(new ItemData("家居家装焕新季，讲199减100！","最火爆"));
        datas.add(new ItemData("带上相机去春游，尼康低至477","HOT"));
        datas.add(new ItemData("价格惊呆！电信千兆光纤上市","new"));

        final MyScrollAdapter adapter = new MyScrollAdapter(datas);
        final VerticalScrollView tbView = (VerticalScrollView) findViewById(R.id.vertical_scroll_view);
        tbView.setAdapter(adapter);
        //开启线程滚东
        tbView.start();

3、Adapter需要继承 **VerticalScrollAdapter<T>**，完整Adapter代码如下：

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
