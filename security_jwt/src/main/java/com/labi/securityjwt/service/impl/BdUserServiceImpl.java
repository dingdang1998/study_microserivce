package com.labi.securityjwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.labi.securityjwt.common.utils.JwtTokenUtil;
import com.labi.securityjwt.component.CustomPasswordEncoder;
import com.labi.securityjwt.dto.UserDetailsDto;
import com.labi.securityjwt.entity.BdPermission;
import com.labi.securityjwt.entity.BdUser;
import com.labi.securityjwt.mapper.BdPermissionMapper;
import com.labi.securityjwt.mapper.BdUserMapper;
import com.labi.securityjwt.service.IBdUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author labi
 * @since 2021-12-03
 */
@Service
@Slf4j
public class BdUserServiceImpl extends ServiceImpl<BdUserMapper, BdUser> implements IBdUserService, UserDetailsService {

    @Autowired
    private BdPermissionMapper permissionMapper;
    @Autowired
    private CustomPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public BdUser getUserByUsername(String username) {
        LambdaQueryWrapper<BdUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BdUser::getUsername, username);
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public List<BdPermission> getUserPermissionsByUserId(Long userId) {
        return permissionMapper.getPermissionsByUserId(userId);
    }

    @Override
    public BdUser register(BdUser bdUser) {
        bdUser.setStatus(UserDetailsDto.ENABLE);
        //todo 校验用户名是否唯一
        bdUser.setPassword(passwordEncoder.encode(bdUser.getPassword()));
        save(bdUser);
        return bdUser;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BdUser user = getUserByUsername(username);
        if (user != null) {
            List<BdPermission> permissionList = getUserPermissionsByUserId(user.getId());
            return new UserDetailsDto(user, permissionList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
