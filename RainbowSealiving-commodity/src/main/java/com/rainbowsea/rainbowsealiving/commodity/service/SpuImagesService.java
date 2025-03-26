package com.rainbowsea.rainbowsealiving.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * spu 图片集
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 21:02:53
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveImages(Long id, List<String> images);
}

