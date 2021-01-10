package com.zgz.cpdq.pageVo.xiaohuadaquan;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

@PageSelect(cssQuery = "body")
@Data
public class XhDqPageVo {

    //body > div:nth-child(11) > div.main > ul > li:nth-child(1)
    //body > div:nth-child(11) > div.main > ul
    //body > div:nth-child(11) > div.main > ul > li:nth-child(5)
    @PageFieldSelect(cssQuery = "div > div.main > ul > li", selectType = XxlCrawlerConf.SelectType.OBJECT)
    private List<XhDqDeatilPageVo> xmList;

}
