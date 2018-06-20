package com.tga.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.tga.models.place;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mada on 5/29/2018.
 */

public abstract class BaseRecyclerViewAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected LayoutInflater mInflater;
    protected List<place> mDataList;
    protected ItemClickListener mItemClickListener;

    protected BaseRecyclerViewAdapter(@NonNull Context context,
                                      ItemClickListener itemClickListener) {
        mInflater = LayoutInflater.from(context);
        mItemClickListener = itemClickListener;
        mDataList = new ArrayList<>();
    }

    public void add(List<place> itemList) {
        mDataList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void set(List<T> dataList) {
        List<T> clone = new ArrayList<>(dataList);
        mDataList.clear();
        mDataList.addAll((Collection<? extends place>) clone);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
