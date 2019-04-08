package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 廖师兄
 * 2017-05-07 14:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest2()
    {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional //在测试里使用数据不插入数据库 事物完全回滚
//    @Rollback(false) //不加则数据回回滚 无法插入数据库
    public void saveTest() {
        ProductCategory productCategory2 = repository.findOne(10);
        System.out.println(productCategory2.toString());
        productCategory2.setCategoryType(10);
        repository.save(productCategory2);

        ProductCategory productCategory = new ProductCategory("测试名字", 12);
//        productCategory.setCategoryName();
//        productCategory.setCategoryType();

        System.out.println(productCategory.toString());
        ProductCategory productCategory1 = repository.save(productCategory);
        System.out.println(productCategory1.toString());
        Assert.assertNotNull(productCategory1);
//        Assert.assertNotEquals(null, productCategory1);

//        Assert.assertNotEquals();
//        ProductCategory productCategory = new ProductCategory("男生最爱", 4);
//        ProductCategory result = repository.save(productCategory);
//        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findByCategoryTypeInTest() {
//        List<Integer> list = Arrays.asList(2,3,4);
//
//        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
//        Assert.assertNotEquals(0, result.size());
        List<Integer> list = Arrays.asList(6, 7, 10);
        List<ProductCategory> p1 = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, p1.size());
    }
}