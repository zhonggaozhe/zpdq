package com.zgz.cpdq.pageVo.qisongyike;


import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

/**
 * 轻松一刻
 * url : http://www.17989.com/xiaohua/duanxiaohua/
 */
@Data
public class QsykDetailsPageVo {

    //body > div.container > div.content > div.module.articlelist > ul > li:nth-child(1) > div
    @PageFieldSelect(cssQuery = "div.hd", selectType = XxlCrawlerConf.SelectType.TEXT)
    private String title;

    //body > div.container > div.content > div.module.articlelist > ul > li:nth-child(1) > pre
    @PageFieldSelect(cssQuery = "pre", selectType = XxlCrawlerConf.SelectType.HTML)
    private String content;

//    //
//    @PageFieldSelect(cssQuery = "div.article-commentbar.articleCommentbar.clearfix", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "data-id")
//    private String id;
//
//    //body > div:nth-child(11) > div.main > ul > li:nth-child(1) > div.article-commentbar.articleCommentbar.clearfix > div.good-btn-box.vote-btn > em > span
//    @PageFieldSelect(cssQuery = "div.article-commentbar.articleCommentbar.clearfix > div.good-btn-box.vote-btn > em > span", selectType = XxlCrawlerConf.SelectType.TEXT)
//    private String goodNum;
//
//    //body > div:nth-child(11) > div.main > ul > li:nth-child(1) > div.article-commentbar.articleCommentbar.clearfix > div.bad-btn-box.vote-btn > em > span
//    @PageFieldSelect(cssQuery = "div.article-commentbar.articleCommentbar.clearfix > div.bad-btn-box.vote-btn > em > span", selectType = XxlCrawlerConf.SelectType.ATTR , selectVal = "data-id")
//    private String badNum;
}
