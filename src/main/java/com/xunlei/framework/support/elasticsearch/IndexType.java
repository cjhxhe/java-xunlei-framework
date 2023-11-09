package com.xunlei.framework.support.elasticsearch;

import com.xunlei.framework.common.util.EnumUtils;

/**
 * 索引方式
 *
 * @author qhluo
 */
public enum IndexType implements EnumUtils.IDEnum {

    Analyzed("analyzed", "首先分析这个字符串，然后索引。换言之，以全文形式索引此字段"),

    Not_Analyzed("not_analyzed", "索引这个字段，使之可以被搜索，但是索引内容和指定值一样。不分析此字段。"),

    No("no", "不索引这个字段");

    private String id;
    private String desc;

    IndexType(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
