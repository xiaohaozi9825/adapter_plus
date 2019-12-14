package pw.xiaohaozi.zkr.adapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.zkr.view_holder.ViewHolder;

public abstract class SimpleAdapter<VDB extends ViewDataBinding, D> extends BaseAdapter<VDB, D, ViewHolder<VDB>> {

    @Override
    protected ViewHolder<VDB> onCreateViewHolder(VDB binding,int viewType ) {
        return new ViewHolder<>(binding);
    }

    @Override
    protected int getLayoutRes(int viewType) {
        return getLayoutResource();
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
