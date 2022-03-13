package com.kangkang.fileLoad.channel;

import com.kangkang.fileLoad.entity.Message;
import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @ClassName: CacheChannel  缓存的类
 * @Author: shaochunhai
 * @Date: 2022/3/12 9:32 上午
 * @Description: TODO
 */
@Data
public abstract class CacheChannel implements Channel{

    /**
     * 缓存内容 这里用protected 为了子类可以获得
     */
    protected BlockingQueue<Message> queue;



    protected void init(int capacity){
        queue=new LinkedBlockingDeque<Message>(capacity==0?Integer.MAX_VALUE:capacity);
    }

}
