package com.rainbowsea.rainbowsealiving.commodity.service.impl;

import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.rainbowsea.rainbowsealiving.commodity.dao.CategoryDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    /**
     * 核心方法： 返回所有分类及其子分类（带有层级关系-即树形）
     * 这里我们会使用 java8的，流式计算(stream api) + 递归操作(有一定难度)
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listTree() {
        // 老韩思路分析-步骤:
        // 1. 查出所有的分类数据
        List<CategoryEntity> entities = baseMapper.selectList(null);

        // 2.组装成层级树形结构使用到 Java8 的 stream api + 递归操作
        // 思路:
        // 1.过滤，返回1级分类
        // 2.2 进行 map 映射操作，给每个分类设置对应的子分类(这个过程会使用到递归)
        // 2.3 进行排序 sorted 操作
        // 2.4 将处理好的数据收集/转换到集合

        // 3.返回 带有层级关系数据-即树形
        // 需求：从 List 中过滤出 person.id % 2 != 0 的 person对象
        // list.stream() : 把 List 转成流对象，目的是为了使用流的方法，
        // 这样就可以处理一些比较负载的业务

        List<CategoryEntity> categoryTree =
                entities.stream().filter(categoryEntity -> {
                    // 2.1 过滤filter，返回 1级分类
                    return categoryEntity.getParentId() == 0; // 0 就是一级分类
                }).map(category -> {
                    // 2.2 进行map映射操作，给每个分类设置对应的子分类(这个过程会使用到递归)
                    category.setChildrenCategories(getChildrenCategories(category,entities));
                    return category;
                }).sorted((category1, category2) -> {
                    // 2.3 进行排序sorted 操作，按照 sort 的升序排列
                    return (category1.getSort() == null ? 0 : category1.getSort()) -
                            (category2.getSort() == null ? 0 : category2.getSort());
                }).collect(Collectors.toList());  // // 2.4 将处理好的数据收集 collect/转换到集合中


        // 3. 返回带有层级关系的-即树形
        return categoryTree;
    }


    /**
     * 递归查询所有的分类的子分类
     * * 该方法的任务就是把 root 下的所有子分类的层级关系组织好，并返回。
     * * all 就是所有的分类信息（即上个方法的 entities ）
     *
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> getChildrenCategories(CategoryEntity root,
                                                       List<CategoryEntity> all) {
        // 1.过滤
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentId() == root.getId();
        }).map(categoryEntity -> {
            // 2. 找到子分类，并设置递归
            categoryEntity.setChildrenCategories(getChildrenCategories(categoryEntity, all));
            return categoryEntity;
        }).sorted((category1, category2) -> {
            // 按照 sort 排序-升序
            return (category1.getSort() == null ? 0 : category1.getSort()) -
                    (category2.getSort() == null ? 0 : category2.getSort());
        }).collect(Collectors.toList()); // 将处理好的数据收集 collect/转换到集合中
        return children;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

}