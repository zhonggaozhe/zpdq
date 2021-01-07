package com.zgz.cpdq.controller;

import com.zgz.cpdq.handler.MenuSetDbHandler;
import com.zgz.cpdq.pageVo.XmPageVo;
import com.zgz.cpdq.pageVo.YmxhPageVo;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.service.SpiderXhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class TestController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MenuSetDbHandler menuSetDbHandler;

    @Autowired
    private SpiderXhService spiderXhService;

    @RequestMapping(value = "/originalMenuDataHandler")
    public void cc() {
        menuSetDbHandler.originalMenuDataHandler();
    }


    @RequestMapping(value = "/getHumor")
    public void getHumor() {
        spiderXhService.getHumor();
    }

    @RequestMapping(value = "humorList")
    public Object humorList(@RequestParam(defaultValue = "1") long page , @RequestParam(defaultValue = "10") long pageSize){
        String key = "xmList";
        long totalPages = redisUtil.sGetSetSize(key);
        if (page >= totalPages){
            page = totalPages;
        }
        Set<Object> set = redisUtil.sGet(key);
        int i = 0;
        for (Object object : set) {
            i++;
            if(i == page){
                return object;
            }
        }
        return null;
    }
}
