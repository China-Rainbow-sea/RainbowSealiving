package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrAttrgroupRelationEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-17 19:03:17
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    /**
     * 删除属性组和属性的关联
     */
    void deleteRelation(AttrAttrgroupRelationEntity[]
                                attrAttrgroupRelationEntities);

    /**
     * 保存 商品属性，并同时保存商品属性和所在属性组的关联关系
     *
     * @param attr
     */
    void saveAttrAndRelation(AttrEntity attr);


    /**
     * 获取某个属性组可以关联的基本属性
     * 1. 如果某个基本属性已经和某个属性组关联了, 就不能再关联
     * 2. 某个属性组可以关联的基本属性，必须是同一个分类
     */

    PageUtils getAllowRelationAttr(Map<String, Object> params, Long attrgroupId);

    PageUtils queryBaseAttrPage
            (Map<String, Object> params, Long categoryId);


    /**
     * 这里我们返回商品销售属性信息, 根据 categoryId+分页+查询添加
     *
     * @param params
     * @param categoryId
     * @return
     */
    PageUtils querySaleAttrPage(Map<String, Object> params, Long categoryId);
}

