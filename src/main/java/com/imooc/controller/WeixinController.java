package com.imooc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code)
    {
        log.info("进入auth方法-----");
        log.info("code={}",code);
        String url  = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb406354d0c3fb7b8&secret=c5520090b3a5571b90749befbd1a7cd2&code="
                +code +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }

    @GetMapping("/return")
    public String  returnAuth(@RequestParam("Token") String Token)
    {
        log.info("回调方法-----");
        log.info("Token={}",Token);

//        String url  = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code="
//                +code +"&grant_type=authorization_code";
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(url, String.class);
//        log.info("response={}", response);
        return Token;
    }
}
