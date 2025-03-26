package com.rainbowsea.rainbowsealiving.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // 开启 nacos 的注册服务发现
public class RainbowsealivingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainbowsealivingServiceApplication.class, args);
    }
}
