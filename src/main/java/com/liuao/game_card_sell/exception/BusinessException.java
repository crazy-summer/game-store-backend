package com.liuao.game_card_sell.exception;
/**
 * 基础业务异常类
 */
public class BusinessException extends RuntimeException {
    // 错误码
    private final String code;
    // 错误信息
    private final String message;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    // Getters
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}