package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String , Object> map = new HashMap<>();
        map.put("category_name","石盾的商品");
        map.put("category_type",12);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1, result);
    }

    @Test
    public void insertByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("石盾1111");
        productCategory.setCategoryType(13);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1, result);
    }

    @Test
    public void findByCategoryType() {
        ProductCategory productCategory =  mapper.findByCategoryType(13);
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void findByCategoryName() {
        List<ProductCategory> productCategory =  mapper.findByCategoryName("石盾的商品");
        Assert.assertNotEquals(0, productCategory.size());
    }

    @Test
    public void  updateByCategoryType() {
        int result =  mapper.updateByCategoryType("1111",13);
        Assert.assertEquals(1, result);
    }

    @Test
    public void updateByObjecty() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("石盾1111");
        productCategory.setCategoryType(13);
        int result =  mapper.updateByObjecty(productCategory);
        Assert.assertEquals(1, result);
    }

    @Test
    public void deleteByCategoryType() {
        int result =  mapper.deleteByCategoryType(13);
        Assert.assertEquals(1, result);
    }

    @Test
    public void selectByCategoryType() {
        ProductCategory productCategory = mapper.selectByCategoryType(12);
        Assert.assertNotNull(productCategory);
    }
}