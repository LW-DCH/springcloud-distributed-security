package com.lw.cn.distributed.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author:刘伟
 * @date:2021/7/18 23:34
 * @description:授权服务（令牌获取）
 */
@SpringBootApplication
@EnableDiscoveryClient//注册中心
@EnableHystrix//熔断
@EnableFeignClients(basePackages = {"com.lw.cn.distributed.uaa"})//远程调用
public class UAAServer {

    public static void main(String[] args) {
        SpringApplication.run(UAAServer.class);
    }
}
