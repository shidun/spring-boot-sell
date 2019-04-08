package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * http请求的最外层对象
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO <T> implements Serializable {

    private static final long serialVersionUID = -904180148586534937L;

    //错误码
    private Integer code;

    //提示信息
    private String msg = "";

    //返回具体数据
    private T data;
}
