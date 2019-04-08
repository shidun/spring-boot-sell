package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("1234567");
        Assert.assertEquals("1234567", productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
//        List<ProductInfo> productInfoList = productService.findAll();
//        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1232");
        productInfo.setCategoryType(4);
        productInfo.setProductDescription("测试3");
        productInfo.setProductName("商品3");
        productInfo.setProductPrice(new BigDecimal(21.0));
        productInfo.setProductStock(101);
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        ProductInfo productInfo1 = productService.save(productInfo);
        Assert.assertNotNull(productInfo1);
    }

    @Test
    public void Onsale() {
        ProductInfo productInfo = productService.onSale("1232");
        Assert.assertEquals(ProductStatusEnum.UP, productInfo.getProductStatusEnum());
    }

    @Test
    public void Offsale() {
        ProductInfo productInfo = productService.offSale("1232");
        Assert.assertEquals(ProductStatusEnum.DOWN, productInfo.getProductStatusEnum());
    }
}