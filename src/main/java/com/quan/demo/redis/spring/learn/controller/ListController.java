package com.quan.demo.redis.spring.learn.controller;

import com.quan.demo.redis.spring.learn.model.LockTable;
import com.quan.demo.redis.spring.learn.service.ListOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ListController {

    @Autowired
    private ListOperation listOperation;

    @PostMapping(value="/api/lock")
    public ResponseEntity<String> postMethodName(@RequestBody final LockTable entity) {
        listOperation.insertLock(entity);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(value = "/api/lock/size")
    public ResponseEntity<Long>  postMethodName() {
        return ResponseEntity.ok().body(listOperation.listSize());
    }

    @GetMapping(value = "/api/lock/{id}")
    public ResponseEntity<LockTable> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(listOperation.get(id));
    }
}