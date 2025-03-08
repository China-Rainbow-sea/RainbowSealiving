package com.rainbowsea.rainbowsealiving.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 商品分类表
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-04 16:38:22
 */
@Data
@TableName("commodity_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 父分类 id
	 */
	private Long parentId;
	/**
	 * 层级
	 */
	private Integer catLevel;
	/**
	 * 0 不显示，1 显示
	 * 1.	@TableLogic(value = "1",delval = "0")
	 * 2. 后面也可以指定那个值表示逻辑删除
	 * 3.如果没有指定，就以 application.yaml 配置的为准。
	 */
	@TableLogic
	private Integer isShow;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 统计单位
	 */
	private String proUnit;
	/**
	 * 商品数量
	 */
	private Integer proCount;


	/**
	 *  增加一个属性，childrenCategories
	 *  1.childrenCategories 表示某个分类的子分类集合
	 *  2.childrenCategories 没有对应表 commodity_category 字段
	 *  3. @TableField(exist = false) 表示 childrenCategories 不对应表的字段
	 */
	@TableField(exist = false)
	private List<CategoryEntity> childrenCategories;

}
