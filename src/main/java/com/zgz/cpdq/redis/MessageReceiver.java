package com.zgz.cpdq.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class MessageReceiver implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("redis 缓存监听..............");
        doBusiness(message);
    }


    /**
     * 接受订阅消息
     * 具体业务处理
     * @param message
     */
    public void doBusiness(Message message) {
        Object value = redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.info("consumer message entity:{}" , value.toString());
    }
}
