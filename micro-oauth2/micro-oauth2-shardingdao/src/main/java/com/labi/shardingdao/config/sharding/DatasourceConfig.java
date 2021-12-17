package com.labi.shardingdao.config.sharding;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.macro.cloud.entity.sharding.ShardingTable;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program: study_microservice
 * @description: sharding数据源配置
 * @author: dzp
 * @create: 2021-12-17 16:03
 **/
@Configuration
public class DatasourceConfig {

    @Autowired
    private SpringBootShardingRuleConfigurationProperties springBootShardingRuleConfigurationProperties;

    /**
     * 获取sharding jdbc 分表的逻辑表名及分表字段
     * 1、仅考虑分表，不考虑分库
     * 2、分表字段仅考虑一个，复合分片和hint分片暂时不在考虑范围内
     *
     * @return
     */
    @Bean
    public List<ShardingTable> shardingTables() {
        List<ShardingTable> list = new ArrayList<>();
        //读取配置文件配置
        final ShardingRuleConfiguration swap = new ShardingRuleConfigurationYamlSwapper().swap(springBootShardingRuleConfigurationProperties);
        //得到逻辑表和分表字段
        final Collection<TableRuleConfiguration> tableRuleConfigs = swap.getTableRuleConfigs();
        for (TableRuleConfiguration tableRuleConfig : tableRuleConfigs) {
            String table = tableRuleConfig.getLogicTable();
            final ShardingStrategyConfiguration tableShardingStrategyConfig = tableRuleConfig.getTableShardingStrategyConfig();
            if (tableShardingStrategyConfig instanceof StandardShardingStrategyConfiguration) {
                StandardShardingStrategyConfiguration standardShardingStrategyConfiguration = (StandardShardingStrategyConfiguration) tableShardingStrategyConfig;
                String column = standardShardingStrategyConfiguration.getShardingColumn();

                list.add(new ShardingTable(table, column));
            }
        }
        return list;
    }

    /**
     * mybatis-plus分页插件 + sql拦截器
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
        // 页数溢出时，返回第一页
        paginationInterceptor.setOverflow(true);
        List<ISqlParser> sqlParserList = new ArrayList<>();
        /*
        mybatis拦截器，检查分表字段
        针对分表 进行 增、删、改、查 操作时，强制要求必须加分表字段，否则拦截器会报错
        不支持子查询
         */
        sqlParserList.add(new ShardingColumnChecker(shardingTables()));
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }
}
