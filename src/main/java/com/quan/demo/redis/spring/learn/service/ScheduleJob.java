package com.quan.demo.redis.spring.learn.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.quan.demo.redis.spring.learn.model.LockTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ScheduleJob {

    @Autowired
    private ListOperation listOperation;

    
    @Scheduled(fixedRate = 60000)
    public void scheduleInsert(){
        log.info("********************** SCHEDULE INSERT TO REDIS START***************");
        LockTable lockTable = new LockTable("TR_SALE", "LOCK FOR SAVE TR_SALE",  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+7")));
        listOperation.insertLock(lockTable);
        log.info("********************** SCHEDULE INSERT TO REDIS END***************");
    }
}