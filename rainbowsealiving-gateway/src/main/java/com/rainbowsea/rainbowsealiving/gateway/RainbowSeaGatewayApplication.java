package com.rainbowsea.rainbowsealiving.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // 将 nacos 服务和发现开启
public class RainbowSeaGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RainbowSeaGatewayApplication.class,args);
    }
}
