package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrgroupEntity;
import com.rainbowsea.rainbowsealiving.commodity.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 家居商品属性分组
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-15 14:41:12
 */
public interface AttrgroupService extends IService<AttrgroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //增加根据家居分类(第三级的)+查询条件+分页 API接口
    // 根据自己的业务逻辑，进行定制
    PageUtils queryPage(Map<String, Object> params, Long categoryId);



    /**
     * getAttrGroupWithAttrsByCatelogId
     根据分类得到该分类下的所有属性分组和分组下的基本属性
     * 1、查出当前分类下的所有属性分组，
     * 2、查出每个属性分组的所有属性
     * @param catelogId
     * @return
     */
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}

