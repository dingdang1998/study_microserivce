package com.macro.cloud.feign;

import com.macro.cloud.entity.business.BdHistory;
import com.macro.cloud.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @program: study_microservice
 * @description: 远程查询历史数据
 * @author: dzp
 * @create: 2021-12-17 14:27
 **/
@FeignClient(value = "sharding-dao", fallback = HistoryFallbackServiceImpl.class)
public interface HistoryFeignService {

    /**
     * 获取历史数据
     *
     * @return
     */
    @GetMapping("/bd-history/list")
    CommonResult<List<BdHistory>> getList();
}
