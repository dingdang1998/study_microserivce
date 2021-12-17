package com.macro.cloud.entity.sharding;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: study_microservice
 * @description: 分表内容保存
 * @author: dzp
 * @create: 2021-12-17 15:57
 **/
@Getter
@AllArgsConstructor
public class ShardingTable {
    /**
     * 逻辑表
     */
    private final String logicTable;
    /**
     * 依据哪个字段进行分表
     */
    private final String shardingColumn;
}
