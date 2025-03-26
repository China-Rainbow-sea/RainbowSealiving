package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.BrandEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-17 17:19:48
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 1.增加一个保存 commodity_category_brand_relation 表全部字段的方法
     * 2.包括这个表的 brand_name 和 category_name
     *
     * @param categoryBrandRelation
     */
    void saveAll(CategoryBrandRelationEntity categoryBrandRelation);

    /**
     * 增加方法, 返回指定 categoryId 的所有品牌
     */
    List<BrandEntity> getBrandsByCatId(Long catId);
}

