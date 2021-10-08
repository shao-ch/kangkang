package com.kangkang.tools;

import lombok.Data;

/**
 * @ClassName: ResultUtils  静态属性的单例模式   结果类
 * @Author: shaochunhai
 * @Date: 2021/10/8 4:03 下午
 * @Description: TODO
 */
@Data
public class ResultUtils<T> {

    private T data;
    /**
     * 结果状态，0-代表成功，1-代表失败，异常
     */
    private String flag;

    private String error;

    public ResultUtils(T data, String flag, String error) {
        this.data = data;
        this.flag = flag;
        this.error = error;
    }

    private ResultUtils() {
    }

    public static <T> ResultUtils<T> result(T t, String flag,String error){
      return  new ResultUtils<T>(t,flag,error);
    }

}

