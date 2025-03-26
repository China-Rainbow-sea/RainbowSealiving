package com.rainbowsea.rainbowsealiving.commodity.dao;

import com.rainbowsea.rainbowsealiving.commodity.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性和商品属性组的关联表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-17 19:25:11
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {


    // 增加批量删除属性组和属性的关联关系
    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);


}
