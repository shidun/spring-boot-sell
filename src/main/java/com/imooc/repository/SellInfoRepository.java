package com.imooc.repository;

import com.imooc.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellInfoRepository extends JpaRepository<SellerInfo, String > {

    SellerInfo findByOpenid(String openid);
}
