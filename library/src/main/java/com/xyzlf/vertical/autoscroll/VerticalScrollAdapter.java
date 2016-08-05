package com.xyzlf.vertical.autoscroll;

import android.view.View;

import java.util.List;

/**
 * Created by zhanglifeng on 2016/8/2.
 * Vertical auto scroll view adapter
 */

public abstract class VerticalScrollAdapter<T> {

    private List<T> mDatas;

    public VerticalScrollAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     * @return Count of items.
     */
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        return null == mDatas ? null : mDatas.get(position);
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param parent {@link VerticalScrollView}
     * @return view
     */
    public abstract View getView(VerticalScrollView parent);

    /**
     * 设置数据
     * @param view scroll view
     * @param data 数据
     */
    public abstract void setItem(View view, T data);

}
