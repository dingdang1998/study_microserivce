package com.macro.cloud.result;

/**
 * @program: study_microservice
 * @description: 封装错误码
 * @author: dzp
 * @create: 2021-12-09 16:55
 **/
public interface IErrorCode {

    long getCode();

    String getMessage();
}
