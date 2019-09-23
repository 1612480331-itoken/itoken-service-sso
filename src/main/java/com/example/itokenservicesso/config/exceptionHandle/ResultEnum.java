package com.example.itokenservicesso.config.exceptionHandle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * 在自定义异常的错误码和信息时，如果过多，没有统一管理，则会出现重复。
 * 使用枚举统一管理code和message：
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    OK(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()),
    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功");
    private Integer code;
    private String msg;
}
