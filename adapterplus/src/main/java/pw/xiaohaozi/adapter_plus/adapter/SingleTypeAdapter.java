package pw.xiaohaozi.adapter_plus.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * 只有一种类型item
 * <p>
 * 该类在BaseAdapter基础上封装了getLayoutRes 方法，可以根据 VDB 的获取到layout id。
 * <p>
 * 适用场景：
 * 1、viewType（如果是多viewType需要使用父类BaseAdapter）；
 * 2、需要重写ViewHolder中一些方法（如果不需要重写ViewHolder，推荐使用SingleAdapter）
 *
 * @param <VDB>
 * @param <D>
 * @param <VH>
 */
public abstract class SingleTypeAdapter<VDB extends ViewDataBinding, D, VH extends ViewHolder<VDB>> extends BaseAdapter<VDB, D, VH> {

    /**
     * 当viewType只有一种类型时，我们是可以直接确定layout id的值的
     *
     * @param viewType
     * @return
     */
    @Override
    final protected int getLayoutRes(int viewType) {
        return getLayoutResource();
    }


    @Override
    protected VH onCreateViewHolder(@NonNull RecyclerView recyclerView, VDB binding, int viewType) {
        // FIXME: 2019/12/18 0018 这里使用泛型实例化ViewHolder，目前测试ok，不保证以后不会出现问题
        try {
            Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            Class<VDB> VDB_Class = (Class<VDB>) types[0];
            Type type2 = types[2];
            Constructor<?> constructor;
            if (types.length >= 3 && type2 instanceof Class) {
                Class VH_Class = (Class) type2;
                constructor = VH_Class.getConstructor(VDB_Class);
            } else {
                String class_name = type2.toString().substring(0, type2.toString().lastIndexOf("<"));
                constructor = Class.forName(class_name).getDeclaredConstructor(ViewDataBinding.class);
            }
            return (VH) constructor.newInstance(binding);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("泛型指定错误,ViewHolder实例化失败");
    }

    private int getLayoutResource() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType))
            throw new RuntimeException("SimpleAdapter实例创建时必须指定泛型 VDB");//如果没有制定泛型
        Class<VDB> _class = (Class<VDB>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];//获取泛型对应的类
        String simpleName = _class.getSimpleName();//获取泛型类名
        if ("ViewDataBinding".equals(simpleName))
            throw new RuntimeException("泛型VDB 不能是ViewDataBinding，必须是ViewDataBinding的子类");
        String hump = simpleName.substring(0, simpleName.length() - 7);//去掉类名中“Binding”字段
        // String res = String.join("_", hump.replaceAll("([A-Z])", ",$1").split(",")).toLowerCase();//低版本不支持
        //将驼峰试命名转成下划线小写，得到layout对应名称，最后获取对应的layout id
        return mContext.getResources().getIdentifier(humpToLine(hump), "layout", mContext.getPackageName());
    }


    /**
     * 驼峰转下划线（第一个不加下划线）
     *
     * @param str
     * @return
     */
    private String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {//第一个不加下划线
            matcher.appendReplacement(sb, matcher.group(0).toLowerCase());
        }
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
