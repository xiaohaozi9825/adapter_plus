package pw.xiaohaozi.adapter_plus.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public class SelectHolderPlus<VDB extends ViewDataBinding> extends ViewHolder<VDB> {
    private OnSelectChangeListener mOnSelectChangeListener;
    private View mTriggerView;
    private VDB mVDB;

    public SelectHolderPlus(@NonNull VDB binding) {
        super(binding);
        mVDB = binding;
        setTrigger(binding.getRoot());
    }

    /**
     * 设置触发器
     *
     * @param view 指定由该view触发选中事件
     */
    public void setTrigger(View view) {
        if (mTriggerView != null) mTriggerView.setOnClickListener(null);
        mTriggerView = view;
        view.setOnClickListener(this);
    }

    public void setOnSelectChangeListener(OnSelectChangeListener<VDB> onSelectChangeListener) {
        mOnSelectChangeListener = onSelectChangeListener;
    }

    @Override
    public void onClick(View v) {
        if (mTriggerView != null && v.getId() == mTriggerView.getId()) {
            Integer selectPosition = getLayoutPosition();
            mOnSelectChangeListener.change(this, mVDB);
        } else {
            super.onClick(v);
        }
    }

    public interface OnSelectChangeListener<VDB> {
        void change(SelectHolderPlus selectHolder, VDB vdb);
    }
}
