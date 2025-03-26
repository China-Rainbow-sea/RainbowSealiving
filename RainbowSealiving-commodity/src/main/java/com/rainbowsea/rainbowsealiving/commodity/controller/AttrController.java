package com.rainbowsea.rainbowsealiving.commodity.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.R;


/**
 * 商品属性表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-17 19:03:17
 */
@RestController
@RequestMapping("commodity/baseattr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("commodity:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    //@RequestMapping("/save")
    ////@RequiresPermissions("commodity:attr:save")
    //public R save(@RequestBody AttrEntity attr) {
    //    attrService.save(attr);
    //
    //    return R.ok();
    //}

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:attr:update")
    public R update(@RequestBody AttrEntity attr) {
        attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
// @RequiresPermissions("commodity:attr:save")
    public R save(@RequestBody AttrEntity attr) {
        //attrService.save(attr);
        attrService.saveAttrAndRelation(attr);
        return R.ok();

    }


    /**
     * 这里我们返回商品基本属性信息, 根据 categoryId+分页+查询添加
     * @param params
     * @param categoryId
     * @return
     */
    @GetMapping("/base/list/{categoryId}")
    public R baseAttrList(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId){
        PageUtils page = attrService.queryBaseAttrPage(params,categoryId);
        return R.ok().put("page", page);
    }


    @GetMapping("/sale/list/{categoryId}")
    public R saleAttrList(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId){
        System.out.println("sale");
        PageUtils page = attrService.querySaleAttrPage(params,categoryId);
        return R.ok().put("page", page);
    }
}
