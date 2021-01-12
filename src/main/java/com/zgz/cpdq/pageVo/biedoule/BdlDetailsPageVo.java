package com.zgz.cpdq.pageVo.biedoule;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

@Data
public class BdlDetailsPageVo {

    //body > div.content > div.left > div.nr > dl:nth-child(1) > span > dd > a
    //body > div.content > div.left > div.nr > dl:nth-child(1) > dd
    @PageFieldSelect(cssQuery = "span > dd > a", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String title;

    @PageFieldSelect(cssQuery = "span > dd > a", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "href")
    private String id;

    @PageFieldSelect(cssQuery = "> dd", selectType = XxlCrawlerConf.SelectType.HTML)
    private String content;

    //body > div.content > div.left > div.nr > dl:nth-child(1) > div > div.active.c_r
    //body > div.content > div.left > div.nr > dl:nth-child(1) > div > div.active.c_r > a.pinattn.good
    @PageFieldSelect(cssQuery = "div > div.active.c_r > a.pinattn.good", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "data_attr")
    private String goodNum;

    @PageFieldSelect(cssQuery = "div > div.active.c_r > a.pinattn.bad", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "data_attr")
    private String badNum;

}
