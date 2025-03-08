package com.rainbowsea.rainbowsealiving.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 家居品牌
 * 
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-08 20:11:04
 */
@Data
@TableName("commodity_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 2L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 品牌名
	 */
	private String name;
	/**
	 * logo
	 */
	private String logo;
	/**
	 * 说明
	 */
	private String description;
	/**
	 * 显示
	 */
	private Integer isshow;
	/**
	 * 检索首字母
	 */
	private String firstLetter;
	/**
	 * 排序
	 */
	private Integer sort;

}
