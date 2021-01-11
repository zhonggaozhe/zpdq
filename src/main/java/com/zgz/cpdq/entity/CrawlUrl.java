package com.zgz.cpdq.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class CrawlUrl {

    @NotEmpty(message = "URL集合长度不能为 0")
    private String[] urls;

    @NotEmpty(message = "校验 URL 正则表达为空")
    private String[] whiteRegex;

    private boolean allowSpread = true;

    @NotBlank(message = "数据来源为空")
    private String source;

}
