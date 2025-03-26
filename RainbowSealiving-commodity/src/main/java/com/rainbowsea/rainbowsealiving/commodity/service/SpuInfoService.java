package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoEntity;
import com.rainbowsea.rainbowsealiving.commodity.vo.SpuSaveVO;

import java.util.Map;

/**
 * 商品 spu 信息
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 20:31:43
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {



    void up(Long spuId);
    void down(Long spuId);

    PageUtils queryPageByCondition(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVO spuSaveVO);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);
}

