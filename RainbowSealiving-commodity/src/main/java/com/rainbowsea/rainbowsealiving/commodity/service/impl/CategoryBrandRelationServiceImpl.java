package com.rainbowsea.rainbowsealiving.commodity.service.impl;

import com.rainbowsea.rainbowsealiving.commodity.dao.BrandDao;
import com.rainbowsea.rainbowsealiving.commodity.dao.CategoryDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.BrandEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.Query;

import com.rainbowsea.rainbowsealiving.commodity.dao.CategoryBrandRelationDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryBrandRelationEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.CategoryBrandRelationService;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {


    @Resource
    CategoryBrandRelationDao relationDao;
    @Resource
    BrandService brandService;
    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {

        //查找 catId 关联的所有品牌
        List<CategoryBrandRelationEntity> categoryBrandRelationEntities
                = relationDao.selectList
                (new QueryWrapper<CategoryBrandRelationEntity>().eq("category_id", catId));
//这里是一个一个 sql 语句发的，也可以收集到所有 brandId, 一次查出返回(后面优化)
        List<BrandEntity> brandEntities =
                categoryBrandRelationEntities.stream().map(item -> {
                    Long brandId = item.getBrandId();
                    BrandEntity byId = brandService.getById(brandId);
                    return byId;
                }).collect(Collectors.toList());
        return brandEntities;
    }

    @Resource
    BrandDao brandDao;
    @Resource
    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAll(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long categoryId = categoryBrandRelation.getCategoryId();
//1、根据 brandId，的品牌的名字
        BrandEntity brandEntity = brandDao.selectById(brandId);
//2、根据 categoryId，的分类的名字
        CategoryEntity categoryEntity = categoryDao.selectById(categoryId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCategoryName(categoryEntity.getName());
//保存
        this.save(categoryBrandRelation);
    }

}