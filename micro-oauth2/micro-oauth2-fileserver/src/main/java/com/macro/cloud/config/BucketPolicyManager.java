package com.macro.cloud.config;

/**
 * @program: study_microservice
 * @description: 桶配置管理
 * @author: dzp
 * @create: 2021-12-21 10:24
 **/
public class BucketPolicyManager {

    /**
     * 根据type返回相应的桶配置策略
     * 默认为只读
     *
     * @param type
     * @return
     */
    public static IBucketPolicy factory(BucketPolicyTypeEnum type) {
        switch (type) {
            //默认为只读
            default:
                return new ReadOnlyBucketPolicy();
        }
    }
}
