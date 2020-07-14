package com.roden.study.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate restTemplate(){
        /****************************************************配置底层实现***************************/

        /**
         * 默认底层实现  urlConnection
         */
        //RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        /**
         * 引入jar包 httpclient 可实现替换默认实现
         */
         /*
        //生成一个设置了连接超时时间、请求超时时间、异常最大重试次数的httpClient
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(30000).build();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config).setRetryHandler(new DefaultHttpRequestRetryHandler(5, false));
        HttpClient httpClient = builder.build();
        //使用httpClient创建一个ClientHttpRequestFactory的实现
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        //ClientHttpRequestFactory作为参数构造一个使用作为底层的RestTemplate
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        */

        //RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        /**
         * 引入jar包 okhttp 可实现替换默认实现
         */
        //RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());

        RestTemplate restTemplate = new RestTemplate();

        /****************************************************配置消息转换器***************************/
        //默认 StringHttpMessageConverter处理text/plain;
        //默认 MappingJackson2HttpMessageConverter处理application/json;
        //默认 MappingJackson2XmlHttpMessageConverter处理application/xml

        //获取RestTemplate默认配置好的所有转换器
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //默认的MappingJackson2HttpMessageConverter在第7个 先把它移除掉
        //messageConverters.remove(6);
        //添加GSON的转换器
        //messageConverters.add(6, new GsonHttpMessageConverter());

        /**************************************************** 添加自定义的拦截器 ***************************/

        restTemplate.getInterceptors().add(new TokenInterceptor());
        return restTemplate;
    }
}