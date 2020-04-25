package pw.xiaohaozi.adapter_plus.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.listener.OnItemLongClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnItemClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnLongClickListener;


public class ViewHolder<VDB extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private VDB mBinding;
    OnItemClickListener<VDB> mOnItemClickListener;
    OnClickListener<VDB> mOnClickListener;

    OnItemLongClickListener<VDB> mOnItemLongClickListener;
    OnLongClickListener<VDB> mOnLongClickListener;

    //①将原来的形参 View itemView 改为 Binding
    public ViewHolder(@NonNull VDB vdb) {
        //②super中需要返回itemView，所以需要传入Binding.getRoot()
        super(vdb.getRoot());
        mBinding = vdb;
    }

    public VDB getBinding() {
        return mBinding;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBinding.getRoot().getId()) {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(mBinding);
        } else {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(v, mBinding);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == mBinding.getRoot().getId()) {
            if (mOnItemLongClickListener != null)
                mOnItemLongClickListener.onItemLongClick(mBinding);
        } else {
            if (mOnLongClickListener != null)
                mOnLongClickListener.onLongClick(v, mBinding);
        }
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener<VDB> itemClickListener) {
        mOnItemClickListener = itemClickListener;
        mBinding.getRoot().setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener<VDB> onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<VDB> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
        mBinding.getRoot().setOnLongClickListener(this);
    }

    public void setOnLongClickListener(OnLongClickListener<VDB> onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }


}
