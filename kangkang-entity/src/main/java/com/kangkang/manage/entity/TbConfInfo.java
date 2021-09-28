package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName:  TbConfInfo
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-09-22 10:55:03 
 */
@Data
@TableName("tb_conf_info")
public class TbConfInfo {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 配置的key
	 */
    private String confKey;

	/**
	 * 配置的值
	 */
    private String confValue;

	/**
	 * 描述信息
	 */
    private String decription;

	/**
	 * 更新时间
	 */
    private Date updateTime;

	/**
	 * 创建时间
	 */
    private Date createTime;

}
