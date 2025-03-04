package com.rainbowsea.rainbowsealiving.commodity.dao;

import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类表
 * 
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-04 16:38:22
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
