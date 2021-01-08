package com.zgz.cpdq.pageVo;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

@Data
public class XhDqDeatilPageVo {

    //body > div:nth-child(11) > div.main > ul > li:nth-child(1) > span.article-title > a
    //body > div:nth-child(11) > div.main > ul > li:nth-child(1) > div.summary-text
    @PageFieldSelect(cssQuery = "span.article-title > a", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String title;

    @PageFieldSelect(cssQuery = "div.summary-text", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String content;

    @PageFieldSelect(cssQuery = "div.article-commentbar.articleCommentbar.clearfix", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "data-id")
    private String id;

    //body > div:nth-child(11) > div.main > ul > li:nth-child(1) > div.article-commentbar.articleCommentbar.clearfix > div.good-btn-box.vote-btn > em > span
    @PageFieldSelect(cssQuery = "div.article-commentbar.articleCommentbar.clearfix > div.good-btn-box.vote-btn > em > span", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String goodNum;

    //body > div:nth-child(11) > div.main > ul > li:nth-child(1) > div.article-commentbar.articleCommentbar.clearfix > div.bad-btn-box.vote-btn > em > span
    @PageFieldSelect(cssQuery = "div.article-commentbar.articleCommentbar.clearfix > div.bad-btn-box.vote-btn > em > span", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "data-id")
    private String badNum;

}
