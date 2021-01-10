package com.zgz.cpdq.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.queue.key}")
    private String queueKey;

    /**
     * 发送消息
     *
     * @param message
     * @return
     */
    public Long sendMeassage(MessageEntity message) {
        log.info("发送队列消息, message = {} ", message.toString());
        return redisTemplate.opsForList().leftPush(queueKey, message);

    }

    /**
     * 发送订阅消息
     *
     * @param channel
     * @param messageEntity
     */
    public void sendSubPubMeassage(String channel, MessageEntity messageEntity) {
        log.info("发送订阅消息,channel = {} , message = {} ", channel, messageEntity.toString());
        redisTemplate.convertAndSend(channel, messageEntity);
    }
}
