package com.rainbowsea.rainbowsealiving.commodity.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration  // 标识是一个配置类
@EnableTransactionManagement //开启事务
@MapperScan("com.rainbowsea.rainbowsealiving.commodity.dao")  // 扫描的 dao 目录包位置
public class MyBatisConfig {

    // 引入分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作，
        // true 调回到首页，false 继续请求 默认 false
        paginationInterceptor.setOverflow(true);
        //设置最大单页限制数量，默认 500条，-1不受限制
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }
}
