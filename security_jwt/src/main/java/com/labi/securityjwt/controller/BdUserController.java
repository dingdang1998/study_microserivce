package com.labi.securityjwt.controller;


import com.labi.securityjwt.common.api.CommonResult;
import com.labi.securityjwt.entity.BdUser;
import com.labi.securityjwt.service.IBdUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author labi
 * @since 2021-12-03
 */
@RestController
@RequestMapping("/bd-user")
public class BdUserController {

    @Autowired
    private IBdUserService userService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<BdUser> register(@RequestBody BdUser user) {
        BdUser umsAdmin = userService.register(user);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Map<String, String>> login(@RequestBody BdUser umsAdminLoginParam) {
        String token = userService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('aaa')")
    public CommonResult<List<BdUser>> getUser() {
        List<BdUser> list = userService.list();
        return CommonResult.success(list);
    }
}

