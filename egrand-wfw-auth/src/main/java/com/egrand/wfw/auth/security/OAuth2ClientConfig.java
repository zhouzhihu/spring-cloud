package com.egrand.wfw.auth.security;

import feign.RequestInterceptor;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;


@EnableOAuth2Client
@EnableConfigurationProperties
@Configuration
public class OAuth2ClientConfig {
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate() {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(this.oAuth2ProtectedResourceDetails);
        oAuth2RestTemplate.setRequestFactory(generateHttpRequestFactory());
        return oAuth2RestTemplate;
    }

    @Resource
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        return details;
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate(@Qualifier("clientHttpRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory) {
        OAuth2RestTemplate template = new OAuth2RestTemplate((OAuth2ProtectedResourceDetails) clientCredentialsResourceDetails());
        template.setRequestFactory(generateHttpRequestFactory());
        return template;
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(@Qualifier("feignOauth2RequestInterceptor") FeignOauth2RequestInterceptor requestInterceptor) {
        return (RequestInterceptor) requestInterceptor;
    }


    private ClientHttpRequestFactory generateHttpRequestFactory() {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, (TrustStrategy) acceptingTrustStrategy).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, (HostnameVerifier) new NoopHostnameVerifier());
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLSocketFactory((LayeredConnectionSocketFactory) connectionSocketFactory);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient((HttpClient) httpClient);
        return (ClientHttpRequestFactory) factory;
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }


    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        return (ClientHttpRequestFactory) new HttpComponentsClientHttpRequestFactory(httpClient());
    }


    private HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = null;
        try {
            registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", new SSLConnectionSocketFactory(createIgnoreVerifySSL())).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setValidateAfterInactivity(2000);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(65000).setConnectTimeout(5000).setConnectionRequestTimeout(1000).build();
        return (HttpClient) HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager((HttpClientConnectionManager) connectionManager).build();
    }

//    private static SSLConnectionSocketFactory trustAllHttpsCertificates() {
//        SSLConnectionSocketFactory socketFactory = null;
//        TrustManager[] trustAllCerts = new TrustManager[1];
//        miTM miTM = new miTM();
//        trustAllCerts[0] = (TrustManager) miTM;
//        SSLContext sc = null;
//        try {
//            sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, null);
//            socketFactory = new SSLConnectionSocketFactory(sc, (HostnameVerifier) NoopHostnameVerifier.INSTANCE);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        return socketFactory;
//    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }
}
