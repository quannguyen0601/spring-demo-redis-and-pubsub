package com.quan.demo.redis.spring.learn.service;

import com.quan.demo.redis.spring.learn.model.EmailEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublishingMessage {

    @Autowired
    private RedisTemplate<String, EmailEvent> redisEventTemplate;

    public void publishing(final EmailEvent event){
        redisEventTemplate.convertAndSend("messageQueue", event);
    }
}