package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.SkuInfoEntity;
import com.rainbowsea.rainbowsealiving.commodity.vo.SearchResult;

import java.util.Map;

/**
 * sku 信息
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 22:01:45
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    //返回家居网前台,购买用户检索结果
    SearchResult querySearchPageByCondition(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);
}

