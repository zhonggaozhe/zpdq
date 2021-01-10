package com.zgz.cpdq.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.zgz.cpdq.redis.MessageConsumer;
import com.zgz.cpdq.redis.MessageProducer;
import com.zgz.cpdq.redis.RedisLock;
import com.zgz.cpdq.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 定时拉取各个平台数据
 */

@Configuration
@EnableScheduling
@Slf4j
public class XhScheduleTask {

    @Autowired
    private MessageConsumer consumer;

    /**
     * 定时扫描 redis缓存数据入库
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    private void configureTasks() {
        log.info("执行定时扫描 redis缓存数据入库:{} ", LocalDateTime.now());
        TimeInterval timer = DateUtil.timer();
//        consumer.start();
        log.info("执行定时扫描 redis缓存数据入库,耗时:{} ", timer.interval());

    }


}
