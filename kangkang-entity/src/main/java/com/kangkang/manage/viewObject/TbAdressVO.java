package com.kangkang.manage.viewObject;

import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbArea;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: TbAdressVO
 * @Author: shaochunhai
 * @Date: 2021/8/21 4:07 下午
 * @Description: TODO
 */

@Data
public class TbAdressVO extends TbAddress {
    private List<TbArea> areas;

}
