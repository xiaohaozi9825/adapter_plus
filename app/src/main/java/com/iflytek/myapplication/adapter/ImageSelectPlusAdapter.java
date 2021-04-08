package com.iflytek.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.iflytek.myapplication.R;
import com.iflytek.myapplication.bean.ImageUrlInfo;
import com.iflytek.myapplication.databinding.ItemSelectImageBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;


public class ImageSelectPlusAdapter extends SimpleAdapter<ItemSelectImageBinding, ImageUrlInfo> {
    private int mSpanCount;

    @Override
    protected <VG extends ViewGroup> ViewHolder<ItemSelectImageBinding> onCreateViewHolder(@NonNull VG vg, ItemSelectImageBinding itemSelectImageBinding, int viewType) {
        RecyclerView.LayoutManager layoutManager = ((RecyclerView) vg).getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            mSpanCount = gridLayoutManager.getSpanCount();
        }
        ViewHolder<ItemSelectImageBinding> viewHolder = super.onCreateViewHolder(vg, itemSelectImageBinding, viewType);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemSelectImageBinding.cardView.getLayoutParams();
        layoutParams.height = (vg.getWidth() - vg.getPaddingLeft() - vg.getPaddingRight()) / mSpanCount - layoutParams.leftMargin - layoutParams.rightMargin;
        itemSelectImageBinding.cardView.setLayoutParams(layoutParams);


        //指定由哪个控件触发选中事件，默认 binding.getRoot()
        viewHolder.setTrigger(itemSelectImageBinding.ivSelect);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<ItemSelectImageBinding> holder, int position, @NonNull ItemSelectImageBinding binding, @NonNull ImageUrlInfo data, int isSelect) {
        binding.setUrl(data.getUsl());
        if (isSelect >= 0) {
            binding.viewSelect.setVisibility(View.VISIBLE);
            binding.ivSelect.setImageResource(R.drawable.ic_select);
            binding.tvCheckIndex.setText("" + isSelect);
        } else {
            binding.viewSelect.setVisibility(View.INVISIBLE);
            binding.ivSelect.setImageResource(R.drawable.ic_no_select);
            binding.tvCheckIndex.setText("");
        }
    }


//    @Override
//    protected void onSelectChange(int position, boolean isSelect) {
//        if (isSelect) {
//            Log.i("选择", "onSelectChange: +++ " + position);
//        } else {
//            Log.i("选择", "onSelectChange: --- " + position);
//        }
//    }


}
