package com.rainbowsea.rainbowsealiving.commodity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品 spu 信息介绍
 * 
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-24 20:46:13
 */
@Data
@TableName("commodity_spu_info_desc")
public class SpuInfoDescEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品 id
	 * 因为 commodity_spu_info_desc 表的 id 不是自增长的，而是我们指定的
	 * 因此，我们这里给 spuId 标识上 @TableId(type = IdType.INPUT)
	 * , 否则底层的 sql 语句时不会生成添加 supId 的 sql 语句(可以通过日志输出看看)
	 */
	@TableId(type = IdType.INPUT)
	private Long spuId;
	/**
	 * 商品介绍图片
	 */
	private String decript;

}
