package com.labi.securityjwt.component;

import cn.hutool.core.util.StrUtil;
import com.labi.securityjwt.common.utils.JwtTokenUtil;
import com.labi.securityjwt.service.impl.BdUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: study_microservice
 * @description: jwt登录授权过滤器
 * @author: dzp
 * @create: 2021-12-03 14:26
 **/
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private BdUserServiceImpl userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //拿到请求头中的token
        String authHeader = request.getHeader(this.tokenHeader);

        if (StrUtil.isNotBlank(authHeader) && authHeader.startsWith(this.tokenHead)) {
            //截取token The part after "Bearer "
            String authToken = authHeader.substring(this.tokenHead.length());
            //获取token中的用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            log.info("开始校验 username:{}", username);

            if (StrUtil.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("已认证 user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
