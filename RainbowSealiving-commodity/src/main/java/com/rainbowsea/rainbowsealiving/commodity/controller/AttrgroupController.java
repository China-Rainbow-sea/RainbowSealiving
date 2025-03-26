package com.rainbowsea.rainbowsealiving.commodity.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrAttrgroupRelationEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrAttrgroupRelationService;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrService;
import com.rainbowsea.rainbowsealiving.commodity.service.CategoryService;
import com.rainbowsea.rainbowsealiving.commodity.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowsea.rainbowsealiving.commodity.entity.AttrgroupEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrgroupService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.R;

import javax.annotation.Resource;


/**
 * 家居商品属性分组
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-15 14:41:12
 */
@RestController
@RequestMapping("commodity/attrgroup")
public class AttrgroupController {
    @Resource
    AttrService attrService;
    @Autowired
    private AttrgroupService attrgroupService;
    @Autowired
    private CategoryService categoryService;



    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities) {

        //AttrAttrgroupRelationService 本身就提供批量保存的方法
        attrAttrgroupRelationService.saveBatch(attrAttrgroupRelationEntities);
        return R.ok();
    }



    /**
     * 获取某个分类下的所有属性分组和基本属性
     * @param catelogId
     * @return
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId")Long catelogId) {
//1、查出当前分类下的所有属性分组，
//2、查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos =
                attrgroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data", vos);
    }

    /**
     * 响应删除属性组合属性的关联关系
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrAttrgroupRelationEntity[]
                                    attrAttrgroupRelationEntities) {
        attrService.deleteRelation(attrAttrgroupRelationEntities);
        return R.ok();
    }


    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data", entities);
    }

    /**
     * 根据attrgroupId返回可以关联的基本属性
     */
    @RequestMapping("/{attrgroupId}/attr/allowrelation")
    public R attrAllowRelation(@PathVariable("attrgroupId") Long attrgroupId,
                               @RequestParam Map<String, Object> params) {
        PageUtils page =
                attrService.getAllowRelationAttr(params, attrgroupId);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     * /
     *
     * @RequestMapping("/list") //@RequiresPermissions("commodity:attrgroup:list")
     * public R list(@RequestParam Map<String, Object> params){
     * PageUtils page = attrgroupService.queryPage(params);
     * <p>
     * return R.ok().put("page", page);
     * }
     * <p>
     * <p>
     * /**
     * 列表
     * 说明:
     * 1. 根据业务需求，增加根据分类(第3级分类) + 查询条件+ 分页的API接口/方法
     */
    @RequestMapping("/list/{categoryId}")
//@RequiresPermissions("commodity:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId) {
        PageUtils page = attrgroupService.queryPage(params, categoryId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//@RequiresPermissions("commodity:attrgroup:info")
    public R info(@PathVariable("id") Long id) {
        AttrgroupEntity attrgroup = attrgroupService.getById(id);
// 获取该属性分组对应的 categoryId
        Long categoryId = attrgroup.getCategoryId();
        Long[] cascadedCategoryId = categoryService.getCascadedCategoryId(categoryId);
        attrgroup.setCascadedCategoryId(cascadedCategoryId);
        return R.ok().put("attrgroup", attrgroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//@RequiresPermissions("commodity:attrgroup:save")
    public R save(@RequestBody AttrgroupEntity attrgroup) {
        attrgroupService.save(attrgroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//@RequiresPermissions("commodity:attrgroup:update")
    public R update(@RequestBody AttrgroupEntity attrgroup) {
        attrgroupService.updateById(attrgroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//@RequiresPermissions("commodity:attrgroup:delete")
    public R delete(@RequestBody Long[] ids) {
        attrgroupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
