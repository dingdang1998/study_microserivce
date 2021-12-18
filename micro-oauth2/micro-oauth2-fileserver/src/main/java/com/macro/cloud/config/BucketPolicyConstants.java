package com.macro.cloud.config;

/**
 * @program: study_microservice
 * @description: 桶配置策略常量
 * @author: dzp
 * @create: 2021-12-18 16:55
 **/
public class BucketPolicyConstants {
    /**
     * 只读
     */
    public static final String READ_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\"],\"Resource\":[\"arn:aws:s3:::macro\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::macro\"],\"Condition\":{\"StringEquals\":{\"s3:prefix\":[\"*.*\"]}}},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::macro/*.**\"]}]}";
}
