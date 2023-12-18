package com.ecommerce.constant.enums;


public enum ResultEnum {
    SUCCESS(200,"success"),
    NOT_LOGIN(4001,"not login"),
    ACCOUNT_ALREADY_EXISTS(4002,"Account already exists"),
    INVALID_PASSWORD(4003,"Invalid password"),
    UNREGISTERED(4004,"unregistered"),
    NO_FOUND(4005,"no found"),
    NOT_BUYER(4006,"not buyer"),
    NOT_SELLER(4007,"not seller"),
    // 库存不足
    NOT_ENOUGH(4008,"not enough"),
    // 地址错误
    ADDRESS_ERROR(4009,"address error"),

    UNKNOWN_ERROR(9999, "未知错误"),
    ;

    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
