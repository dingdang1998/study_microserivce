package com.labi.shardingdao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-16 09:27
 **/
@Repository
public interface ShardingMapper {
    /**
     * 建历史数据表
     *
     * @param tableName
     */
    void createHistoryTable(@Param("tableName") String tableName);
}
