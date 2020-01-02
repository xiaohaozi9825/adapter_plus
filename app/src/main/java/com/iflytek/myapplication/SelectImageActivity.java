package com.iflytek.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import com.iflytek.myapplication.adapter.ImageSelectAdapter;
import com.iflytek.myapplication.databinding.ItemSelectImageBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectImageActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageSelectAdapter mImageSelectAdapter;
    private ObservableArrayList<String> mObservableArrayList;
    final private static int SPAN_COUNT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT, RecyclerView.VERTICAL, false));
        mRecyclerView.post(() -> {
            //如果想要正方形，还需要减去cardView的margin值
            int itemHeight = mRecyclerView.getMeasuredWidth() / SPAN_COUNT;

            mImageSelectAdapter = new ImageSelectAdapter(itemHeight);
            mImageSelectAdapter.setOnItemClickListener(itemSelectImageBinding -> {
                Toast.makeText(getApplicationContext(), "点击查看大图", Toast.LENGTH_SHORT).show();
            });
            mRecyclerView.setAdapter(mImageSelectAdapter);
            initData();
        });

    }

    private void initData() {
        mObservableArrayList = new ObservableArrayList<>();
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576754910095&di=337892aa5be671ab368ee12e7bbd5251&imgtype=0&src=http%3A%2F%2Fhiphotos.baidu.com%2Fimage%2F%2577%3D%2536%2534%2530%3B%2563%2572%256F%2570%3D%2530%2C%2534%2536%2C%2536%2534%2530%2C%2533%2535%2535%2Fsign%3Df4ce751aa3014c08193b2ba13a40617a%2Fa50f4bfbfbedab641ea5b573fd36afc378311ea9.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576754910095&di=5435efceba93fe334b4afea38f7beafc&imgtype=0&src=http%3A%2F%2Fpicture.ik123.com%2Fuploads%2Fallimg%2F180207%2F4-1P20G44617.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576754910093&di=adf32f27116ade47b5952fce76f91e02&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201407%2F16%2F20140716155024_MJvEQ.thumb.700_0.jpeg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576754910093&di=86513880e64573f384397b7d6e8d5be1&imgtype=0&src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F07%2F16%2F146863696259973413.JPEG");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576754987668&di=020efac372d5c3939600403a153e21f2&imgtype=0&src=http%3A%2F%2Fimage20.it168.com%2F201008_500x375%2F173%2F3db2813262288a81.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755017353&di=9069408ed8db167302ae820bf8c75a28&imgtype=jpg&src=http%3A%2F%2Fwx4.sinaimg.cn%2Flarge%2F72cbf277ly1fuphaq90w5j20dw08p3yp.jpg");
        mObservableArrayList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3353220413,1630497550&fm=26&gp=0.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755046701&di=3d58241337fa0da0d841892fe8896ec6&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2Fd%2Fcf%2F097a1569110.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755046701&di=a98d5221a872acd0c05a4356286a3bb1&imgtype=0&src=http%3A%2F%2Fdingyue.nosdn.127.net%2FhIHcZgZVsrffw0R3eiXBGgBqO9VN10tDGT2RtNFLjcmJ91536651455763compressflag.jpg");
        mObservableArrayList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=359181547,104620177&fm=26&gp=0.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755046700&di=9bc4f8bcd019262fbfb91f30fd1ed443&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2Fd%2Fcf%2F097a1569103.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755046700&di=33cdc5a745cce14a6009caa00ff0424a&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-11-08%2F5a027aae54b2c.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755046700&di=19a0b421f6726edca0c6b2c17c268705&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-10-25%2F59f0002830024.jpg");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755046700&di=ed7e4698c2b1f0a463afa6f29f0559a4&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-11-03%2F59fbd1a6aff99.jpg%3Fdown");
        mObservableArrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576755046700&di=a4ded98376fb0951c7b0678699074431&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Ff80402fee8e95d7fe67aab0a61080eb010061ac81b911-RhDKia_fw658");

        mImageSelectAdapter.refresh(mObservableArrayList);
    }
}
