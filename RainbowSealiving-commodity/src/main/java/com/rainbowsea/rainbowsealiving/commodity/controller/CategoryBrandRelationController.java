package com.rainbowsea.rainbowsealiving.commodity.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainbowsea.rainbowsealiving.commodity.entity.BrandEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryBrandRelationEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.CategoryBrandRelationService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.R;


/**
 * 品牌分类关联表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-17 17:19:48
 */
@RestController
@RequestMapping("commodity/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;



    /**
     * 说明： 返回某个三级分类的所有品牌
     */
    @GetMapping("/brands/list")
    public R relationBrandsList
    (@RequestParam(value = "catId",required = true)Long catId){
        List<BrandEntity> brandEntities =
                categoryBrandRelationService.getBrandsByCatId(catId);
        return R.ok().put("data",brandEntities);
    }

    /**
     * 获取当前品牌关联的所有分类列表, 注意不是所有的
     */
    @GetMapping("/brand/list")
//@RequiresPermissions("product:categorybrandrelation:list")
    public R categoryBrandlistByBrandId(@RequestParam("brandId") Long brandId) {
        List<CategoryBrandRelationEntity> data =
                categoryBrandRelationService.list(
                        new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId)
                );
        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        //categoryBrandRelationService.save(categoryBrandRelation);
        //1. saveAll 方法是我们自己编写的，在保存分类和品牌关联表数据时，
// 将分类名和品牌名一起保存
        categoryBrandRelationService.saveAll(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
