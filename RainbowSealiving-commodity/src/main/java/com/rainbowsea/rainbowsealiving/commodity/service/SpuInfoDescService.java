package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * 商品 spu 信息介绍
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 20:46:13
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);
}

