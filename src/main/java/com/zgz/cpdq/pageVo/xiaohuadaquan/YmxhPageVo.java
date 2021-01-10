package com.zgz.cpdq.pageVo.xiaohuadaquan;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

@Data
public class YmxhPageVo {

    //#\33 5088 > div.post-head > div > time:nth-child(1)
    @PageFieldSelect(cssQuery = "div.post-head > div > time:nth-child(1)", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String dateTime;

    @PageFieldSelect(cssQuery = "div.post-content > p", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String content;

    //#\33 5088 > div.post-content > p

    //#\33 5088 > div.post-head > div > time:nth-child(2)

    @PageFieldSelect(cssQuery = "div.post-head > div > time:nth-child(1)", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String weight;

}
