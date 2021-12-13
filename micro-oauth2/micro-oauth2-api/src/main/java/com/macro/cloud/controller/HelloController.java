package com.macro.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author macro
 * @date 2020/6/19
 */
@RestController
@Api(value = "测试接口", tags = "测试接口")
public class HelloController {

    @GetMapping("/hello")
    @ApiOperation(value = "hello接口")
    public String hello() {
        return "Hello World.";
    }

}
