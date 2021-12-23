package com.kangkang.file.dao.esdao;

import com.kangkang.ESVO.ESStoreVO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: ESStoreVORepository
 * @Author: shaochunhai
 * @Date: 2021/12/22 8:01 下午
 * @Description: TODO
 */
@Repository
public interface ESStoreVORepository extends ElasticsearchRepository<ESStoreVO,Long> {
}
