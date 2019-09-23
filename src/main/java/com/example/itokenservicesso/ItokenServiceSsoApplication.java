package com.example.itokenservicesso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 单点登录既要注册到服务注册中心，又要向redis服务系统获取鼓舞
 * 所以要添加 @EnableDiscoveryClient   @EnableEurekaClient 两个注解
 *
 */

@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = "com.example.itokenservicesso.mapper")
@SpringBootApplication
public class ItokenServiceSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItokenServiceSsoApplication.class, args);
    }

}
