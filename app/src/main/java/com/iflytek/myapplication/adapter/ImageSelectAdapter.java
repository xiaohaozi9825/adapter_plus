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
    protected <VG extends ViewGroup> SelectHolder<ItemSelectImageBinding>
    onCreateViewHolder(@NonNull VG vg, ItemSelectImageBinding vdb, int viewType) {
        SelectHolder<ItemSelectImageBinding> viewHolder = super.onCreateViewHolder(vg, vdb, viewType);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) vdb.cardView.getLayoutParams();
        layoutParams.height = (vg.getWidth() - vg.getPaddingLeft() - vg.getPaddingRight()) / mSpanCount;
        vdb.cardView.setLayoutParams(layoutParams);


        //指定由哪个控件触发选中事件，默认 binding.getRoot()
        viewHolder.setTrigger(vdb.ivSelect);
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
    protected void onBindViewHolder(SelectHolder<ItemSelectImageBinding> itemSelectImageBindingSelectHolder,
                                    int position, ItemSelectImageBinding itemSelectImageBinding,
                                    String s, boolean isSelect) {
        itemSelectImageBinding.setUrl(s);
        if (isSelect) {
            itemSelectImageBinding.viewSelect.setVisibility(View.VISIBLE);
            itemSelectImageBinding.ivSelect.setImageResource(R.drawable.ic_select);
        } else {
            itemSelectImageBinding.viewSelect.setVisibility(View.INVISIBLE);
            itemSelectImageBinding.ivSelect.setImageResource(R.drawable.ic_no_select);
        }
    }

}
