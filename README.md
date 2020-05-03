# spring-demo-redis-and-pubsub

This repo contains the source code of my demo using spring-boot and redis.

## Run Redis server

There are many ways to install redis server. I'm using docker for creating the server because it is pretty easier in my opinion. Below is a content of the docker-compose.yml.
```
version: '3.7'

services: 
    redis:
        image: redis:6.0.1
        container_name: redis-learn
        ports: 
        - "6379:6379"
        volumes:
        - ./my-redis.conf:/usr/local/etc/redis/redis.conf

```

### Config Spring boot 
* It requires to config the connection to make redis working with redis. We will edit the application.properties. I used the basic configurations but there are plenty of these.
```
spring.redis.host=localhost
spring.redis.port=6379
```
* To make sure Redis working. I need to put a annotation `@EnableRedisRepositories`.
* After that, we need creating some beans for everything is working .
```
@Bean
public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory();
}
```
**NOTE:** In currently, I'm using [Lettuce](https://lettuce.io/). But there are many other such as  [Redisson](https://github.com/redisson/redisson), [Jedis](https://github.com/xetorthio/jedis).

### IF you have any questions, feel to contact me at [nquan@outlook.com.vn](mailto:nquan@outlook.com.vn).
