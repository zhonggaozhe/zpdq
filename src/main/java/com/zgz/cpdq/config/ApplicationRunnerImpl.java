package com.zgz.cpdq.config;

import com.zgz.cpdq.redis.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    MessageConsumer messageConsumer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("项目启动成功,开启 Redis 队列监听,..............................");
        messageConsumer.start();
    }
}
