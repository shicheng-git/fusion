package com.fusion.gateway.utils;

import lombok.Builder;
import lombok.Data;

/**
 * @Program: fusion
 * @Date: 2021/5/30 18:26
 * @Author: shicheng
 * @Description:
 */
@Builder
@Data
public class ResponseResult {

    private int code;
    private String msg;
    private Object data;
}
