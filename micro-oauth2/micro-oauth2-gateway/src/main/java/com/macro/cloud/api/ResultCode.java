package com.macro.cloud.api;

/**
 * @program: study_microservice
 * @description: 枚举一些常用API操作码
 * @author: dzp
 * @create: 2021-12-09 16:56
 **/
public enum ResultCode implements IErrorCode {
    SUCCESS(20000, "操作成功"),
    FAILED(50000, "操作失败"),
    VALIDATE_FAILED(40400, "参数检验失败"),
    UNAUTHORIZED(40100, "暂未登录或token已经过期"),
    FORBIDDEN(40300, "没有相关权限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

