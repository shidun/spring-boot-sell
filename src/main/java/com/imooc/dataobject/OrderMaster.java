package com.imooc.dataobject;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate //自动更新时间
public class OrderMaster {

    //订单id
    @Id
    private String orderId;
    //买家名字
    private String buyerName;
    //买家手机号
    private String buyerPhone;
    //买家地址
    private String buyerAdress;
    //买家微信OpenId
    private String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmout;
    //订单状态，默认为0新下单
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //支付状态，默认为0未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;

//    @Transient
//    private List<OrderDetail> orderDetailList;
}
