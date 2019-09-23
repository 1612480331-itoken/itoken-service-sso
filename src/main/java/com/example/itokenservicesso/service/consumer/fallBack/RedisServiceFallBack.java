package com.example.itokenservicesso.service.consumer.fallBack;


import com.example.itokenservicesso.service.consumer.RedisService;
import com.example.itokenservicesso.service.consumer.hystrix.FallBack;
import org.springframework.stereotype.Component;

@Component
public class RedisServiceFallBack implements RedisService {
    @Override
    public String put(String key, String value, long seconds) {
        return FallBack.badGateWay();
    }

    @Override
    public String get(String key) {
        return FallBack.badGateWay();
    }
}
