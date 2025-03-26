package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu 基本属性值
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 21:40:59
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);
}

