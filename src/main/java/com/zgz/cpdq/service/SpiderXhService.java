package com.zgz.cpdq.service;

import cn.hutool.json.JSONUtil;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.zgz.cpdq.pageVo.CategoryPageVo;
import com.zgz.cpdq.pageVo.XmPageVo;
import com.zgz.cpdq.pageVo.YmxhPageVo;
import com.zgz.cpdq.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpiderXhService {

    @Autowired
    RedisUtil redisUtil;

    public void getHumor() {

        String whiteRegex3 = "https://duanziwang.com/category/%E7%BB%8F%E5%85%B8%E6%AE%B5%E5%AD%90/"+"\\d+"+"/index.html";

        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://duanziwang.com/category/%E7%BB%8F%E5%85%B8%E6%AE%B5%E5%AD%90/index.html")
                .setWhiteUrlRegexs(whiteRegex3)
                .setAllowSpread(true)
                .setThreadCount(8)
//                .setProxyMaker(new RandomProxyMaker().addProxy(proxy))
                .setPageParser(new PageParser<XmPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, XmPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        log.info("幽默笑话: {} ", JSONUtil.toJsonStr(pageVo));
                        redisUtil.sSet("xmList", pageVo.getXmList());
                    }
                })
                .build();
        crawler.start(true);
    }
}
