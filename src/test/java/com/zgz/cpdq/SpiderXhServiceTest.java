package com.zgz.cpdq;

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
}
