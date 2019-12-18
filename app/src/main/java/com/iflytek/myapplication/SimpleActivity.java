package com.iflytek.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.iflytek.myapplication.bean.FriendInfo;

public class SimpleActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mFriendAdapter;
    private ObservableArrayList<FriendInfo> mFriendInfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false));
        mFriendAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mFriendAdapter);
//        mFriendAdapter.setAutoRemove(false, msg -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
//        mFriendAdapter.setMaxSelectSize(5);
        mFriendAdapter.setNoCancel(true);
        mFriendAdapter.addSelectItem(2);

        mFriendAdapter.setOnItemClickListener(binding ->
                Toast.makeText(this, "您点击了：" + binding.getFriendInfo().getName(),
                        Toast.LENGTH_SHORT).show()
        );
        mFriendAdapter.setOnItemLongClickListener(itemFriendBinding -> {
            Toast.makeText(this, "您长按了：" + itemFriendBinding.getFriendInfo().getName(),
                    Toast.LENGTH_SHORT).show();
            mFriendAdapter.cancelSelectItem(2);
        });
    }

    private void initData() {
        //这里需要模拟一些数据
        mFriendInfos = new ObservableArrayList<>();
        mFriendInfos.add(new FriendInfo("马云", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322838876&di=4352cdbb664326b23ec43d538e019a79&imgtype=0&src=http%3A%2F%2Ffile.qiyejia.info%2Fspider%2F2017%2F01%2F07%2F2017010710145432995.jpg", "我对钱没兴趣"));
        mFriendInfos.add(new FriendInfo("王健林", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322891556&di=662826fbc3a87917c769246e25fc2711&imgtype=0&src=http%3A%2F%2Fimage.thepaper.cn%2Fwap%2Fimage%2F4%2F375%2F847.jpg", "但是最好先定一个能达到的小目标。比如我先挣它一个亿。"));
        mFriendInfos.add(new FriendInfo("刘亦菲", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323047699&di=db4f4fe3999304d994a56677dab69667&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201811%2F18%2F20181118231735_qopfn.jpg", "我是家里最丑的"));
        mFriendInfos.add(new FriendInfo("鹿晗", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323085523&di=5f58e7e949e50b908db38cbea2b54461&imgtype=0&src=http%3A%2F%2Fimage.uczzd.cn%2F15952447920881452289.jpg", "讨厌，能不能别叫我小姐姐"));
        mFriendInfos.add(new FriendInfo("李宇春", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323118985&di=525535d0dbcf5ca0cfc6030ec477a1a5&imgtype=0&src=http%3A%2F%2Ft-1.tuzhan.com%2F1c4ffe967c62%2Fc-1%2Fl%2F2012%2F10%2F08%2F17%2F66c40456e9474af9b5b6a1747eca02b1.jpg", "麻烦不要叫我春哥"));
//
//        mFriendInfos.add(new FriendInfo("马云", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322838876&di=4352cdbb664326b23ec43d538e019a79&imgtype=0&src=http%3A%2F%2Ffile.qiyejia.info%2Fspider%2F2017%2F01%2F07%2F2017010710145432995.jpg", "我对钱没兴趣"));
//        mFriendInfos.add(new FriendInfo("王健林", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322891556&di=662826fbc3a87917c769246e25fc2711&imgtype=0&src=http%3A%2F%2Fimage.thepaper.cn%2Fwap%2Fimage%2F4%2F375%2F847.jpg", "但是最好先定一个能达到的小目标。比如我先挣它一个亿。"));
//        mFriendInfos.add(new FriendInfo("刘亦菲", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323047699&di=db4f4fe3999304d994a56677dab69667&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201811%2F18%2F20181118231735_qopfn.jpg", "我是家里最丑的"));
//        mFriendInfos.add(new FriendInfo("鹿晗", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323085523&di=5f58e7e949e50b908db38cbea2b54461&imgtype=0&src=http%3A%2F%2Fimage.uczzd.cn%2F15952447920881452289.jpg", "讨厌，能不能别叫我小姐姐"));
//        mFriendInfos.add(new FriendInfo("李宇春", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323118985&di=525535d0dbcf5ca0cfc6030ec477a1a5&imgtype=0&src=http%3A%2F%2Ft-1.tuzhan.com%2F1c4ffe967c62%2Fc-1%2Fl%2F2012%2F10%2F08%2F17%2F66c40456e9474af9b5b6a1747eca02b1.jpg", "麻烦不要叫我春哥"));
//
//        mFriendInfos.add(new FriendInfo("马云", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322838876&di=4352cdbb664326b23ec43d538e019a79&imgtype=0&src=http%3A%2F%2Ffile.qiyejia.info%2Fspider%2F2017%2F01%2F07%2F2017010710145432995.jpg", "我对钱没兴趣"));
//        mFriendInfos.add(new FriendInfo("王健林", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322891556&di=662826fbc3a87917c769246e25fc2711&imgtype=0&src=http%3A%2F%2Fimage.thepaper.cn%2Fwap%2Fimage%2F4%2F375%2F847.jpg", "但是最好先定一个能达到的小目标。比如我先挣它一个亿。"));
//        mFriendInfos.add(new FriendInfo("刘亦菲", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323047699&di=db4f4fe3999304d994a56677dab69667&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201811%2F18%2F20181118231735_qopfn.jpg", "我是家里最丑的"));
//        mFriendInfos.add(new FriendInfo("鹿晗", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323085523&di=5f58e7e949e50b908db38cbea2b54461&imgtype=0&src=http%3A%2F%2Fimage.uczzd.cn%2F15952447920881452289.jpg", "讨厌，能不能别叫我小姐姐"));
//        mFriendInfos.add(new FriendInfo("李宇春", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323118985&di=525535d0dbcf5ca0cfc6030ec477a1a5&imgtype=0&src=http%3A%2F%2Ft-1.tuzhan.com%2F1c4ffe967c62%2Fc-1%2Fl%2F2012%2F10%2F08%2F17%2F66c40456e9474af9b5b6a1747eca02b1.jpg", "麻烦不要叫我春哥"));
//
//        mFriendInfos.add(new FriendInfo("马云", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322838876&di=4352cdbb664326b23ec43d538e019a79&imgtype=0&src=http%3A%2F%2Ffile.qiyejia.info%2Fspider%2F2017%2F01%2F07%2F2017010710145432995.jpg", "我对钱没兴趣"));
//        mFriendInfos.add(new FriendInfo("王健林", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322891556&di=662826fbc3a87917c769246e25fc2711&imgtype=0&src=http%3A%2F%2Fimage.thepaper.cn%2Fwap%2Fimage%2F4%2F375%2F847.jpg", "但是最好先定一个能达到的小目标。比如我先挣它一个亿。"));
//        mFriendInfos.add(new FriendInfo("刘亦菲", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323047699&di=db4f4fe3999304d994a56677dab69667&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201811%2F18%2F20181118231735_qopfn.jpg", "我是家里最丑的"));
//        mFriendInfos.add(new FriendInfo("鹿晗", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323085523&di=5f58e7e949e50b908db38cbea2b54461&imgtype=0&src=http%3A%2F%2Fimage.uczzd.cn%2F15952447920881452289.jpg", "讨厌，能不能别叫我小姐姐"));
//        mFriendInfos.add(new FriendInfo("李宇春", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576323118985&di=525535d0dbcf5ca0cfc6030ec477a1a5&imgtype=0&src=http%3A%2F%2Ft-1.tuzhan.com%2F1c4ffe967c62%2Fc-1%2Fl%2F2012%2F10%2F08%2F17%2F66c40456e9474af9b5b6a1747eca02b1.jpg", "麻烦不要叫我春哥"));


        mFriendAdapter.refresh(mFriendInfos);
        mFriendAdapter.addSelectItem(2);
        mFriendAdapter.add(mFriendInfos);
    }


}
