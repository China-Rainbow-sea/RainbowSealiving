package com.rainbowsea.rainbowsealiving.commodity.dao;

import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品 spu 信息
 * 
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 20:31:43
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    void updaSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
	
}
