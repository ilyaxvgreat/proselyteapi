package com.khomchenko.proselyteapi.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khomchenko.proselyteapi.dto.StockDto;
import com.khomchenko.proselyteapi.models.Account;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class AppConfiguration {

    @Bean
    public ReactiveRedisOperations<String, Account> redisOperations(LettuceConnectionFactory connectionFactory) {
        RedisSerializationContext<String, Account> serializationContext = RedisSerializationContext
                .<String, Account>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new GenericToStringSerializer<>(Account.class))
                .hashKey(new StringRedisSerializer())
                .hashValue(new GenericJackson2JsonRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    public ReactiveHashOperations<String, String, Account> hashOperations(ReactiveRedisOperations<String, Account> redisOperations) {
        return redisOperations.opsForHash();
    }

    @Bean
    public ReactiveRedisOperations<String, StockDto> redisStocksOperations(LettuceConnectionFactory connectionFactory) {
        RedisSerializationContext<String, StockDto> serializationContext = RedisSerializationContext
                .<String, StockDto>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new RedisSerializer<>() {
                    private final ObjectMapper om = new ObjectMapper();

                    @SneakyThrows
                    @Override
                    public byte[] serialize(StockDto stockDto) throws SerializationException {
                        return om.writeValueAsBytes(stockDto);
                    }

                    @SneakyThrows
                    @Override
                    public StockDto deserialize(byte[] bytes) throws SerializationException {
                        return om.readValue(bytes, StockDto.class);
                    }
                })
                .hashKey(new StringRedisSerializer())
                .hashValue(new GenericJackson2JsonRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    public ReactiveListOperations<String, StockDto> stocksListOperations(ReactiveRedisOperations<String, StockDto> redisStocksOperations) {
        return redisStocksOperations.opsForList();
    }

}
