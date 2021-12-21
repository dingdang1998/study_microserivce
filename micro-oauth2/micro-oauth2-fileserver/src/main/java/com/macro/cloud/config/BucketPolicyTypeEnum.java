package com.macro.cloud.config;

import lombok.AllArgsConstructor;

/**
 * @program: study_microservice
 * @description: 桶配置类型枚举
 * @author: dzp
 * @create: 2021-12-21 10:28
 **/
@AllArgsConstructor
public enum BucketPolicyTypeEnum {
    /**
     * 只读
     */
    READ_ONLY("read-only"),
    /**
     * 只写
     */
    WRITE_ONLY("write-only"),
    /**
     * 读写
     */
    READ_AND_WRITE("read-and-write");

    private final String type;

    /**
     * 获取类型
     *
     * @param type
     * @return
     */
    public static BucketPolicyTypeEnum getEnum(String type) {
        BucketPolicyTypeEnum[] values = BucketPolicyTypeEnum.values();
        for (BucketPolicyTypeEnum typeEnum : values) {
            if (typeEnum.type.equals(type)) {
                return typeEnum;
            }
        }

        //默认返回只读
        return READ_ONLY;
    }
}
