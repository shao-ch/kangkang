package com.kangkang.file.dao.esdao;

import com.kangkang.ESVO.ESStoreStatisVO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: EsStoreStatisRepository  tb_store的数据更新到es的关联类
 * @Author: shaochunhai
 * @Date: 2022/2/10 4:57 下午
 * @Description: TODO
 */
@Repository
public interface EsStoreStatisRepository extends ElasticsearchRepository<ESStoreStatisVO,Long> {
}
