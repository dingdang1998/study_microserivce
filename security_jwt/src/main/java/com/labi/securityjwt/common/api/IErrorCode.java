package com.labi.securityjwt.common.api;

/**
 * 封装API的错误码
 *
 * @author macro
 * @date 2019/4/19
 */
public interface IErrorCode {

    long getCode();

    String getMessage();
}
