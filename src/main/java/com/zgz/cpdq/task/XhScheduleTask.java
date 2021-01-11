package com.zgz.cpdq.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSONObject;
import com.zgz.cpdq.config.RedisCacheConfig;
import com.zgz.cpdq.constant.RedisKeyConstant;
import com.zgz.cpdq.entity.CrawlUrl;
import com.zgz.cpdq.enums.SourceEnums;
import com.zgz.cpdq.redis.MessageConsumer;
import com.zgz.cpdq.redis.MessageProducer;
import com.zgz.cpdq.redis.RedisLock;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.service.spider.SpiderXhService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 定时拉取各个平台数据
 */

@Configuration
@EnableScheduling
@Slf4j
public class XhScheduleTask {

    @Autowired
    private SpiderXhService spiderXhService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 抓取数据源
     */
    @Scheduled(cron = "0 0/10 * * * ?" ,fixedDelay = 3000)
    private void configureTasks() {
        log.info("执行定时扫描,抓取数据源:{} ", LocalDateTime.now());
        TimeInterval timer = DateUtil.timer();
        RedisLock redisLock = new RedisLock(redisUtil.getRedisTemplate(), RedisKeyConstant.crawl_cache_urls_lock_key);
        boolean isGetLock = redisLock.lockNoRetry();
        try {
            if (isGetLock) {
                Set<Object> set = redisUtil.sGet(RedisKeyConstant.crawl_cache_urls);
                if (set == null || set.size() <= 0) {
                    log.info("定时扫描拉取数据,获取需要拉取的 URL 地址为空!!!!!");
                    return;
                }

                for (Object obj : set) {
                    log.info("定时任务处理需要抓取数据 URL:{}", obj.toString());
                    CrawlUrl crawlUrl = JSONObject.parseObject(obj.toString(), CrawlUrl.class);
                    SourceEnums sourceEnums = SourceEnums.getSourceEnumsBySource(crawlUrl.getSource());
                    if (null == sourceEnums) {
                        log.error("定时任务处理需要抓取数据,获取数据来源失败!!!");
                        redisUtil.setRemove(RedisKeyConstant.crawl_cache_urls, crawlUrl);
                        continue;
                    }
                    switch (sourceEnums) {
                        case XHDQ:
                            spiderXhService.getXhDaQuan(crawlUrl);
                            break;
                        case QSYK:
                            spiderXhService.getQsyk(crawlUrl);
                            break;
                    }
                    redisUtil.setRemove(RedisKeyConstant.crawl_cache_urls, crawlUrl);
                }
            } else {
                log.info("执行定时扫描,抓取数据源,正在执行中........... ....................");
            }
        } catch (Exception e) {
            log.error("定时任务执行异常 ", e);
        } finally {
            redisLock.unlock();
        }

        log.info("执行定时扫描,抓取数据源,耗时:{} ", timer.interval());

    }


}
