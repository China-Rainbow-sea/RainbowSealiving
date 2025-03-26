USE hspliving_commodity
CREATE TABLE commodity_attr
(
attr_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '属性 id',
attr_name CHAR(30) COMMENT '属性名',
search_type TINYINT COMMENT '是否需要检索[0-不需要，1-需要]',
icon VARCHAR(255) COMMENT '图标',
value_select CHAR(255) COMMENT '可选值列表[用逗号分隔]',
attr_type TINYINT COMMENT '属性类型[0-销售属性，1-基本属性]',
ENABLE BIGINT COMMENT '启用状态[0 - 禁用，1 - 启用]',
category_id BIGINT COMMENT '所属分类',
show_desc TINYINT COMMENT '快速展示【是否展示在介绍上；0-否 1-是】
',
PRIMARY KEY (attr_id)
)CHARSET=utf8mb4 COMMENT='商品属性表';


SELECT * FROM `commodity_attr`