# 使用方法 #
##1、添加依赖库 ##
在app build.gradle文件中android下添加：
        
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }

在dependencies 中添加依赖：
    
    implementation 'com.github.xiaohaozi9825:RecyclerAdapterDatabinding:V1.1'

##2、简单使用##

    
    /**
     * 创建一个类并继承SimpleAdapter；
     * ItemGoodsBinding： 是layout/item_goods.xml 对应的databinding类
     * GoodsInfo：是对应的数据
     */
    public class GoodsAdapter extends SimpleAdapter<ItemGoodsBinding, GoodsInfo> {
        @Override
        protected void onBindViewHolder(int position, ViewHolder<ItemGoodsBinding> viewHolder) {
            GoodsInfo goodsInfo = getDataList().get(position);//获取到数据
            viewHolder.getBinding().setGoodsInfo(goodsInfo);//将数据设置到View中
        }
    }

当然，如果你觉得麻烦，可以直接创建一个内部类也行，代码就这么几行而已。
SimpleAdapter类中已经封装好了item点击事件，可以直接使用：
            
        GoodsAdapter goodsAdapter = new GoodsAdapter();
        goodsAdapter.setOnItemClickListener(binding -> {
            
        });

##3、自定义##
如果SimpleAdapter不能满足您的需求，可以自定义adapter实现更多功能：
###1、创建一个类，并继承ViewHolder<VDB>###
    
    public class GoodsHolder extends ViewHolder<ItemGoodsBinding> {
        public GoodsHolder(@NonNull ItemGoodsBinding binding) {
            super(binding);
        }
    }
如果该中没有需要修改的地方，可以不定义，直接使用 父类ViewHolder<VDB>也是可以的
###2、创建一个类，并继承BaseAdapter###

/**
 * ItemGoodsBinding 对应的layout/item_goods.xml文件
 * GoodsInfo 对应数据
 * ViewHolder<ItemGoodsBinding> 您刚创建的ViewHolder
 */
public class GoodsAdapter extends BaseAdapter<ItemGoodsBinding, GoodsInfo, GoodsHolder> {

    @Override
    protected GoodsHolder onCreateViewHolder(ItemGoodsBinding binding, int viewType) {
        //创建ViewHolder并返回
        return new GoodsHolder(binding);
    }

    @Override
    protected void onBindViewHolder(int position, GoodsHolder viewHolder) {
        //绑定数据到View
    }

    @Override
    protected int getLayoutRes(int viewType) {
        //这里需要返回对应的layout，注意要与第一个泛型对应
        return R.layout.item_goods;
    }
}


##4、多类型##
