package com.zgz.cpdq.redis;

import com.alibaba.fastjson.JSONObject;
import com.zgz.cpdq.handler.HumorSetDbHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis 消息队列监听线程
 */
@Service
@Slf4j
public class MessageConsumer extends Thread {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private volatile boolean flag = true;

    @Value("${redis.queue.key}")
    private String queueKey;

    @Value("${redis.queue.pop.timeout}")
    private Long popTimeout;

    @Autowired
    private HumorSetDbHandler humorSetDbHandler;

    @Override
    public void run() {
        try {
            MessageEntity message;
            while (flag && !Thread.currentThread().isInterrupted()) {
                log.info("redis消息队列 queueKey = {} 线阻塞程状态:{}", queueKey, Thread.currentThread().isInterrupted());
                Object object = redisTemplate.opsForList().rightPop(queueKey, popTimeout, TimeUnit.SECONDS);
                if (object == null) {
                    log.info("redis消息队列 queueKey = {} ,获取队列数据为空 --------------flag = {}", queueKey, flag);
                    continue;
                }
                message = JSONObject.parseObject(object.toString(), MessageEntity.class);
                log.info("redis消息队列获得消息体:{}", message.toString());
                doBusiness(message);
            }
        } catch (Exception e) {
            log.error("redis 消息队列处理异常!!!", e);
        }
    }

    /**
     * 做具体业务
     *
     * @param message 队列消息体
     */
    private void doBusiness(MessageEntity message) {
        if (message.getMessageTypeEnums() == null) {
            return;
        }
        switch (message.getMessageTypeEnums()) {
            case QSYK:
                humorSetDbHandler.saveQsykData(message.getContent());
                break;
            case XHDQ:
                humorSetDbHandler.saveXhdqData(message.getContent());
                break;
            case BDL:
                humorSetDbHandler.saveBdlData(message.getContent());
                break;
        }

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
