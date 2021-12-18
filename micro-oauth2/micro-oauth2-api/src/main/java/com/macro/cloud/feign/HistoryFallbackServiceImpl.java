package com.macro.cloud.feign;

import com.macro.cloud.entity.business.BdHistory;
import com.macro.cloud.result.CommonResult;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @program: study_microservice
 * @description: 历史数据服务降级实现类
 * @author: dzp
 * @create: 2021-12-18 09:33
 **/
@Component
public class HistoryFallbackServiceImpl implements HistoryFeignService {

    @Override
    public CommonResult<List<BdHistory>> getList() {
        return CommonResult.feignFallback(Collections.emptyList());
    }
}
