package com.labi.shardingdao.config.sharding;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import com.labi.shardingdao.mapper.ShardingMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @program: study_microservice
 * @description: 历史数据精确分片算法
 * @author: dzp
 * @create: 2021-12-15 16:03
 **/
@Slf4j
public class HistoryPreciseAlgorithm implements PreciseShardingAlgorithm<Date> {

    private static final String HISTORY_REDIS_KEY = "table-history";
    private static final String HISTORY_TABLE_BASE_NAME = "bd_history";

    private static RedisTemplate<String, Object> redisTemplate;
    private static ShardingMapper shardingMapper;

    /**
     * 手动注入shardingMapper
     */
    public static void setShardingMapper(ShardingMapper shardingMapper) {
        HistoryPreciseAlgorithm.shardingMapper = shardingMapper;
    }

    /**
     * 手动注入redisTemplate
     *
     * @param redisTemplate
     */
    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        HistoryPreciseAlgorithm.redisTemplate = redisTemplate;
    }

    /**
     * 1、先上redis中检查有没有这张表
     * 2、有的话直接返回表名
     * 3、没有的话先创建表，然后再插入数据
     *
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        //计算出往哪张表中插入数据
        Date value = preciseShardingValue.getValue();
        int minute = DateUtil.minute(value);
        int year = DateUtil.year(value);
        StringBuilder tableNameBuilder = new StringBuilder(HISTORY_TABLE_BASE_NAME).append("_").append(year).append("_");
        if (minute % 2 == 0) {
            tableNameBuilder.append("0");
        } else {
            tableNameBuilder.append("1");
        }
        String tableName = tableNameBuilder.toString();
        //先在redis中检查这张表是否已经存在
        List<Object> range = redisTemplate.opsForList().range(HISTORY_REDIS_KEY, 0, -1);
        List<String> alreadyHave = Convert.convert(new TypeReference<List<String>>() {
        }, range);

        //redis中没有，要先建表
        if (!alreadyHave.contains(tableName)) {
            try {
                shardingMapper.createHistoryTable(tableName);
                //建完表，把表名存入redis
                redisTemplate.opsForList().rightPush(HISTORY_REDIS_KEY, tableName);
            } catch (Exception e) {
                log.error("建历史表失败:{}", e.getMessage());
            }
        }

        return tableName;
    }
}
