package com.xunlei.framework.common.enums;

import com.xunlei.framework.common.util.EnumUtils;

/**
 * 时间阶段枚举定义
 */
public enum TimeStage implements EnumUtils.IDEnum {
    BEFORE("BEFORE", "未开始"),
    ING("ING", "进行中"),
    AFTER("AFTER", "已结束");

    private String id;
    private String desc;

    TimeStage(String id, String desc) {
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
