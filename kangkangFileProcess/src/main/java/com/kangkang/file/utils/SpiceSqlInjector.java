package com.kangkang.file.utils;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: SpiceSqlInjector   SQL注入器   用于批量插入的插件
 * @Author: shaochunhai
 * @Date: 2021/10/12 3:03 下午
 * @Description: TODO
 */
@Component
public class SpiceSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        // 注意：此SQL注入器继承了DefaultSqlInjector(默认注入器)，调用了DefaultSqlInjector的getMethodList方法，保留了mybatis-plus的自带方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        // 注入InsertBatchSomeColumn
        // 在!t.isLogicDelete()表示不要逻辑删除字段，!"update_time".equals(t.getColumn())表示不要字段名为 update_time 的字段
        methodList.add(new InsertBatchSomeColumn());  //这里表示不对字段进行任何处理
//        methodList.add(new InsertBatchSomeColumn(t -> !t.isLogicDelete() && !"update_time".equals(t.getColumn())));
        return methodList;
    }
}
