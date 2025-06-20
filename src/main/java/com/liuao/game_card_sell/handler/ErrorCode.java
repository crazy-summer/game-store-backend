package com.liuao.game_card_sell.handler;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 通用成功码
    SUCCESS(20000, "操作成功", HttpStatus.OK),

    // 参数错误
    PARAM_FORMAT_ERROR(40001, "参数格式错误", HttpStatus.BAD_REQUEST),
    PARAM_MISSING(40002, "缺少必选参数", HttpStatus.BAD_REQUEST),

    // 认证授权
    UNAUTHORIZED(41001, "未登录", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(41002, "Token已过期", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(41003, "权限不足", HttpStatus.FORBIDDEN),

    // 资源错误
    RESOURCE_NOT_FOUND(42001, "资源不存在", HttpStatus.NOT_FOUND),
    RESOURCE_CONFLICT(42002, "资源冲突", HttpStatus.CONFLICT),

    // 业务错误
    INSUFFICIENT_BALANCE(50001, "余额不足", HttpStatus.FORBIDDEN),

    // 系统错误
    INTERNAL_ERROR(60001, "服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    // Getters
    public int getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getHttpStatus() { return httpStatus; }
}