package com.xunlei.framework.common.util;

import com.xunlei.framework.common.dto.SortFast;
import com.xunlei.framework.common.dto.SortPolicy;
import com.xunlei.framework.common.enums.SortType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对象排序工具类
 */
public class SortUtils {


    /**
     * 快速排序方法，基于接口实现，对实体类没有侵入，对于高频接口推荐使用这个
     * <note><b> 推荐使用 </b></note>
     *
     * @param srcList  源数据
     * @param sortFast 排序的策略接口
     */
    public static <T> void sortFast(List<T> srcList, final SortFast<T> sortFast) {
        if (ListUtils.isEmpty(srcList) || ListUtils.isEmpty(srcList)) {
            return;
        }
        // 排序策略
        final String[] fields = sortFast.sortField();
        final SortType[] dirs = sortFast.sortType();
        // 排序接口定义
        final Comparator<T> fieldsComparator = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                for (int i = 0; i < fields.length; i++) {
                    String fieldName = fields[i];
                    SortType st = dirs[i];
                    Object v1 = sortFast.sortValue(fieldName, o1);
                    Object v2 = sortFast.sortValue(fieldName, o2);
                    // 倒序的话，调换两个值的顺序
                    if (st == SortType.Descending) {
                        Object tmp = v1;
                        v1 = v2;
                        v2 = tmp;
                    }
                    // 比较两个值
                    int compareInt = compareObject(v1, v2);
                    // 不相等就返回
                    if (compareInt != 0) {
                        return compareInt;
                    }
                }
                return 0;
            }
        };
        Collections.sort(srcList, fieldsComparator);
    }

    /**
     * 对象排序(对象必须实现Comparable接口)
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        Collections.sort(list);
    }

    /**
     * 对象排序，基于反射实现，仅限频次不高的接口使用，好处是对实体类没有要求。
     *
     * @param list
     * @param fieldName 需要排序的字段名称
     * @param sortType  排序方式
     * @param <T>
     */
    public static <T> void sort(List<T> list, String fieldName, SortType sortType) {
        SortUtils.sort(list, new SortPolicy(fieldName, sortType));
    }

    /**
     * 多字段对象排序，也是基于反射实现，仅限频次不高的接口使用
     *
     * @param list
     * @param sortPolicies 排序策略
     * @param <T>
     */
    public static <T> void sort(List<T> list, final SortPolicy... sortPolicies) {
        if (ListUtils.isEmpty(list) || sortPolicies.length == 0) {
            return;
        }
        Class clazz = list.get(0).getClass();
        final Map<String, Method> result = SortUtils.getSortMethod(clazz, sortPolicies);
        // 排序接口定义
        Comparator<T> fieldsComparator = new Comparator<T>() {

            @Override
            public int compare(T o1, T o2) {
                Object v1;
                Object v2;
                int value;
                for (SortPolicy policy : sortPolicies) {
                    v1 = SortUtils.getSortValue(o1, result.get(policy.getFieldName()));
                    v2 = SortUtils.getSortValue(o2, result.get(policy.getFieldName()));
                    if (SortType.Ascending == policy.getSortType()) {
                        value = SortUtils.compareObject(v1, v2);
                    } else {
                        value = SortUtils.compareObject(v2, v1);
                    }
                    if (value != 0) {
                        return value;
                    }
                }
                return 0;
            }
        };
        Collections.sort(list, fieldsComparator);
    }

    private static Map<String, Method> getSortMethod(Class clazz, SortPolicy... sortPolicies) {
        Map<String, Method> result = new HashMap<>();
        String methodName;
        Method method;
        try {
            for (SortPolicy policy : sortPolicies) {
                methodName = ReflectUtils.getGetterMethodName(policy.getFieldName());
                method = clazz.getMethod(methodName, new Class[]{});
                result.put(policy.getFieldName(), method);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Object getSortValue(Object object, Method method) {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int compareObject(Object v1, Object v2) {
        if (v1 == null || v2 == null) {
            if (v1 == null && v2 == null) {
                return 0;
            }
            if (v1 == null || v2 != null) {
                return -1;
            }
            return 1;
        }
        if (v1 instanceof Comparable) {
            return ((Comparable) v1).compareTo(v2);
        }
        throw new IllegalArgumentException("属性值的" + v1 + "类型没有实现Comparable接口");
    }
}
