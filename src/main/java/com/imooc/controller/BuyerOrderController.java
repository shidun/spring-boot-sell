package com.imooc.controller;

import com.imooc.VO.ResultVO;
import com.imooc.converter.OrderFrom2OrderDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/buyer/order")
@RestController
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String >> create(OrderForm orderForm, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】 参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderFrom2OrderDTO.covert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO resutl = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", resutl.getOrderId());
        return ResultVOUtil.success(map);
    }


    /**
     * 订单列表
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size)
    {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
//        ResultVO resultVO = new ResultVO();
//        resultVO.setCode(0);
//        return resultVO;
//        return ResultVOUtil.success();
//        orderDTOPage.getTotalElements();//总数
//        orderDTOPage.getTotalPages();//总页数
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail")
    private ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                      @RequestParam("orderId") String orderId)
    {
//        OrderDTO orderDTO = orderService.findOne(orderId);
//        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
//            log.error("权限不足");
//        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancle")
    public ResultVO cancle(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId)
    {
//        OrderDTO orderDTO = orderService.findOne(orderId);
//        //安全改进
//        orderService.cancel(orderDTO);

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }


}
