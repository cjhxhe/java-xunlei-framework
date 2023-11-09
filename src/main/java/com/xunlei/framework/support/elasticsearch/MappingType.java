package com.xunlei.framework.support.elasticsearch;

import com.xunlei.framework.common.util.EnumUtils;

/**
 * 映射的类型
 * 基础类型不指定，自动识别
 * 此处暂时只定义特殊类型
 *
 * @author qhluo
 */
public enum MappingType implements EnumUtils.IDEnum {

    GeoPoint("geo_point", "地址位置"),

    Text("text", "会分词然后进行索引，支持模糊、精确查询，不支持聚合"),

    Keyword("keyword", "不进行分词，直接索引，支持模糊、精确查询，支持聚合，通常用于状态，标签，性别，年龄等数据"),

    Integer("integer", "整形"),

    Long("long", "长整形"),

    Boolean("boolean", "布尔型"),

    Double("double", "浮点数"),

    Date("date", "日期类型"),

    Auto("auto", "表示不特别指定类型，默认自动识别");

    private String id;
    private String desc;

    MappingType(String id, String desc) {
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
