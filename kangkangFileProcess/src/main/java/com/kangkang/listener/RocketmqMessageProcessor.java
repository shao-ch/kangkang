package com.kangkang.listener;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.enumInfo.RocketInfo;
import com.kangkang.file.dao.esdao.ESStoreVORepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: RocketmqMessageProcessor   评分更新消息处理器
 * @Author: shaochunhai
 * @Date: 2021/9/1 5:24 下午
 * @Description: TODO
 */
@Slf4j
@Component
public class RocketmqMessageProcessor implements MessageListenerConcurrently {

    @Autowired
    private ElasticsearchRestTemplate template;
    /**
     * 因为之前设置的setConsumeMessageBatchMaxSize 为1  所以list里面只有一条数据
     * @param list
     * @param consumeConcurrentlyContext
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        if (list.isEmpty()){
            log.info("没有消息需要处理");
            //直接返回成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        //获取消息,因为之前设置的是一条一条的消费消息，所以这里直接get(0)
        MessageExt messageExt = list.get(0);
        //获取topic
        String topic = messageExt.getTopic();
        //获取tag
        String tags = messageExt.getTags();

        if (topic.equals(RocketInfo.SEND_STORE_SCORE_TOPIC)&&tags.equals(RocketInfo.SEND_STORE_SCORE_TAG)){
            //更新访问量数据
            log.info("==========更新商品访问量数据===========");
            //更新库存数据
            byte[] body = messageExt.getBody();
            String msg = new String(body, StandardCharsets.UTF_8);
            try {
                JSONObject jsonObject = JSONObject.parseObject(msg);
                Set<String> strings = jsonObject.keySet();
                //因为里面只有一条数据,所以可以直接next获取
                String storeId = strings.iterator().next();
                //构建更新条件，我这边是通过商品的类型去更新的，也就是storeId
                UpdateByQueryRequest request = new UpdateByQueryRequest();
                request.indices("kangkang_store_info");
                request.setDocTypes("doc");
                /**这个设置就是说在并发的时候，会存在两个线程同时修改一条数据，这个参数设置就是位了防止冲突
                 * 也就是说在刷新索引的时候，又有修改进来了，或者报错的时候，这里的更新操作已经做了，就不会回滚了
                 */
                request.setConflicts("proceed");
                //设置你要通过什么条件更新的字段
                request.setQuery(new TermQueryBuilder("storeId",storeId));
                /**ScriptType.INLINE  这是是说索要更新的内容在source 里面，还有其他属性  id之类的，或者自定义
                 * 第二个参数：可以是painless，可以是groovy,groovy在2.X以上的版本被替代了，因为有风险，而且还需要配置yml去打开
                 */
                Script script = new Script(ScriptType.INLINE,"painless",
                        "ctx._source.visitCount+="+jsonObject.get(storeId), Collections.EMPTY_MAP);
                //设置脚本，也就是上面的脚本，上面的脚本的意思就是visitCount在愿基础上加上mq传来的数
                request.setScript(script);

                //这里可以设置一次更新多少条数据，下面的两条数量我都不需要
//                request.setSize(10);
                //如果是批量更新，批量更新的数量最大为1000条
//                request.setBatchSize(1000);
                //设置并行数
                request.setSlices(2);
                //更新要刷新消息，防止版本冲突
                request.setRefresh(true);
                // 使用滚动参数来控制“搜索上下文”存活的时间，最好带上，虽然不知道啥意思
                request.setScroll(TimeValue.timeValueMinutes(10));
                //设置超时时间
                request.setTimeout(TimeValue.timeValueSeconds(2));

                //执行更新操作
                template.getClient().updateByQuery(request, RequestOptions.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("==========更新评分数据失败===========["+e+"]");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            log.info("==========更新评分数据数据成功===========");
        }


        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
