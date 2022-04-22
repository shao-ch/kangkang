package com.kangkang.ERP.store.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.ERP.store.service.SpecificationService;
import com.kangkang.store.entity.TbSpecification;
import com.kangkang.store.dtoObject.TbSpecificationDTO;
import com.kangkang.tools.ConverUtils;
import com.kangkang.tools.PageInfo;
import com.kangkang.tools.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SpecificationController  商品规格的信息
 * @Author: shaochunhai
 * @Date: 2022/4/18 11:00 下午
 * @Description: TODO
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class SpecificationController {


    @Autowired
    private SpecificationService specificationService;
    /**
     * 添加/更新商品规格
     * @param json
     * @return
     */
    @ResponseBody
    @PostMapping("/addSpecification")
    public ResponseCode<String> addSpecification(@RequestBody String json){

        ResponseCode<String> save;
        try {
            log .info("===添加商品规格====,传入的参数为："+ JSONObject.toJSONString(json));


            TbSpecificationDTO tbSpecificationDTO = JSONObject.parseObject(json, TbSpecificationDTO.class);
            TbSpecification tbSpecification = new TbSpecification();
            //属性转移
            BeanUtils.copyProperties(tbSpecificationDTO,tbSpecification);

            /**
             * 如果id不为null这就代表是更新规格，那就吧商品
             */
            if (tbSpecification.getId()!=null){
                specificationService.updateSpecificationById(tbSpecification);
                //更新成功
                log.info("=========["+tbSpecification.getRuleName()+"]规格参数个更新成功===========");
                save=ResponseCode.message(200,"更新成功","success");
                return save;
            }

            specificationService.addSpecification(tbSpecification);
            save=ResponseCode.message(200,"添加成功","success");
        } catch (Exception e) {
            log.error("调用失败："+e.getMessage());
            save=ResponseCode.message(500,"登陆失败","服务异常");
        }
        return save;
    }


    /**
     * 查询商品规格
     * @param ruleName  规则名称
     * @param pageIndex 页码
     * @param pageSize  每页查询的数据条数
     * @return
     */


    @GetMapping("/querySpecification")
    public ResponseCode<PageInfo<TbSpecification>> querySpecification(@RequestParam("ruleName") String ruleName,
                                                                      @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
                                                                      @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        Page<TbSpecification> list = null;
        try {
            TbSpecificationDTO tbSpecificationDTO = new TbSpecificationDTO();
            tbSpecificationDTO.setRuleName(ruleName);
            tbSpecificationDTO.setPageIndex(pageIndex);
            tbSpecificationDTO.setPageSize(pageSize);
            log.info("===查询商品规格====,传入的参数为："+JSONObject.toJSONString(tbSpecificationDTO));
            list = specificationService.querySpecification(tbSpecificationDTO);

            //分页转化
            PageInfo<TbSpecification> pageInfo = ConverUtils.pageConver(list);

            //返回数据
            return ResponseCode.message(200, pageInfo, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }



    /**
     * 批量删除规格
     *
     * @param ids id的集合
     * @return
     */
    @DeleteMapping ("/deleteSpecification")
    public ResponseCode<String> deleteSpecification(@RequestBody List<Long> ids) {

        try {
            log.info("=========批量删删除规格=========传入的参数为："+JSONObject.toJSONString(ids));
           specificationService.deleteSpecification(ids);
            return ResponseCode.message(200, "删除成功", "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }
}
