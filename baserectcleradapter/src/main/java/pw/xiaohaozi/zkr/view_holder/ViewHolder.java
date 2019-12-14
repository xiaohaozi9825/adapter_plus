package pw.xiaohaozi.zkr.view_holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.zkr.adapter.listener.OnClickListener;
import pw.xiaohaozi.zkr.adapter.listener.OnItemClickListener;


public class ViewHolder<VDB extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener {
    private VDB mBinding;
    OnItemClickListener<VDB> mOnItemClickListener;
    OnClickListener<VDB> mOnClickListener;
    //①将原来的形参 View itemView 改为 Binding
    public ViewHolder(@NonNull VDB binding) {
        //②super中需要返回itemView，所以需要传入Binding.getRoot()
        super(binding.getRoot());
        mBinding = binding;
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

     public void setOnItemClickListener(OnItemClickListener<VDB> itemClickListener) {
        mOnItemClickListener = itemClickListener;
         mBinding.getRoot().setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

}
