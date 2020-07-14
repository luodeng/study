package com.roden.study.springboot.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class TokenInterceptor implements ClientHttpRequestInterceptor
{
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
    {
        //请求地址
        String checkTokenUrl = request.getURI().getPath();
        //请求方法名 POST、GET等
        String methodName = request.getMethod().name();
        //请求内容
        String requestBody = new String(body);

        //将令牌放入请求header中
        request.getHeaders().add("token","Auth");

        return execution.execute(request, body);
    }
}