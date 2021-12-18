package com.macro.cloud.controller;

import com.macro.cloud.entity.business.BdHistory;
import com.macro.cloud.feign.HistoryFeignService;
import com.macro.cloud.result.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: study_microservice
 * @description: 历史记录接口
 * @author: dzp
 * @create: 2021-12-17 14:12
 **/
@RestController
@RequestMapping("/history")
@Api(value = "历史记录接口", tags = "历史记录接口")
public class HistoryController {

    @Autowired
    private HistoryFeignService historyFeignService;

    @GetMapping("/list")
    public CommonResult<List<BdHistory>> getList() {
        return historyFeignService.getList();
    }
}
