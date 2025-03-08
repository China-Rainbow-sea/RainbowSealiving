package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品分类表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-04 16:38:22
 */
public interface CategoryService extends IService<CategoryEntity> {

    /**
     *  返回所有分类及其子分类（层级关系-即树形）
     */
    List<CategoryEntity> listTree();

    PageUtils queryPage(Map<String, Object> params);
}

