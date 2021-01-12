package com.zgz.cpdq.pageVo.biedoule;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

@PageSelect(cssQuery = "body")
@Data
public class BdlPageVo {

    @PageFieldSelect(cssQuery = "div.content > div.left > div.nr > dl", selectType = XxlCrawlerConf.SelectType.OBJECT)
    private List<BdlDetailsPageVo> bdlDetailsPageVoList;
}
