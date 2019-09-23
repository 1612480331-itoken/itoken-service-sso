package com.example.itokenservicesso.service.consumer;


import com.example.itokenservicesso.service.consumer.fallBack.RedisServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "itoken-service-redis", fallback = RedisServiceFallBack.class)
public interface RedisService {

    @PostMapping(value = "put")
    public String put(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value, @RequestParam(value = "seconds") long seconds);

    @GetMapping(value = "get")
    public String get(@RequestParam(value = "key") String key);

}
