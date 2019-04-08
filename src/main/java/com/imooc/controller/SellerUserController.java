package com.imooc.controller;

import com.imooc.config.ProjectUrl;
import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.dataobject.SellerInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.service.SellerService;
import com.imooc.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrl projectUrl;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String , Object> map) {

        //1.openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_ERROR.getMessage());
            map.put("url", "/sell/seller/order/list");
            return  new ModelAndView("common/error", map);
        }

        //2.设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire= RedisConstant.EXPIRE;
        //1.redis的key 2.redis的value 3.过期时间，4.时间的单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),
                openid, expire, TimeUnit.SECONDS);

        //3.设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
//        Cookie cookie = new Cookie("token", token);
//        cookie.setPath("/");
//        cookie.setMaxAge(7200);
//        response.addCookie(cookie);

        return  new ModelAndView("redirect:" + projectUrl.getSell()
                + "/sell/seller/order/list");

    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String , Object> map) {
        // 1从cookie查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2清楚redis
            redisTemplate.opsForValue().getOperations().delete(String.format(
                    RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3清楚cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null ,0);

        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);

    }

    @GetMapping("/toLogin")
    public ModelAndView toLogin() {
        return new ModelAndView("login/index");
    }
}
