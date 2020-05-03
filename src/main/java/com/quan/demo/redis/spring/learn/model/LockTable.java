package com.quan.demo.redis.spring.learn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockTable {
    private String name;
    private String description;
    private Long created;
}