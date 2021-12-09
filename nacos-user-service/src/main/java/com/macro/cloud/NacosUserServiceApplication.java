package com.macro.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Scanner;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosUserServiceApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String port = scanner.nextLine();
        new SpringApplicationBuilder(NacosUserServiceApplication.class)
                .properties("server.port=" + port).run(args);
    }

//    public static void main(String[] args) {
//        SpringApplication.run(NacosUserServiceApplication.class, args);
//    }

}
