package com.rainbowsea.rainbowsealiving.commodity.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.rainbowsea.common.valid.SaveGroup;
import com.rainbowsea.common.valid.UpdateGroup;
import com.rainbowsea.common.valid.UpdateIsShowGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowsea.rainbowsealiving.commodity.entity.BrandEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.BrandService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.R;



/**
 * 家居品牌
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-08 20:11:04
 */
@RestController
@RequestMapping("commodity/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:brand:info")
    public R info(@PathVariable("id") Long id){
		BrandEntity brand = brandService.getById(id);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     * 说明:
     * 1. @Validated 注解的作用就是启用 BrandEntity 字段校验
     * 2. 注解如果没有写 @Validated 这个校验规则不生效
     * 3. BindingResult result: springboot 会将校验的错误放入到 result
     * 4. 程序员可以通过 BindingResult result 将校验得到错误取出，然后进行相应处理
     * 5.如果使用@ControllerAdvice统一接管异常,就不用单独封装返回异常信息了,将其注释
     * 6.异常会统一抛出给@ControllerAdvice类
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:brand:save")
    // @Validated({SaveGroup.class}) 表示调用 save 时，进行参数校验，使用的是 SaveGroup 的校验规则
    public R save(@Validated({SaveGroup.class})  @RequestBody BrandEntity brand/*, BindingResult result*/){

		// 先创建一个 Map，用于收集校验错误
        //Map<String,String> map = new HashMap<>();
        //
        //if (result.hasErrors()) {  // 如果有校验错误
        //    // 遍历 result ，将错误信息收集到 map
        //    result.getFieldErrors().forEach((item) ->{
        //        // 得到 field
        //        String field = item.getField();
        //        // 得到校验错误信息
        //        String message = item.getDefaultMessage();
        //        // 放入 map
        //        map.put(field,message);
        //    });
        //
        //    return R.error(400,"品牌表单数据不合法").put("data",map);
        //} else { // 如果没有校验错误，入库
        //    brandService.save(brand);
        //    return R.ok();
        //}

        brandService.save(brand);
        return R.ok();

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:brand:update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 对 isshow Switch 控件开关上的校验，请求处理。
     * @param brand
     * @return
     */
    @RequestMapping("/update/isshow")
    public R updateIsShow(@Validated({UpdateIsShowGroup.class}) @RequestBody
                                  BrandEntity brand) {
        brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:brand:delete")
    public R delete(@RequestBody Long[] ids){
		brandService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
