package com.macro.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.macro.cloud.config.BucketPolicyConstants;
import com.macro.cloud.dto.MinioUploadDto;
import com.macro.cloud.result.CommonResult;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: study_microservice
 * @description: MinIO对象存储管理
 * @author: dzp
 * @create: 2021-12-18 14:19
 **/
@RestController
@RequestMapping("/minio")
@Slf4j
public class MinioController {

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    public CommonResult<MinioUploadDto> upload(@RequestParam("file") MultipartFile file) {
        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (isExist) {
                log.info("存储桶已经存在！");
            } else {
                //创建存储桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                //创建桶为只读
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                        .bucket(BUCKET_NAME)
                        .config(BucketPolicyConstants.READ_ONLY)
                        .build());
            }

            String originalFilename = file.getOriginalFilename() == null ? "null" : file.getOriginalFilename();
            String dateStr = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
            //文件名
            StringBuilder objectName = new StringBuilder(dateStr).append("_").append(originalFilename);
            //路径
            StringBuilder url = new StringBuilder("/micro/").append(objectName);

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .stream(inputStream, inputStream.available(), -1)
                        .object(objectName.toString())
                        .contentType(file.getContentType())
                        .build());
                log.info("======>文件上传成功，Object:{}，url:{}", objectName.toString(), url.toString());
            } catch (IOException e) {
                log.error("文件读取写出异常:{}", e.getMessage());
            }

            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(objectName.toString());
            minioUploadDto.setUrl(new StringBuilder(ENDPOINT).append("/").append(BUCKET_NAME).append("/").append(objectName.toString()).toString());
            return CommonResult.success(minioUploadDto);

        } catch (Exception e) {
            log.info("上传发生错误: {}！", e.getMessage());
        }
        return CommonResult.failed();
    }


    @GetMapping("/get")
    public CommonResult<String> getCusBucketPolicy() {

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();

            String bucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(BUCKET_NAME).build());
            return CommonResult.success(bucketPolicy);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }

        return CommonResult.failed();
    }
//    /**
//     * 文件删除
//     *
//     * @param objectName
//     * @return
//     */
//    @DeleteMapping("/delete")
//    public CommonResult delete(@RequestParam("objectName") String objectName) {
//        try {
//            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
//            minioClient.removeObject(BUCKET_NAME, objectName);
//            return CommonResult.success(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return CommonResult.failed();
//    }
}
