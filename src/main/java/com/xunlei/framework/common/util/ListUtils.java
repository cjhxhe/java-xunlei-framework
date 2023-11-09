/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.common.util;

import com.xunlei.framework.common.dto.ElementDTO;
import com.xunlei.framework.common.interfaces.Grouping;
import com.xunlei.framework.common.interfaces.GroupingKey;
import com.xunlei.framework.common.interfaces.PropertyFilter;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * 集合工具类
 */
public class ListUtils extends org.apache.commons.collections.ListUtils {

    /**
     * 分组函数，可根据规定的分组主键进行分组, 需要实现<code>GroupingKey</code> 接口
     *
     * @param src
     * @return
     * @see GroupingKey
     */
    public static <K, T extends GroupingKey<K>> Map<K, List<T>> grouping(List<T> src) {
        Map<K, List<T>> map = new LinkedHashMap<K, List<T>>();
        if (src == null || src.size() == 0) {
            return map;
        }
        for (T t : src) {
            K _gk = t.getGroupingKey();
            if (!map.containsKey(_gk)) {
                map.put(_gk, new ArrayList<T>());
            }

            map.get(_gk).add(t);
        }

        return map;
    }

    /**
     * 给传入的集合分组，分组属性由传入的接口参数返回
     *
     * @return
     */
    public static <K, T> Map<K, List<T>> grouping(List<T> src, Grouping<K, T> group) {
        Map<K, List<T>> map = new LinkedHashMap<K, List<T>>();
        if (src == null || src.size() == 0) {
            return map;
        }
        for (T t : src) {
            K _gk = group.getGroupingKey(t);
            if (!map.containsKey(_gk)) {
                map.put(_gk, new ArrayList<T>());
            }

            map.get(_gk).add(t);
        }

        return map;
    }

    /**
     * 将不同元素的集合按主键值转换成Map返回,
     * list元素应保证唯一性，否则前面的会被覆盖
     *
     * @return
     */
    public static <K, T> Map<K, T> groupingToEntry(List<T> src, Grouping<K, T> group) {
        Map<K, T> map = new LinkedHashMap<>();
        if (src == null || src.size() == 0) {
            return map;
        }
        for (T t : src) {
            K k = group.getGroupingKey(t);
            map.put(k, t);
        }

        return map;
    }

    /**
     * 获取第一个值
     *
     * @param list
     * @return
     */
    public static <T> T getFirstElement(List<T> list) {
        if (list != null && list.size() >= 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 属性过滤器
     *
     * @param dataList 数据源
     * @param pf       过滤器
     */
    public static <T> List<T> filterBy(List<T> dataList, PropertyFilter<T> pf) {
        for (Iterator<T> iterator = dataList.iterator(); iterator.hasNext(); ) {
            if (pf.isFilter(iterator.next())) {
                iterator.remove();
            }
        }
        return dataList;
    }

    /**
     * 判断集合是否为空（==null || isEmpty）
     *
     * @param list 数据源
     */
    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断集合是否非空（!=null || isEmpty）
     *
     * @param list 数据源
     */
    public static <T> boolean isNotEmpty(Collection<T> list) {
        return !isEmpty(list);
    }

    /**
     * 截取集合
     *
     * @param list
     * @param offset 从0开始
     * @param limit  需要截取的个数 -1表示返回所有数据
     * @param <T>
     * @return
     */
    public static <T> List<T> subList(List<T> list, int offset, int limit) {
        int amount = list.size();
        if (amount == 0) {
            return list;
        }
        if (offset > amount - 1) {
            return Collections.emptyList();
        }
        if (offset < 0) {
            offset = 0;
        }
        int toIndex = amount;
        if (limit != -1) {
            toIndex = Math.min(offset + limit, amount);
        }
        return list.subList(offset, toIndex);
    }

    /**
     * 一次性裁剪所有的数据
     *
     * @param src
     * @param pageLimit 每页条数
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> subLists(List<T> src, int pageLimit) {
        if (ListUtils.isEmpty(src)) {
            return Collections.emptyList();
        }

        List<List<T>> targets = new ArrayList<>();
        int pageCount = src.size() / pageLimit + (src.size() % pageLimit > 0 ? 1 : 0);
        for (int i = 0; i < pageCount; i++) {
            int fromIndex = i * pageLimit, endIndex = fromIndex + pageLimit;
            if (i == pageCount - 1) {
                endIndex = src.size();
            }
            // subList
            targets.add(src.subList(fromIndex, endIndex));
        }

        return targets;
    }

    /**
     * 将集合转成数组
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T[] toArray(Collection<T> list) {
        T[] values = (T[]) new Object[list.size()];
        int index = 0;
        for (T value : list) {
            values[index++] = value;
        }
        return values;
    }

    /**
     * 将集合转成字符串数组
     *
     * @param list
     * @return
     */
    public static String[] toStringArray(Collection<?> list) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return new String[]{};
        }
        String[] values = new String[list.size()];
        int index = 0;
        for (Object object : list) {
            values[index++] = object == null ? null : object.toString();
        }
        return values;
    }

