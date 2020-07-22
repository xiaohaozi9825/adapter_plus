package com.iflytek.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.iflytek.myapplication.R;
import com.iflytek.myapplication.bean.ImageUrlInfo;
import com.iflytek.myapplication.databinding.ItemSelectImageBinding;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.adapter.SelectPlusAdapter;
import pw.xiaohaozi.adapter_plus.holder.SelectHolder;


public class ImageSelectPlusAdapter extends SelectPlusAdapter<ItemSelectImageBinding, ImageUrlInfo, SelectHolder<ItemSelectImageBinding>> {
    private int mSpanCount;

    public ImageSelectPlusAdapter(LinkedList<ImageUrlInfo> selecteds) {
        super(selecteds);
    }

    @Override
    protected <VG extends ViewGroup> SelectHolder<ItemSelectImageBinding>
    onCreateViewHolder(@NonNull VG vg, ItemSelectImageBinding vdb, int viewType) {
        RecyclerView.LayoutManager layoutManager = ((RecyclerView) vg).getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            mSpanCount = gridLayoutManager.getSpanCount();
        }
        SelectHolder<ItemSelectImageBinding> viewHolder = super.onCreateViewHolder(vg, vdb, viewType);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) vdb.cardView.getLayoutParams();
        layoutParams.height = (vg.getWidth() - vg.getPaddingLeft() - vg.getPaddingRight()) / mSpanCount - layoutParams.leftMargin - layoutParams.rightMargin;
        vdb.cardView.setLayoutParams(layoutParams);



        //指定由哪个控件触发选中事件，默认 binding.getRoot()
        viewHolder.setTrigger(vdb.ivSelect);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(SelectHolder<ItemSelectImageBinding> itemSelectImageBindingSelectHolder,
                                    int position, ItemSelectImageBinding itemSelectImageBinding,
                                    ImageUrlInfo imageUrlInfo, boolean isSelect) {
        itemSelectImageBinding.setUrl(imageUrlInfo.getUsl());
        if (isSelect) {
            itemSelectImageBinding.viewSelect.setVisibility(View.VISIBLE);
            itemSelectImageBinding.ivSelect.setImageResource(R.drawable.ic_select);
        } else {
            itemSelectImageBinding.viewSelect.setVisibility(View.INVISIBLE);
            itemSelectImageBinding.ivSelect.setImageResource(R.drawable.ic_no_select);
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
