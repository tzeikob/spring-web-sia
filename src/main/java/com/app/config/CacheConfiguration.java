package com.app.config;

import com.app.model.Dokument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@PropertySource("classpath:config.properties")
public class CacheConfiguration {
    
    @Autowired
    private Environment environment;
    
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        String host = environment.getProperty("development.redis.host");
        int port = environment.getProperty("development.redis.port", Integer.class);
        String password = environment.getProperty("development.redis.password");
        
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setDatabase(0);
        factory.setPassword(password);
        factory.setUsePool(true);
        
        return factory;
    }
    
    @Bean
    @Autowired
    public RedisTemplate<String, Dokument> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Dokument> redisTemplate = new RedisTemplate<String, Dokument>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Dokument>(Dokument.class));
        
        return redisTemplate;
    }
    
    @Bean
    @Autowired
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }
}
