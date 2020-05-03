package com.quan.demo.redis.spring.learn.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailEvent implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6119869151653788989L;
    
    private String sender;
    private String receiver;
    private String content;
    private EmailType type;
    private Long created;

    public enum EmailType {
        INVITE,
        NEW,
        NOTIFICATION
    }


    @Override
    public String toString() {
        return "[sender: "+sender+" receiver:"+receiver+" content:"+content+ "type: " +type.toString()+" ]";
    }
}