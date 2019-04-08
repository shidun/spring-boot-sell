package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import com.imooc.utils.JsonUtil;
import com.imooc.utils.MathUtil;
import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME="微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
//        WxPayH5Config wxPayH5Config = new WxPayH5Config();
//        wxPayH5Config.setAppId("xxxxx");
//        wxPayH5Config.setAppSecret("xxxxxxxx");
//        wxPayH5Config.setMchId("xxxxxx");
//        wxPayH5Config.setMchKey("xxxxxxx");
//        wxPayH5Config.setNotifyUrl("http://xxxxx");
//
//        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
//
//        bestPayService.setWxPayH5Config(wxPayH5Config);
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmout().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

//        PayRequest payRequest = new PayRequest();
//        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
//        payRequest.setOrderId("123456");
//        payRequest.setOrderName("微信公众账号支付订单");
//        payRequest.setOrderAmount(0.01);
//        payRequest.setOpenid("openid_xxxxxx");
        log.info("【微信支付】发起支付 request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】 发起支付 response ={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名
        //2.微信回调的支付状态
        //3.支付的金额
        //4.支付人（下单人 == 支付人）

        PayResponse payResponse =bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】 异步通知 payResponse={}", JsonUtil.toJson(payResponse));

        //修改订单支付状态
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if (orderDTO == null) {
            log.error("【微信支付】异步通知 订单不存在 orderId={}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致 ( 0.10   0.1)
//        if (!payResponse.getOrderAmount().equals(orderDTO.getOrderAmout())) { //金额数量类型不一致不能用equals
        //转换后由于精度问题不能完全相等
//        if (orderDTO.getOrderAmout().compareTo(new BigDecimal(payResponse.getOrderAmount())) != 0) {
        if (!MathUtil.equals(orderDTO.getOrderAmout().doubleValue(), payResponse.getOrderAmount())) {
            log.error("【微信支付】 异步通知 订单金额不一致 orderId={}，微信通知金额={}，系统订单金额={}",
                    payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmout());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_NOT_EQUAL);
        }
        orderService.paid(orderDTO);

        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmout().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 request={}", JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
