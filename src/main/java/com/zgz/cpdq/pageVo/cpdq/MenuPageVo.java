package com.zgz.cpdq.pageVo.cpdq;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

@PageSelect(cssQuery = "body")
@Data
public class MenuPageVo {

    @PageFieldSelect(cssQuery = "div.content > div > div.fl.pm30.w632 > div.new-menu-list.search-menu-list.clearfix.mt10 > div", selectType = XxlCrawlerConf.SelectType.HTML)
    private List<String> menus;

}
