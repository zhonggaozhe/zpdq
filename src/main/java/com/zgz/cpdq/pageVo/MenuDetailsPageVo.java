package com.zgz.cpdq.pageVo;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

@Data
@PageSelect(cssQuery = "body")
public class MenuDetailsPageVo {

    /**
     * 材料
     */
    @PageFieldSelect(cssQuery = "div.content > div > div.fl.w632 > div.bpannel.mt20.p15.re-steps > div:nth-child(1)", selectType = XxlCrawlerConf.SelectType.HAS_CLASS)
    private List<MaterialPageVo> material;

    @PageFieldSelect(cssQuery = "div.content > div > div.fl.w632 > div.bpannel.pr > div.recipe-exp > div.re-up > h1", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String title;

    //body > div.content > div > div.fl.w632 > div.bpannel.pr > div.recipe-slider > div > ul:nth-child(1) > a > img
    @PageFieldSelect(cssQuery = "div.content > div > div.fl.w632 > div.bpannel.pr > div.recipe-slider > div > ul:nth-child(1) > a > img", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "src")
    private String imageUrl;

    //body > div.content > div > div.fl.w632 > div.bpannel.pr > div.recipe-exp > div.re-up > p > span:nth-child(2)
    @PageFieldSelect(cssQuery = "div.content > div > div.fl.w632 > div.bpannel.pr > div.recipe-exp > div.re-up > p > span:nth-child(2)", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String menuId;



    /**
     * 做法
     */
    @PageFieldSelect(cssQuery = "div.content > div > div.fl.w632 > div.bpannel.mt20.p15.re-steps > div:nth-child(2) > div.dd > ol > li", selectType = XxlCrawlerConf.SelectType.HTML)
    private String practice;

    // body > div.content > div > div.fl.w632 > div.bpannel.mt20.p15.re-steps > div:nth-child(1) > div.dt.cg2
    // body > div.content > div > div.fl.w632 > div.bpannel.mt20.p15.re-steps > div:nth-child(1) > div.dd.food-material > div > span
    // body > div.content > div > div.fl.w632 > div.bpannel.mt20.p15.re-steps > div:nth-child(2) > div.dt.cg2
}
