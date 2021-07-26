package com.lw.cn.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author:刘伟
 * @date:2021/7/19 22:51
 * @description:
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 授权码模式
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();//内存方式
    }

    /**
     * 配置客户端详情信息服务
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("c1")//客户端ID
                .secret(new BCryptPasswordEncoder().encode("secret"))//客户端密钥
                .resourceIds("res1")//可以访问得资源列表
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")//允许得授权类型
                .scopes("all")//允许得授权范围
                .autoApprove(false)//false  跳转到授权页面  true 直接发令牌
                .redirectUris("http://www.baidu.com");//回调地址
    }

    /**
     * 令牌访问端点
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器
                .authorizationCodeServices(authorizationCodeServices)//授权码模式
                .tokenServices(tokenServices())//下面得令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//允许POST方式提交
    }

    /**
     * 令牌服务
     *
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(clientDetailsService);//客户端信息服务
        defaultTokenServices.setSupportRefreshToken(true);//是否产生刷新令牌
        defaultTokenServices.setTokenStore(tokenStore);//令牌存储策略
        defaultTokenServices.setAccessTokenValiditySeconds(7200);//令牌默认有效期 2小时
        defaultTokenServices.setRefreshTokenValiditySeconds(259200);//刷新令牌默认有效期 3天
        return defaultTokenServices;
    }

    /**
     * 令牌访问端点得安全策略
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") // oauth/token_key 提供公钥端点公开
                .checkTokenAccess("permitAll()")// oauth/check_key 用于资源服务访问得令牌解析端点WebSecurityConfig
                .allowFormAuthenticationForClients();//运行表单认证
    }
}
