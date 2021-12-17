package com.labi.shardingdao.runner;

import com.labi.shardingdao.config.sharding.algorithm.HistoryPreciseAlgorithm;
import com.labi.shardingdao.config.sharding.algorithm.HistoryRangeAlgorithm;
import com.labi.shardingdao.mapper.ShardingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-16 11:39
 **/
@Slf4j
@Order(value = 1)
@Component
public class ShardingTablesLoadRunner implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ShardingMapper shardingMapper;

    @Override
    public void run(String... args) {
        HistoryPreciseAlgorithm.setRedisTemplate(redisTemplate);
        HistoryPreciseAlgorithm.setShardingMapper(shardingMapper);
        log.info("======>HistoryPreciseAlgorithm属性注入成功");

        HistoryRangeAlgorithm.setRedisTemplate(redisTemplate);
        log.info("======>HistoryRangeAlgorithm属性注入成功");
    }
}
