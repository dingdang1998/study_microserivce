package com.macro.cloud.dto;

import lombok.Data;

/**
 * @program: study_microservice
 * @description: 文件上传结果
 * @author: dzp
 * @create: 2021-12-18 14:53
 **/
@Data
public class MinioUploadDto {
    /**
     * 文件url访问路径
     */
    private String url;
    /**
     * 文件名
     */
    private String name;
}
