package com.kangkang.ERP;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbSpecification;
import com.kangkang.store.viewObject.TbSpecificationVO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @InterfaceName: SpecificationFeign  规格参数的远程调用类
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:25 下午
 * @Description: TODO
 */
public interface SpecificationFeign {

    /**
     * 添加商品规格
     * @param tbSpecificationVO
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "spec/addSpecification/")
    void addSpecification(@RequestBody TbSpecification tbSpecification);

    /**
     * 通过id删除规格
     * @param id
     */
    @LoadBalanced
    @PostMapping(value = "spec/updateSpecificationById/")
    void updateSpecificationById(@RequestBody TbSpecification tbSpecification);


    /**
     * 查询商品规格
     * @param tbSpecificationVO
     */
    @LoadBalanced
    @PostMapping(value = "spec/querySpecification/")
    Page<TbSpecification> querySpecification(@RequestBody TbSpecificationVO tbSpecificationVO);


    /**
     * 批量删除规格
     *
     * @param ids id的集合
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "spec/deleteSpecification/")
    void deleteSpecification(@RequestBody List<Long> ids);
}
