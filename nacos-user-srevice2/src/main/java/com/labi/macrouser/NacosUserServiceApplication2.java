package com.labi.macrouser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosUserServiceApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(NacosUserServiceApplication2.class, args);
    }
}
