package com.save.cep.config;

import com.save.cep.DTO.AddressDTO;
import com.save.cep.model.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.validation.annotation.Validated;

@Configuration
public class RedisConfig {

    @Value("${host.redis}")
    private String hostRedis;

    @Bean
    RedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(hostRedis);
        config.setPort(6379);
        System.out.println(hostRedis);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, AddressDTO> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, AddressDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
