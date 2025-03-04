package com.rainbowsea.rainbowsealiving.commodity.service.impl;

import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.rainbowsea.rainbowsealiving.commodity.dao.CategoryDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

}