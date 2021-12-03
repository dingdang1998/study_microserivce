package com.labi.securityjwt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.labi.securityjwt.entity.BdPermission;
import com.labi.securityjwt.entity.BdUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author labi
 * @since 2021-12-03
 */
public interface IBdUserService extends IService<BdUser> {

    BdUser getUserByUsername(String username);

    List<BdPermission> getUserPermissionsByUserId(Long userId);

    BdUser register(BdUser bdUser);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);
}
