package com.labi.shardingdao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-03 11:20
 **/
@Configuration
@MapperScan("com.labi.shardingdao.mapper")
public class MyBatisConfig {
}
