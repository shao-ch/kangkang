package com.kangkang.dao.esstore;

import com.kangkang.ESVO.ESStoreVO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: ESStoreVOReopository
 * @Author: shaochunhai
 * @Date: 2021/12/23 9:13 上午
 * @Description: TODO
 */
@Repository
public interface ESStoreVOReopository extends ElasticsearchRepository<ESStoreVO,Long> {
}
