package com.zgz.cpdq.pageVo;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

@PageSelect(cssQuery = "body")
@Data
public class CategoryPageVo {

    @PageFieldSelect(cssQuery = "div.content > div > ul > li > a", selectType = XxlCrawlerConf.SelectType.TEXT)
    private List<String> lables;

    @PageFieldSelect(cssQuery = "#pig > ul > li > a", selectType = XxlCrawlerConf.SelectType.TOSTRING)
    private List<String> a_html;

    // #pig > ul > li:nth-child(2) > a
    // body > div.content > div > div > div:nth-child(2) > div.col2-1.w470.fr > div:nth-child(1) > div > ul > li:nth-child(1) > a
    // body > div.content > div > div > div:nth-child(2) > div.col2-1.w470.fl > div:nth-child(2) > div > ul > li:nth-child(3) > a
    // body > div.content > div > div > div:nth-child(2) > div.col2-1.w470.fr > div:nth-child(2) > div > ul > li:nth-child(2) > a
    // body > div.content > div > div > div:nth-child(2) > div.col2-1.w470.fr > div:nth-child(2) > div > ul > li:nth-child(2) > a

    // #pig > ul > li:nth-child(1) > a
}
