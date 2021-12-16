package com.labi.shardingdao.controller;


import com.labi.shardingdao.entity.BdHistory;
import com.labi.shardingdao.service.IBdHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author labi
 * @since 2021-12-16
 */
@Slf4j
@RestController
@RequestMapping("/bd-history")
public class BdHistoryController {

    @Autowired
    private IBdHistoryService historyService;

    @PostMapping("/add")
    public void addHistory(@RequestBody BdHistory history) {
        historyService.addHistory(history);
        log.info("添加成功：{}", history.toString());
    }
}

