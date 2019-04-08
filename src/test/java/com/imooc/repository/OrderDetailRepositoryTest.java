package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save()
    {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("11121");
        orderDetail.setOrderId("123431");
        orderDetail.setProductIcon("订2123");
        orderDetail.setProductQuantity(132);
        orderDetail.setProductId("1444");
        orderDetail.setProductName("商品5555");
        orderDetail.setProductPrice(new BigDecimal(51));
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId ()
    {
        List<OrderDetail> orderDetailList = repository.findByOrderId("1234561");
        Assert.assertNotEquals(0, orderDetailList.size());

    }

}