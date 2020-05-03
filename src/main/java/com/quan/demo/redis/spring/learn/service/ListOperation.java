package com.quan.demo.redis.spring.learn.service;

import javax.annotation.Resource;

import com.quan.demo.redis.spring.learn.model.LockTable;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

@Service  
public class ListOperation {

    private static final String KEY_LOCK = "LOCK_TABLE";

    @Resource(name ="redisLockTemplate")
    private ListOperations<String, LockTable> lockTableListOps;

    public void insertLock(LockTable table){
        lockTableListOps.leftPush(KEY_LOCK, table);
    }

    public long listSize(){
        return lockTableListOps.size(KEY_LOCK);
    }

    public LockTable get(Long index){
        return lockTableListOps.index(KEY_LOCK, index);
    }

    public LockTable rightPop(){
        return lockTableListOps.rightPop(KEY_LOCK);
    }

    public Long delete(LockTable table){
        return lockTableListOps.remove(KEY_LOCK,1L, table);
    }
}    
