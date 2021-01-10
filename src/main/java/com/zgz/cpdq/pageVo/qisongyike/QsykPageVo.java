package com.zgz.cpdq.pageVo.qisongyike;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

@PageSelect(cssQuery = "body")
@Data
public class QsykPageVo {

    //body > div.container > div.content > div.module.articlelist > ul > li:nth-child(1)
    @PageFieldSelect(cssQuery = "div.container > div.content > div.module.articlelist > ul > li", selectType = XxlCrawlerConf.SelectType.OBJECT)
    private List<QsykDetailsPageVo> qsykDetailsPageVos;
}
