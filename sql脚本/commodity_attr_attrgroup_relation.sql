1. 创建数据表 , 保存 商品属性(基本属性)和商品属性组的关联关系
/*===================================================*/
/* 1. 商品属性和商品属性组的关联表 */
/*===================================================*/
USE hspliving_commodity
CREATE TABLE commodity_attr_attrgroup_relation
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
attr_id BIGINT COMMENT '属性 id',
attr_group_id BIGINT COMMENT '属性分组 id',
attr_sort INT COMMENT '属性组内排序',
PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='商品属性和商品属性组的关联表';

SELECT * FROM `commodity_attr_attrgroup_relation`