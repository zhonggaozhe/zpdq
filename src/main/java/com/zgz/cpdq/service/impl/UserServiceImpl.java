package com.zgz.cpdq.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.zgz.cpdq.entity.UserInfo;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.service.UserService;
import com.zgz.cpdq.util.NickName;
import com.zgz.cpdq.util.RandomImage;
import com.zgz.cpdq.util.RandomName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    private static String user_table = "user_table";

    @Override
    public void initUserInfo(int n) {
        int exist_count = 0;
        Map<Object, Object> map = redisUtil.hmget(user_table);
        if (null != map) {
            exist_count = map.size();
        } else {
            map = new HashMap<>();
        }
        for (int i = 1; i <= n; i++) {
            String chineseName = NickName.generateName();
            String head = RandomImage.randomImage();
//            String head = "";
            Integer userId = exist_count + i;
            log.info("初始化用户-{} - {} - {} ", i , chineseName, head);
            map.put(userId.toString(), new UserInfo(userId, chineseName, head));
        }
        redisUtil.hmset(user_table, map);
    }

    @Override
    public UserInfo getUserById(Integer userId) {
        Object object = redisUtil.hget(user_table, userId.toString());
        if (null == object) {
            return null;
        }
        UserInfo userInfo = JSONObject.parseObject(object.toString() , UserInfo.class);
        return userInfo;
    }


//    public static void main(String[] args) {
//        String paths[] = {"/Users/zhonggaozhe/Downloads/图片助手(ImageAssistant) 批量图片下载器/www.woyaogexing.com/2021-01-08_09-54-24/微信头像_男生女生微信头像图片_我要个性网" ,
//        "/Users/zhonggaozhe/Downloads/图片助手(ImageAssistant) 批量图片下载器/www.woyaogexing.com/2021-01-08_10-08-15/微信头像_男生女生微信头像图片_第2页_我要个性网"};
//
//        for (int i = 0; i < paths.length; i++) {
//            File[] files = FileUtil.ls(paths[i]);
//            for (File file : files){
//                System.out.println("\""+file.getName()+"\",");
//            }
//        }
//    }
}
