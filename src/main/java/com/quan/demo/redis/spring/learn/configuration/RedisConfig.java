package com.quan.demo.redis.spring.learn.configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.quan.demo.redis.spring.learn.model.EmailEvent;
import com.quan.demo.redis.spring.learn.model.LockTable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, LockTable> redisLockTemplate() {
        final RedisTemplate<String, LockTable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        Jackson2JsonRedisSerializer<LockTable> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<LockTable>(
                LockTable.class);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, EmailEvent> redisEventTemplate() {
        final RedisTemplate<String, EmailEvent> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setValueSerializer(new EmailEventSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        final StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisListener() {
        RedisMessageListenerContainer listener = new RedisMessageListenerContainer();
        listener.setConnectionFactory(redisConnectionFactory());
        listener.addMessageListener(new CustomMessageListener(), messageTopic());

        return listener;
    }

    @Bean
    ChannelTopic messageTopic() {
        return new ChannelTopic("messageQueue");
    }

    /**
     * 
     */
    public class CustomMessageListener implements MessageListener {
        @Override
        public void onMessage(Message message, byte[] pattern) {
            log.info("************* RECEIVER MESSAGE FROM QUEUE ***********");
            ByteArrayInputStream in;
            ObjectInputStream is;
            try {
                in = new ByteArrayInputStream(message.getBody());
                is = new ObjectInputStream(in);

                EmailEvent event = (EmailEvent) is.readObject();
                log.info("message: >>> {}", event.toString());

                is.close();
                in.close();
            } catch (IOException | ClassNotFoundException ce) {

            } finally {

                log.info("************* RECEIVER MESSAGE FROM QUEUE END***********");
            }
        }
    }

    public class EmailEventSerializer implements RedisSerializer<EmailEvent> {

        @Override
        public byte[] serialize(EmailEvent t) throws SerializationException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os;
            try {
                os = new ObjectOutputStream(out);
                os.writeObject(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return out.toByteArray();
        }

        @Override
        public EmailEvent deserialize(byte[] bytes) throws SerializationException {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream is;
            try {
                is = new ObjectInputStream(in);
                return (EmailEvent) is.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

     }
}