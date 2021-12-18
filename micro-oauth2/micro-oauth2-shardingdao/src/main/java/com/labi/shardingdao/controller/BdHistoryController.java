package com.labi.shardingdao.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.labi.shardingdao.service.IBdHistoryService;
import com.macro.cloud.entity.business.BdHistory;
import com.macro.cloud.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public CommonResult<List<BdHistory>> getList() {
        LambdaQueryWrapper<BdHistory> queryWrapper = new LambdaQueryWrapper<>();
        DateTime date = DateUtil.date();
        queryWrapper.le(BdHistory::getHistoryDate, date);
        queryWrapper.ge(BdHistory::getHistoryDate, DateUtil.offsetDay(date, -7));
        return CommonResult.success(historyService.list(queryWrapper));
    }
}

