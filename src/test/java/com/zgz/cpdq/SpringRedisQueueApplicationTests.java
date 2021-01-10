package com.zgz.cpdq;

import com.zgz.cpdq.redis.MessageConsumer;
import com.zgz.cpdq.redis.MessageEntity;
import com.zgz.cpdq.redis.MessageProducer;
import com.zgz.cpdq.redis.MessageReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRedisQueueApplicationTests {

    @Autowired
    private MessageProducer producer;

    @Autowired
    private MessageReceiver receiver;

    @Autowired
    private MessageConsumer consumer;


    @Test
    public void testSubscribe() {
        String channel = "topic_cpdq";
        producer.sendSubPubMeassage(channel , new MessageEntity("3", "cccccccc"));

        try {
            Thread.sleep(7000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testQueue() {
        consumer.start();
        producer.sendMeassage(new MessageEntity("1", "aaaa"));
        producer.sendMeassage(new MessageEntity("2", "bbbb"));
        try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.interrupt();
    }


}
