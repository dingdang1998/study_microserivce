package com.macro.cloud.config;

import cn.hutool.core.util.StrUtil;

/**
 * @program: study_microservice
 * @description: 只读桶配置
 * @author: dzp
 * @create: 2021-12-21 09:26
 **/
public class ReadOnlyBucketPolicy implements IBucketPolicy {

    /**
     * 只读桶配置json数据
     */
    public final String READ_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\"],\"Resource\":[\"arn:aws:s3:::{}\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::{}\"],\"Condition\":{\"StringEquals\":{\"s3:prefix\":[\"*.*\"]}}},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::{}/*.**\"]}]}";


    @Override
    public String getBucketPolicy(String bucketName) {
        return StrUtil.format(READ_ONLY, bucketName, bucketName, bucketName);
    }
}
