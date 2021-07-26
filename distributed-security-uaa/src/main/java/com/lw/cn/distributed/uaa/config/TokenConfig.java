package com.lw.cn.distributed.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author:刘伟
 * @date:2021/7/19 23:08
 * @description: 令牌生成
 */
@Configuration
public class TokenConfig {
    /**
     * 内存得方式生成普通令牌
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }
}
