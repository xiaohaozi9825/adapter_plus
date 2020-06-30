# RecyclerView中adapter与DataBinding封装 #
> 博客地址：https://www.jianshu.com/u/2a2ea7b43087
## 1、添加依赖库 ##
[![](https://jitpack.io/v/xiaohaozi9825/adapter_plus.svg)](https://jitpack.io/#xiaohaozi9825/adapter_plus)


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
    
    implementation 'com.github.xiaohaozi9825:adapter_plus:1.9'
    
    
混淆配置：

    #adapter_plus需要配置
    #-keep class 你项目包名.databinding.**{ *; }，如我项目包名为com.iflytek.queuingmachine
    -keep class com.iflytek.queuingmachine.databinding.**{ *; }
    
如果出现jar包重复问题，可以将修implementation改成compileOnly
## 2、各功能模块使用及效果图 ##
### 1、最最简单的使用（item viewType只有1中） ###
创建一个适配器，并继承SimpleAdapter类，只需要实现onBindViewHolder()方法即可，简单粗暴大气；我们可以在onBindViewHolder()方法中绑定数据到View。
适用场景：在只需要简单绑定数据的情况均可使用<br/>
[详细用法](https://www.jianshu.com/p/ec7d302c1f1c)<br/>
![以好友列表为例](https://github.com/xiaohaozi9825/adapter_plus/blob/master/images/%E6%95%88%E6%9E%9C%E5%9B%BE1%EF%BC%88%E4%BB%A5%E5%A5%BD%E5%8F%8B%E5%88%97%E8%A1%A8%E4%B8%BA%E4%BE%8B%EF%BC%89.png)

### 2、1种viewType扩展 ###
创建一个适配器，并继承SingleTypeAdapter类，和SimpleAdapter类似，只需要重写onBindViewHolder()即可，与SimpleAdapter不同的是，SingleTypeAdapte可以实现拓展更多功能，比SimpleAdapter更加灵活，但SingleTypeAdapte用单反射，性能和稳定性没有SimpleAdapter好。<br/>
适用场景：只有一种item类型，复杂的RecyclerView使用<br/>
[详细用法](https://www.jianshu.com/p/6abdd7f72240)<br/>
![以好友列表为例](https://github.com/xiaohaozi9825/adapter_plus/blob/master/images/%E6%95%88%E6%9E%9C%E5%9B%BE2%EF%BC%88%E4%BB%A5%E5%A5%BD%E5%8F%8B%E5%88%97%E8%A1%A8%E4%B8%BA%E4%BE%8B%EF%BC%89.png)

### 3、多种viewType使用 ###
多种viewType用法相对复杂<br/>
创建一个适配器，并继承MultiTypeAdapter类，需要重写onBindViewHolder()于getLayoutRes()方法；<br/>
同时也需要将javaBean实现RecyclerData接口。<br/>
[详细用法](https://www.jianshu.com/p/fcfc7229244a)<br/>
![以购物车为例](https://github.com/xiaohaozi9825/adapter_plus/blob/master/images/%E6%95%88%E6%9E%9C%E5%9B%BE3%EF%BC%88%E4%BB%A5%E8%B4%AD%E7%89%A9%E8%BD%A6%E4%B8%BA%E4%BE%8B%EF%BC%89.png)

### 4、item选择器 ###
item选择器可以实现单选多选等功能<br/>
[详细用法](https://www.jianshu.com/p/93d715a553de)<br/>
![以图片选择器为例](https://github.com/xiaohaozi9825/adapter_plus/blob/master/images/%E6%95%88%E6%9E%9C%E5%9B%BE4%EF%BC%88%E4%BB%A5%E5%9B%BE%E7%89%87%E9%80%89%E6%8B%A9%E5%99%A8%E4%B8%BA%E4%BE%8B%EF%BC%89.png)
