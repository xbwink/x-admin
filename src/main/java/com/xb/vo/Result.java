package com.xb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xb
 * @description 与前端约束的返回结果类型
 * @create 2023-07-03 14:12
 * @vesion 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code; //返回状态码
    private String message;//描述信息
    private T data;

    public static<T> Result<T> success(T data){
        return new Result<>(20000,"success",data);
    }

    public static<T> Result<T> fail(T data){
        return new Result<>(20001,"fail",data);
    }

    public static<T> Result<T> fail(String message,T data){
        return new Result<>(20001,message,data);
    }

}
