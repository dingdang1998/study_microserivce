package com.macro.cloud.result;

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
    FORBIDDEN(40300, "没有相关权限"),
    FEIGN_FALL_BACK(40400, "服务降级返回结果");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

