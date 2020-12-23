package com.roden.study.examples.org.apache.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


/**
 * http通信用客户端工具
 *
 */
public class HttpClientUtil {

    //连接超时时间，默认10秒
    private static final int SOCKET_TIMEOUT = 10000;

    //传输超时时间，默认30秒
    private static final int CONNECT_TIMEOUT = 30000;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    //自定义请求器配置
    private RequestConfig requestConfig;

    private static HttpClientUtil hcu = new HttpClientUtil();

    //private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(150000).setConnectTimeout(150000).setConnectionRequestTimeout(150000).build();
    private HttpClientUtil() {
        this.httpClient = HttpClients.createDefault();
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
    }

    /**
     * 获取http客户端
     *
     * @return 客户端对象
     */
    public static HttpClientUtil getSingletonClient() {
        return hcu;
    }

    /**
     * 发送xml格式的数据到服务器
     * without cookie
     *
     * @param xmlPostData 请求数据
     * @param url         请求连接
     * @return 响应数据
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String sendPost(String xmlPostData, String url) throws IOException, ClassNotFoundException {
        // 创建post对象
        HttpPost httpPost = new HttpPost(url);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlPostData, "UTF-8");
        httpPost.addHeader("Content-Type", "text/html");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        response = this.httpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        String xmlStr = EntityUtils.toString(httpEntity, "UTF-8");
        // 丢弃http连接
        response.close();
        return xmlStr;
    }

    @Test
    public void sendPost() throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost("https://www.baidu.com/");// 创建httpPost
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        // 创建默认的httpClient实例.
        httpClient = HttpClients.createDefault();
        httpPost.setConfig(requestConfig);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userId", "123"));
        nameValuePairs.add(new BasicNameValuePair("name", "name"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        // 执行请求
        response = httpClient.execute(httpPost);
        entity = response.getEntity();
        responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);
        // 关闭连接,释放资源
        if (response != null) {
            response.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }
    public static String get(String url) throws Exception, IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse res = client.execute(get);
        HttpEntity entity = res.getEntity();
        if(HttpStatus.SC_OK==res.getStatusLine().getStatusCode()){

        }
        return EntityUtils.toString(entity, "UTF-8");
    }

    public static String doPostByJson(String url,String json,String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //httpPost.setConfig(requestConfig);
            StringEntity entity = new StringEntity(json,"utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
class SSLClient extends DefaultHttpClient{
    public SSLClient() throws Exception{
        super();
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }
}