    /**
     * 将集合转成字Long数组
     *
     * @param list
     * @return
     */
    public static Long[] toLongArray(Collection<?> list) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return new Long[]{};
        }
        Long[] values = new Long[list.size()];
        int index = 0;
        for (Object object : list) {
            values[index++] = object == null ? null : Long.valueOf(object.toString());
        }
        return values;
    }

    /**
     * 从集合中随机获取一个值
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T random(List<T> list) {
        if (ListUtils.isEmpty(list)) {
            return null;
        }
        return list.get(RandomUtils.nextInt(0, list.size()));
    }

    /**
     * 将List集合转成Set集合
     *
     * @param items
     * @return
     */
    public static Set<String> toSet(List<?> items) {
        Set<String> values = new HashSet<>();
        for (Object item : items) {
            if (item == null) {
                continue;
            }
            values.add(item.toString());
        }
        return values;
    }

    /**
     * 将逗号分隔的字符串转成Set集合
     *
     * @param value
     * @return
     */
    public static Set<String> toSet(String value) {
        return ListUtils.toSet(value, ",");
    }

    /**
     * 将指定字符分隔的字符串转成Set集合
     *
     * @param value
     * @param separatorChars
     * @return
     */
    public static Set<String> toSet(String value, String separatorChars) {
        if (StringUtils.isEmpty(value)) {
            return Collections.emptySet();
        }
        Set<String> list = new HashSet<>();
        String[] items = StringUtils.split(value, separatorChars);
        for (String item : items) {
            list.add(item);
        }
        return list;
    }

    /**
     * List Stream 根据某个属性去重
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 将集合转成Long集合
     *
     * @param values
     * @return
     */
    public static List<Long> toLongList(Collection<?> values) {
        if (ListUtils.isEmpty(values)) {
            return null;
        }
        List<Long> resultList = new ArrayList<>();
        Object value;
        for (Iterator<?> it = values.iterator(); it.hasNext(); ) {
            value = it.next();
            if (value == null) {
                continue;
            }
            resultList.add(new Long(value.toString()));
        }
        return resultList;
    }

    public static List<Integer> toIntegerList(String value) {
        return ListUtils.toIntegerList(value, ",");
    }

    public static List<Integer> toIntegerList(String value, String separatorChars) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        String[] items = StringUtils.split(value, separatorChars);
        for (String item : items) {
            list.add(Integer.parseInt(item));
        }
        return list;
    }

    /**
     * 为指定位置插入元素
     *
     * @param srcList
     * @param newList
     * @param indexes
     * @param <E>
     * @return
     */
    public static <E> List<E> insert(List<E> srcList, List<E> newList, Integer... indexes) {
        if (srcList == null) {
            srcList = new ArrayList<>();
        }
        int count = Math.min(newList.size(), indexes.length);
        for (int i = 0; i < count; i++) {
            int index = indexes[i] - 1;
            E element = newList.get(i);
            if (index < srcList.size()) {
                srcList.add(index, element);
            } else {
                srcList.add(element);
            }
        }
        return srcList;
    }

    /**
     * 为指定位置插入元素
     *
     * @param srcList
     * @param newList
     * @param <E>
     * @return
     */
    public static <E> List<E> insert(List<E> srcList, List<ElementDTO> newList) {
        if (srcList == null) {
            srcList = new ArrayList<>();
        }
        newList.sort(Comparator.comparing(ElementDTO::getIndex));
        for (int i = 0; i < newList.size(); i++) {
            ElementDTO dto = newList.get(i);
            int index = dto.getIndex() - 1;
            E element = (E) dto.getElement();
            if (index < srcList.size()) {
                srcList.add(index, element);
            } else {
                srcList.add(element);
            }
        }
        return srcList;
    }
}
