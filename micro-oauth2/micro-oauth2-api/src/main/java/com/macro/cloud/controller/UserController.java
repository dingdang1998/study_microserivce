package com.macro.cloud.controller;

import com.macro.cloud.domain.UserDTO;
import com.macro.cloud.holder.LoginUserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取登录用户信息接口
 *
 * @author macro
 * @date 2020/6/19
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理接口", tags = "用户管理接口")
public class UserController {

    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    @ApiOperation(value = "获取当前登录的用户")
    public UserDTO currentUser() {
        return loginUserHolder.getCurrentUser();
    }

}
