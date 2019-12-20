## 1、添加依赖库 ##
在app build.gradle文件中android下添加：

        
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
    dataBinding {
        enabled = true
    }
在dependencies 中添加依赖：
    
    implementation 'com.github.xiaohaozi9825:adapter_plus:1.4'
## 2、各功能模块使用及效果图 ##
### 1、最最简单的使用（item viewType只有1中） ###
创建一个适配器，并继承SimpleAdapter类，只需要实现onBindViewHolder()方法即可，简单粗暴大气；我们可以在onBindViewHolder()方法中绑定数据到View。
适用场景：在只需要简单绑定数据的情况均可使用
[详细用法](https://www.jianshu.com/p/ec7d302c1f1c)
![以好友列表为例](https://upload-images.jianshu.io/upload_images/18038561-4618a61bf26c0e25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2、1种viewType扩展 ###
创建一个适配器，并继承SingleTypeAdapter类，和SimpleAdapter类似，只需要重写onBindViewHolder()即可，与SimpleAdapter不同的是，SingleTypeAdapte可以实现拓展更多功能，比SimpleAdapter更加灵活，但SingleTypeAdapte用单反射，性能和稳定性没有SimpleAdapter好。
适用场景：只有一种item类型，复杂的RecyclerView使用
[详细用法](https://www.jianshu.com/p/6abdd7f72240)
![以好友列表为例](https://upload-images.jianshu.io/upload_images/18038561-2aabe4082f24cb70.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 3、多种viewType使用 ###
多种viewType用法相对复杂
创建一个适配器，并继承MultiTypeAdapter类，需要重写onBindViewHolder()于getLayoutRes()方法；
同时也需要将javaBean实现RecyclerData接口。
[详细用法](https://www.jianshu.com/p/fcfc7229244a)
![以购物车为例](https://upload-images.jianshu.io/upload_images/18038561-6a1e149586bca7de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 4、item选择器 ###
item选择器可以实现单选多选等功能
[详细用法](https://www.jianshu.com/p/93d715a553de)
![以图片选择器为例](https://upload-images.jianshu.io/upload_images/18038561-2a0c6dec205551a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
