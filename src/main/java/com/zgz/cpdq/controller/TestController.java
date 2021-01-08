package com.zgz.cpdq.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zgz.cpdq.entity.UserInfo;
import com.zgz.cpdq.handler.MenuSetDbHandler;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.service.SpiderXhService;
import com.zgz.cpdq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TestController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MenuSetDbHandler menuSetDbHandler;

    @Autowired
    private SpiderXhService spiderXhService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/originalMenuDataHandler")
    public void cc() {
        menuSetDbHandler.originalMenuDataHandler();
    }


    @RequestMapping(value = "/getHumor")
    public void getHumor() {
        spiderXhService.getHumor();
    }

    @RequestMapping(value = "/getXhDaQuan")
    public void getXhDaQuan() {
        spiderXhService.getXhDaQuan();
    }


    @RequestMapping(value = "/initUser")
    public void createUser(@RequestParam(defaultValue = "10000") int count) {
        userService.initUserInfo(count);
    }

    @RequestMapping(value = "/getUserById")
    public UserInfo getUserById(@RequestParam Integer userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(value = "humorList")
    public Object humorList(@RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "10") long pageSize) {
        String key = "xmList";
        long totalPages = redisUtil.sGetSetSize(key);
        if (page >= totalPages) {
            page = totalPages;
        }
        Set<Object> set = redisUtil.sGet(key);
        int i = 0;
        JSONArray jsonArray = null;
        for (Object object : set) {
            i++;
            if (i == page) {
                jsonArray = (JSONArray) object;
                break;
            }
        }

        List<JSONObject> list = new ArrayList<>();
        for (int j = 0; j < jsonArray.size(); j++) {
            JSONObject object = (JSONObject) jsonArray.get(j);
            object.put("id", (page - 1) * 10 + (j + 1));
            object.put("user_name", RandomUtil.randomString(5));
            list.add(object);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", 200);
        result.put("data", list);
        return result;
    }
}
