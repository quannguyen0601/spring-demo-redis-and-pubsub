package com.quan.demo.redis.spring.learn.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

import com.quan.demo.redis.spring.learn.model.EmailEvent;
import com.quan.demo.redis.spring.learn.service.PublishingMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Log4j2
public class PublishController {

    @Autowired
    private PublishingMessage publishingMessage;
    
    @PostMapping(value="/api/event")
    public ResponseEntity<String> publishingEvent(@RequestBody EmailEvent event) {
        log.info("*************  SEND MESSAGE FROM QUEUE ***********");

        event.setCreated(LocalDate.now().toEpochDay());
        publishingMessage.publishing(event);
        
        return ResponseEntity.ok().body("OK");
    }
    

}