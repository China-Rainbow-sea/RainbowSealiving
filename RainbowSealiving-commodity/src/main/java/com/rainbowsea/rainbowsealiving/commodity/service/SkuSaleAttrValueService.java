package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.SkuSaleAttrValueEntity;

import java.util.Map;

/**
 * sku 的销售属性/值表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-25 09:32:40
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

