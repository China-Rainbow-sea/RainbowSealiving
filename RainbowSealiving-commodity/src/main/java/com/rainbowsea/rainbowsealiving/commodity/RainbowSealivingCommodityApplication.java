package com.rainbowsea.rainbowsealiving.commodity;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


// dao 路径扫描映射
// @MapperScan指定扫描的dao包，如果我们在Dao指定的有 @Mapper ，也可以不写
//@MapperScan("com.rainbowsea.rainbowsealiving.commodity.dao")
@EnableDiscoveryClient  // 开启 nacos 的注册服务发现
@SpringBootApplication
public class RainbowSealivingCommodityApplication {
    public static void main(String[] args) {
        SpringApplication.run(RainbowSealivingCommodityApplication.class, args);
    }
}
