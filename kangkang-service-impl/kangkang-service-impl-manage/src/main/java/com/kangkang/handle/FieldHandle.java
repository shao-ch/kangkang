package com.kangkang.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName: FieldHandle  实体类中的时间更新，配合fill = FieldFill.INSERT_UPDATE
 *              使用，更新和插入操作就不用管时间字段了
 * @Author: shaochunhai
 * @Date: 2022/2/28 5:00 下午
 * @Description: TODO
 */
@Component
public class FieldHandle implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate", new Date(), metaObject);
        this.setFieldValByName("createDate", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate", new Date(), metaObject);
    }
}
