package com.zgz.cpdq.pageVo.xiaohuadaquan;

import com.xuxueli.crawler.annotation.PageFieldSelect;
import com.xuxueli.crawler.annotation.PageSelect;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import lombok.Data;

import java.util.List;

@PageSelect(cssQuery = "body")
@Data
public class XmPageVo {

    @PageFieldSelect(cssQuery = "article.post", selectType = XxlCrawlerConf.SelectType.OBJECT)
    private List<YmxhPageVo> xmList;

    public int hashCode(){
        return this.hashCode();
    }
}
