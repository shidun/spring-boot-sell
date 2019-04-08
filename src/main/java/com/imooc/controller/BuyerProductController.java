package com.imooc.controller;

import com.imooc.VO.ProductInfoVo;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "123")
//    @Cacheable(cacheNames = "product", key = "#SellerId",
//            condition = "#sellerId.length() > 3",
//            unless = "#result.getCode() != 0")
    public ResultVO getList(@RequestParam("sellerId") String SellerId)
//    public ResultVO getList()
        {
            //1.查询所有上架商品
            List<ProductInfo> productInfoList = productService.findUpAll();

            //2.查询类目（一次性查询）
//        List<Integer> categoryList = new ArrayList<>();
//        //传统方法
//        for (ProductInfo productInfo : productInfoList) {
//                categoryList.add(productInfo.getCategoryType());
//        }
            //经典方法(java8 lambda)
            List<Integer> categoryList = productInfoList.stream()
                    .map(e -> e.getCategoryType())
                    .collect(Collectors.toList());
            List<ProductCategory> productCategorieList = categoryService.findByCategoryTypeIn(categoryList);

            //3.数据拼装
            List<ProductVO> productVOList = new ArrayList<>();
                    for (ProductCategory productCategory : productCategorieList) {
                        ProductVO productVO = new ProductVO();
                        productVO.setCategoryType(productCategory.getCategoryType());
                        productVO.setCategoryName(productCategory.getCategoryName());

                        List<ProductInfoVo> productInfoVoList = new ArrayList<>();
                        for (ProductInfo productInfo : productInfoList) {
                            if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                                ProductInfoVo productInfoVo = new ProductInfoVo();
                                BeanUtils.copyProperties(productInfo, productInfoVo);
                                productInfoVoList.add(productInfoVo);
                            }
                }
                productVO.setProductInfoVos(productInfoVoList);
                productVOList.add(productVO);
            }

        //3.数据拼装
//        ResultVO resultVO = new ResultVO();
//
//        resultVO.setData(productVOList);
////        ProductVO productVO = new ProductVO();
////        ProductInfoVo productInfoVo = new ProductInfoVo();
////        productVO.setProductInfoVos(Arrays.asList(productInfoVo));
//        resultVO.setCode(0);
//        resultVO.setMsg("成功s");
////        resultVO.setData(Arrays.asList(productVO));
//        return resultVO;
            return ResultVOUtil.success(productVOList);
    }


}
