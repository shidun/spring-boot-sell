package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "1111122";
    @Test
    public void save()
    {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234561");
        orderMaster.setBuyerName("石盾22");
        orderMaster.setBuyerPhone("123512113");
        orderMaster.setBuyerOpenid("1111122");
        orderMaster.setBuyerAdress("北213民提醒您");
        orderMaster.setOrderAmout(new BigDecimal(120));

        OrderMaster orderMaster1 = repository.save(orderMaster);
        Assert.assertNotNull(orderMaster1);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0, 20);
        Page<OrderMaster> result =  repository.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0, result.getTotalElements());
//        System.out.println(result.getTotalElements());
    }

}