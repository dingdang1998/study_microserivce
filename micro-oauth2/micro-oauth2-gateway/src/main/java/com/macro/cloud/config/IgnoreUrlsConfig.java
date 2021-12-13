package com.macro.cloud.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: study_microservice
 * @description: 网关白名单配置
 * @author: dzp
 * @create: 2021-12-10 09:07
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
