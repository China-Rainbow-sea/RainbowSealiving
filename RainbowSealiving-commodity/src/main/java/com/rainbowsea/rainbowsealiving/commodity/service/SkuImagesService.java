package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.SkuImagesEntity;

import java.util.Map;

/**
 * sku 图片
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-25 09:07:14
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

