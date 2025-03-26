package com.rainbowsea.rainbowsealiving.commodity.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.rainbowsea.rainbowsealiving.commodity.service.SkuInfoService;
import com.rainbowsea.rainbowsealiving.commodity.vo.SpuSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.SpuInfoService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.R;

import javax.annotation.Resource;


/**
 * 商品 spu 信息
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 20:31:43
 */
@RestController
@RequestMapping("commodity/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;



    @Resource
    private SkuInfoService skuInfoService;

    //商品上架
    @PostMapping(value = "/{spuId}/up")
    public R spuUp(@PathVariable("spuId") Long spuId) {
        spuInfoService.up(spuId);
        return R.ok();
    }

    //商品下架
    @PostMapping(value = "/{spuId}/down")
    public R spuDown(@PathVariable("spuId") Long spuId) {
        spuInfoService.down(spuId);
        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
//@RequiresPermissions("commodity:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
//PageUtils page = spuInfoService.queryPage(params);//注销
//换成 带条件查询
//        PageUtils page = spuInfoService.queryPageByCondition(params);
        /**
         * 带条件分页查询
         */
        PageUtils page = skuInfoService.queryPageByCondition(params);
        return R.ok().put("page", page);
    }


    /**
     * 解读
     * 1. 因为保存商品信息，涉及到的表非常多，不是一个 SpuInfoEntity 实体类能包括的. * 2. 将 SpuInfoEntity 改成 我们前面生成的 SpuSaveVo 包含了 json 提交所有信息
     * 3. 我们写一个定制的方法 saveSpuInfo 来完成
     */
    @RequestMapping("/save")
// @RequiresPermissions("commodity:spuinfo:save")
    public R save(@RequestBody SpuSaveVO spuSaveVO) {
        spuInfoService.saveSpuInfo(spuSaveVO);
        return R.ok();
    }

    /**
     * 列表
     */
    //@RequestMapping("/list")
    ////@RequiresPermissions("commodity:spuinfo:list")
    //public R list(@RequestParam Map<String, Object> params){
    //    PageUtils page = spuInfoService.queryPage(params);
    //
    //    return R.ok().put("page", page);
    //}


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:spuinfo:info")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    //@RequestMapping("/save")
    ////@RequiresPermissions("commodity:spuinfo:save")
    //public R save(@RequestBody SpuInfoEntity spuInfo){
    //	spuInfoService.save(spuInfo);
    //
    //    return R.ok();
    //}

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:spuinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
