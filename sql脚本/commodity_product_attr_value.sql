/*====================================================*/
/* 1. 保存商品 spu 基本属性值, 有多个
/*====================================================*/
USE hspliving_commodity
CREATE TABLE commodity_product_attr_value
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id', 
spu_id BIGINT COMMENT '商品 id', 
attr_id BIGINT COMMENT '属性 id', 
attr_name VARCHAR(200) COMMENT '属性名', 
attr_value VARCHAR(200) COMMENT '属性值', 
attr_sort INT COMMENT '顺序', 
quick_show TINYINT COMMENT '快速展示【是否展示在介绍上；0-否 1-是】', 
PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='spu 基本属性值';