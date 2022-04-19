package com.kangkang.ERP.store.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.ERP.store.service.SpecificationService;
import com.kangkang.store.entity.TbSpecification;
import com.kangkang.store.viewObject.TbSpecificationVO;
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
     * @param tbSpecificationVO
     * @return
     */
    @ResponseBody
    @PostMapping("/addSpecification")
    public ResponseCode<String> addSpecification(@RequestBody String json){

        ResponseCode<String> save;
        try {
            log .info("===添加商品规格====,传入的参数为："+ JSONObject.toJSONString(json));


            TbSpecificationVO tbSpecificationVO = JSONObject.parseObject(json, TbSpecificationVO.class);
            TbSpecification tbSpecification = new TbSpecification();
            //属性转移
            BeanUtils.copyProperties(tbSpecificationVO,tbSpecification);

            /**
             * 如果id不为null这就代表是更新规格，那就吧商品
             */
            if (tbSpecification.getId()!=null){
                specificationService.updateSpecificationById(tbSpecification);
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
     *
     * @param tbSpecificationVO 查询规格的入参
     * @return
     */
    @GetMapping("/querySpecification")
    public ResponseCode<Page<TbSpecification>> querySpecification(@RequestBody TbSpecificationVO tbSpecificationVO) {

        Page<TbSpecification> list = null;
        try {
            list = specificationService.querySpecification(tbSpecificationVO);
            return ResponseCode.message(200, list, "success");
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
    @PostMapping ("/deleteSpecification")
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
