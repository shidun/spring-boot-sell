package com.imooc.form;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductForm {

    private String productId;
    //商品名字
    private String productName;
    //商品价格
    private BigDecimal productPrice;
    //库存
    private Integer productStock;
    //商品描述
    private String productDescription;
    //小图
    private String productIcon;
//    //状态 0正常 1下架
//    private Integer productStatus;
    //类目编号
    private Integer categoryType;

}
