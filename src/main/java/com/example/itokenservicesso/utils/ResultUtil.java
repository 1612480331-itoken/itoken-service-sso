package com.example.itokenservicesso.utils;


import com.example.itokenservicesso.config.baseResult.Result;
import com.example.itokenservicesso.config.exceptionHandle.ResultEnum;
import com.example.itokenservicesso.config.exceptionHandle.UserException;

/**
 * 统一返回接口的工具类
 */
public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static Result error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(),resultEnum.getMsg());
    }
    public static Result error(UserException userException) {
        return error(userException.getCode(),userException.getMessage());
    }
}
