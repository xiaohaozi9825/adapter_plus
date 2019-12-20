package com.iflytek.myapplication.adapter;

import com.iflytek.myapplication.R;
import com.iflytek.myapplication.bean.GoodsBottomInfo;
import com.iflytek.myapplication.bean.GoodsCentreInfo;
import com.iflytek.myapplication.bean.GoodsTopInfo;
import com.iflytek.myapplication.databinding.ItemBottomBinding;
import com.iflytek.myapplication.databinding.ItemCentreBinding;
import com.iflytek.myapplication.databinding.ItemTopBinding;

import pw.xiaohaozi.zkr.adapter.MultiTypeAdapter;
import pw.xiaohaozi.zkr.holder.ViewHolder;

/**
 * 多类型viewType
 * 1、每种类型必须有对应的javabean，且必须实现RecyclerData接口，返回值可以不连续，但是必须不同；
 * 2、getLayoutRes(int viewType)必须返回对应的layout id；
 * 3、onBindViewHolder()方法中也必须将viewdatabinding这强转成对应的子类，数据也需要强转
 * 4、如果没有重写onCreateViewHolder()方法，则创建ViewHolder；
 * 5、注释部分是使用自定义的ViewHolder
 */
public class ShoppingTrolleyAdapter extends MultiTypeAdapter {
//    @Override
//    protected ViewHolder onCreateViewHolder(ViewDataBinding binding, int viewType) {
//        switch (viewType) {
//            case 0:
//                return new TopHolder((ItemTopBinding) binding);
//            case 1:
//                return new CentreHolder((ItemCentreBinding) binding);
//            default://2
//                return new BottomHolder((ItemBottomBinding) binding);
//        }
//    }

    /**
     * 绑定数据到View中
     * <p>
     * 可以使用getItemViewType(position)获取对应的viewType
     *
     * @param position
     * @param viewHolder
     */
    @Override
    protected void onBindViewHolder(int position, ViewHolder viewHolder) {
        switch (getItemViewType(position)) {
            case 0:
                ((ItemTopBinding) viewHolder.getBinding()).setGoodsTop((GoodsTopInfo) getDataList().get(position));
//                TopHolder topHolder = (TopHolder) viewHolder;
//                topHolder.getBinding().setGoodsTop((GoodsTopInfo) getDataList().get(position));
                break;
            case 1:
                ((ItemCentreBinding) viewHolder.getBinding()).setGoodsCenter((GoodsCentreInfo) getDataList().get(position));

//                CentreHolder centerHolder = (CentreHolder) viewHolder;
//                centerHolder.getBinding().setGoodsCenter((GoodsCentreInfo) getDataList().get(position));
                break;
            case 2:
                ((ItemBottomBinding) viewHolder.getBinding()).setGoodsBottom((GoodsBottomInfo) getDataList().get(position));

//                BottomHolder bottomHolder = (BottomHolder) viewHolder;
//                bottomHolder.getBinding().setGoodsBottom((GoodsBottomInfo) getDataList().get(position));
                break;
        }
    }

    /**
     * 获取每种viewType对应的layout文件
     *
     * @param viewType 即RecyclerData中get_RV_ItemViewType()的返回值
     * @return
     */
    @Override
    protected int getLayoutRes(int viewType) {
        switch (viewType) {
            case 0:
                return R.layout.item_top;
            case 1:
                return R.layout.item_centre;
            case 2:
                return R.layout.item_bottom;
        }
        return -1;
    }
}
