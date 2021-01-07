package com.zgz.cpdq;

import com.zgz.cpdq.service.SpiderXhService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpiderXhServiceTest {


    @Autowired
    private SpiderXhService spiderXhService;

    @Test
    public void pullxh(){
        spiderXhService.getHumor();
    }
}
