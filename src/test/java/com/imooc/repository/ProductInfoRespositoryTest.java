package com.imooc.repository;

import com.imooc.dataobject.ProductInfo;
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
public class ProductInfoRespositoryTest {

    @Autowired
    private ProductInfoRespository respository;

    @Test
    public void save()
    {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123432");
        productInfo.setCategoryType(4);
        productInfo.setProductDescription("测试描述2");
        productInfo.setProductName("商品名字2");
        productInfo.setProductPrice(new BigDecimal(21.0));
        productInfo.setProductStock(101);
        ProductInfo result = respository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> productInfoList = respository.findByProductStatus(0);
        Assert.assertNotEquals(0, productInfoList.size());
    }

}