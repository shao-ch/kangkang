package com.kangkang.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MybatisPlusConfig
 * @Author: shaochunhai
 * @Date: 2021/8/12 10:24 上午
 * @Description: TODO
 */

@Configuration
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //这是分页助手
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        //设置数据库类型
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        //设置溢出总页数之后处理，默认不处理
        paginationInnerInterceptor.setOverflow(true);
//        paginationInnerInterceptor.setMaxLimit(true);  这个是设置单页最大数

        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
