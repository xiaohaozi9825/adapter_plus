package com.iflytek.myapplication.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.iflytek.myapplication.R;
import com.iflytek.myapplication.databinding.ItemSelectImageBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.adapter.SelectAdapter;
import pw.xiaohaozi.adapter_plus.holder.SelectHolder;


public class ImageSelectAdapter extends SelectAdapter<ItemSelectImageBinding, String, SelectHolder<ItemSelectImageBinding>> {
    private int mSpanCount;

    public ImageSelectAdapter(int spanCount) {
        mSpanCount = spanCount;
    }


    @Override
    protected <VG extends ViewGroup> SelectHolder<ItemSelectImageBinding> onCreateViewHolder(@NonNull VG parent, ItemSelectImageBinding binding, int viewType) {
        SelectHolder<ItemSelectImageBinding> viewHolder = super.onCreateViewHolder(parent, binding, viewType);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) binding.cardView.getLayoutParams();
        layoutParams.height = (parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight()) / mSpanCount;
        binding.cardView.setLayoutParams(layoutParams);

        //指定由哪个控件触发选中事件，默认 binding.getRoot()
        viewHolder.setTrigger(binding.ivSelect);
        return viewHolder;
    }

    @Override
    protected void onSelectChange(int position, boolean isSelect) {
        if (isSelect) {
            Log.i("选择", "onSelectChange: +++ " + position);
        } else {
            Log.i("选择", "onSelectChange: --- " + position);
        }
    }

    @Override
    protected void onBindViewHolder(SelectHolder<ItemSelectImageBinding> viewHolder, int position, boolean isSelect) {
        ItemSelectImageBinding binding = viewHolder.getBinding();
        binding.setUrl(getDataList().get(position));
        if (isSelect) {
            binding.viewSelect.setVisibility(View.VISIBLE);
            binding.ivSelect.setImageResource(R.drawable.ic_select);
        } else {
            binding.viewSelect.setVisibility(View.INVISIBLE);
            binding.ivSelect.setImageResource(R.drawable.ic_no_select);
        }
    }
}
