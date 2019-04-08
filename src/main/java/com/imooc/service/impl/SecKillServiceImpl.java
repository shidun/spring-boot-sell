package com.imooc.service.impl;

import com.imooc.exception.SellException;
import com.imooc.service.RedisLock;
import com.imooc.service.SecKillService;
import com.imooc.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {
    private static  final int TIMEOUT = 10 * 1000;//超时时间

    @Autowired
    private RedisLock redisLock;

    static Map<String , Integer> products;
    static Map<String , Integer> stock;
    static Map<String , String > orders;

    static
    {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId)
    {
        return "清明活动，烤肉特价，限量"
                + products.get(productId)
                + "还剩：" + stock.get(productId) + "份"
                + "该商品成功下单用户数目："
                + orders.size() + "人";
    }

    public String querySeckKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    public void orderProductMockDiffUser(String productId) {

        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if  (!redisLock.lock(productId, String.valueOf(time))) {
            throw new SellException(101, "啊啊啊啊，人太多了，请重新再试");
        }
        //查询
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "活动结束");
        } else {
            //下单
            orders.put(KeyUtil.getUniquekey(), productId);
            //减库存
            stockNum -=1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        //解锁
        redisLock.unlock(productId, String.valueOf(time));
    }

}
