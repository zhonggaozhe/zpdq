package com.zgz.cpdq;

import com.zgz.cpdq.entity.CrawlUrl;
import com.zgz.cpdq.handler.HumorSetDbHandler;
import com.zgz.cpdq.service.spider.SpiderXhService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpiderXhServiceTest {


    @Autowired
    private SpiderXhService spiderXhService;

    @Autowired
    HumorSetDbHandler humorSetDbHandler;

    @Test
    public void pullxh(){
        spiderXhService.getHumor();
    }


//    @Test
//    public void qsykListHandler(){
//        humorSetDbHandler.qsykListHandler();
//    }

    @Test
    public void getBiedouleData() {
        CrawlUrl crawlUrl = new CrawlUrl();
        crawlUrl.setAllowSpread(false);
        crawlUrl.setUrls(new String[]{"https://www.biedoul.com/t/5pCe56yR5q615a2Q_20.html"});
        crawlUrl.setWhiteRegex(new String[]{"https://www.biedoul.com/t/5pCe56yR5q615a2Q_20.html"});
        spiderXhService.getBiedoule(crawlUrl);
    }
}
