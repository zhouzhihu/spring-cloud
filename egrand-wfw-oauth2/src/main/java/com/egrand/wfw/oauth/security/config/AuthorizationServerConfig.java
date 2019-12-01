package com.egrand.wfw.oauth.security.config;

import com.egrand.wfw.oauth.security.error.EgdWebResponseExceptionTranslator;
import com.egrand.wfw.oauth.security.service.impl.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer //开启授权服务的功能
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceDetail userServiceDetail;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //ClientDetailsServiceConfigurer配置了客户端的一些基本信息
//        clients.inMemory() //将客户端的信息存储在内存中
//                .withClient("browser") //创建了一个client名为browser的客户端
//                .authorizedGrantTypes("refresh_token", "password")//配置验证类型
//                .scopes("ui")//配置客户端域为“ui”
//                .and()
//                .withClient("service-hi")
//                .secret("123456")
//                .authorizedGrantTypes("client_credentials", "refresh_token","password")
//                .scopes("server");
        clients.withClientDetails(clientDetails());
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        JdbcTokenStore tokenStore=new JdbcTokenStore(dataSource);
//        endpoints
//                .tokenStore(tokenStore)
//                .authenticationManager(authenticationManager) //WebSecurity配置好的
//                .userDetailsService(userServiceDetail);//读取用户的验证信息
        endpoints.tokenStore(redisTokenStore())
                .userDetailsService(userServiceDetail)
                .authenticationManager(authenticationManager);
        endpoints.tokenServices(defaultTokenServices());
        endpoints.exceptionTranslator(webResponseExceptionTranslator());//认证异常翻译
    }

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator(){
        return new EgdWebResponseExceptionTranslator();
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());
        tokenServices.setAccessTokenValiditySeconds(60*60*12); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
        return tokenServices;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //配置获取Token的策略
        oauthServer
                .tokenKeyAccess("permitAll()") //对获取Token的请求不再拦截
                .checkTokenAccess("isAuthenticated()") //验证获取Token的验证信息
                .allowFormAuthenticationForClients();

    }
}
