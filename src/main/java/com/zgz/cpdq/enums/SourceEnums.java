package com.zgz.cpdq.enums;

import lombok.Getter;

/**
 * 抓取资源来源
 */
@Getter
public enum SourceEnums {

    XHDQ("xhdq", "笑话大全"),
    QSYK("qsyk", "轻松一刻");


    private String source;

    private String name;

    SourceEnums(String source, String name) {
        this.source = source;
        this.name = name;
    }

    /**
     * 获取枚举对象
     *
     * @param source
     * @return
     */
    public static SourceEnums getSourceEnumsBySource(String source) {
        SourceEnums[] values = SourceEnums.values();
        for (SourceEnums value : values) {
            if (value.getSource().equals(source)) {
                return value;
            }
        }
        return null;
    }
}
