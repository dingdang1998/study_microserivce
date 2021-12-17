package com.labi.shardingdao;

import cn.hutool.core.date.DateUtil;
import com.labi.shardingdao.service.IBdHistoryService;
import com.macro.cloud.entity.business.BdHistory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
class CustomTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private IBdHistoryService historyService;

    @Test
    void contextLoads() {
        while (true) {
            BdHistory history = new BdHistory();
            history.setHistoryDate(DateUtil.date());
            historyService.addHistory(history);

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}