package com.labi.shardingdao.config.sharding.algorithm;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.labi.shardingdao.common.Constants;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @program: study_microservice
 * @description: 历史数据范围分片算法
 * @author: dzp
 * @create: 2021-12-17 16:29
 **/
public class HistoryRangeAlgorithm implements RangeShardingAlgorithm<Date> {

    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * 手动注入redisTemplate
     *
     * @param redisTemplate
     */
    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        HistoryRangeAlgorithm.redisTemplate = redisTemplate;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        //获取数据库已建好的表，从redis中找已经建好的表
        List<Object> range = redisTemplate.opsForList().range(Constants.HISTORY_REDIS_KEY, 0, -1);
        List<String> alreadyHave = Convert.convert(new TypeReference<List<String>>() {
        }, range);

        if (alreadyHave.size() == 0) {
            return Collections.singletonList(Constants.HISTORY_TABLE_BASE_NAME);
        } else {
            //todo 这个地方最好不要直接全部返回，应该精确范围
            return alreadyHave;
        }
    }
}
