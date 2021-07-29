package com.lw.cn.distributed.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author:刘伟
 * @date:2021/7/19 23:08
 * @description: 令牌生成
 */
@Configuration
public class TokenConfig {

    /**
     * 密钥
     */
    private String SIGNING_KEY = "uaa123";
    /**
     * 内存得方式生成普通令牌
     * @return
     */
    //@Bean
    //public TokenStore tokenStore(){
    //    return new InMemoryTokenStore();
    //}

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //对称秘钥，资源服务器使用该秘钥来验证
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
