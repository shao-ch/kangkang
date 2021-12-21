package com.kangkang.log.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: EsClientTemplate  ，这个是通过最原始的注入方式，如果是spring-boot-starter-data-elasticsearch这个依赖就不需要这个类了
 * @Author: shaochunhai
 * @Date: 2021/12/14 10:09 上午
 * @Description: TODO
 *
 * FactoryBean  就是从ioc容器中获取bean的对象，
 * InitializingBean  初始化你所需的对象的，可以对实例化的过程中自定义bean对象
 * DisposableBean  这个接口就是在对象销毁的时候有一次回调destroy方法的机会
 */
@Slf4j
//@Component
//@EnableConfigurationProperties(EsProperties.class)
public class EsClientTemplate implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

    @Autowired
    private EsProperties esProperties;
    /**
     * 设置es连接超时时间
     */
    private static int CONNECT_TIMEOUT=60*1000;
    /**
     * 设置socket连接超时时间
     */
    private static int SOCKET_CONNECT_TIMEOUT=5*60*1000;
    /**
     * 设置请求超时时间
     */
    private static int CONNECTION_REQUEST_TIMEOUT_MILLIS=CONNECT_TIMEOUT;

    private RestHighLevelClient restHighLevelClient;
    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restHighLevelClient=buildClient();
    }

    @Override
    public void destroy() throws Exception {

        try {
            if (restHighLevelClient!=null){
                restHighLevelClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("关闭es异常，异常信息为：",e);
        }
    }


    /**
     * 构建es客户端
     * @return
     */
    private RestHighLevelClient buildClient() {

        try {
            /**
             * 获取主机信息，这里可以是集群
             */
//            HttpHost host = HttpHost.create(esProperties.getNode());
            HttpHost host = HttpHost.create("esProperties.getNode()");
            /**
             * 设置请求头
             */
            BasicHeader[] basicHeaders = {new BasicHeader("Accept", "application/json; charset=UTF-8")};

            /**
             * 构建连接,这里可以是集群
             */
            RestClientBuilder builder = RestClient.builder(host);

            /**
             * 如果访问失败了，这里是可以做一些监听，比如日志的打印，或者获取一些参数信息等
             */
            builder.setFailureListener(new RestClient.FailureListener(){
                @Override
                public void onFailure(Node node) {
                    /**
                     * 获取主机信息,ip、端口什么的
                     */
                    HttpHost host1 = node.getHost();

                    /**
                     * 获取参数信息，可以做日志的打印
                     */
                    Map<String, List<String>> attributes = node.getAttributes();

                    log.info("访问失败回调的参数为："+ JSONObject.toJSONString(attributes));
                    /**
                     * 官方解释是正在监听的主机，还不知道啥意思
                     */
                    Set<HttpHost> boundHosts = node.getBoundHosts();
                    log.info("正在监听的主机地址为："+ JSONObject.toJSONString(boundHosts));


                    super.onFailure(node);
                }
            });
            /**
             * 设置连接相关参数,这里的回函数的目的就是为了修改配置数据的，比如超时时间，或者认证啥的。通过上面的builder也是可以直接设置的
             */
            builder.setDefaultHeaders(basicHeaders).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                @Override
                public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder configBuilder) {
                    configBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
                    configBuilder.setConnectTimeout(CONNECT_TIMEOUT);
                    configBuilder.setSocketTimeout(SOCKET_CONNECT_TIMEOUT);
                    return configBuilder;
                }
            });
             restHighLevelClient = new RestHighLevelClient(builder);
             log.info("=====创建esclient成功=====");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建es客户端异常：",e);
        }
        return restHighLevelClient;
    }

}
