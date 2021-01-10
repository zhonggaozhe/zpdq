package com.zgz.cpdq.pageVo.cpdq;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

@PageSelect(cssQuery = "div.dd.food-material > div")
@Data
public class MaterialPageVo {

    //body > div.content > div > div.fl.w632 > div.bpannel.mt20.p15.re-steps > div:nth-child(1) > div.dd.food-material > div > span:nth-child(2) > em.fl
    @PageFieldSelect(cssQuery = "div > div.dd", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String name;

    //body > div.content > div > div.fl.w632 > div.bpannel.mt20.p15.re-steps > div:nth-child(1) > div.dd.food-material > div > span:nth-child(2) > em.fr
    @PageFieldSelect(cssQuery = "span > em.fr", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String value;
}
