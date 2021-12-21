package com.macro.cloud.config;

/**
 * @program: study_microservice
 * @description: 获取桶配置接口
 * @author: dzp
 * @create: 2021-12-21 09:25
 **/
public interface IBucketPolicy {

    /**
     * 得到桶配置
     *
     * @param bucketName
     * @return
     */
    String getBucketPolicy(String bucketName);
}
