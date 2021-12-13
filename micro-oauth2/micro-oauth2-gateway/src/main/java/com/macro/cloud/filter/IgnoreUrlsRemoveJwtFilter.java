package com.macro.cloud.filter;

import cn.hutool.core.util.StrUtil;
import com.macro.cloud.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @program: study_microservice
 * @description: 白名单访问时需要移除jwt请求头
 * @author: dzp
 * @create: 2021-12-10 09:10
 **/
@Component
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();

        //swagger认证配置
        //特别注意knife4j的Authorize是将client_id,client_secret放到Authorization中，此时不能去掉jwt
        String tokenUrl = "/auth/oauth/token";
        if (StrUtil.equals(serverWebExchange.getRequest().getPath().toString(), tokenUrl)) {
            return webFilterChain.filter(serverWebExchange);
        }

        //白名单路径移除JWT请求头
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                request = serverWebExchange.getRequest().mutate().header("Authorization", "").build();
                serverWebExchange = serverWebExchange.mutate().request(request).build();
                return webFilterChain.filter(serverWebExchange);
            }
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
