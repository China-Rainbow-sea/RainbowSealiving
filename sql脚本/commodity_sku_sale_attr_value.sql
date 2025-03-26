# 创建保存 sku 的销售属性表
/*====================================================*/
/* 1.保存 sku 的销售属性/值, 一个 sku 可以有多个销售属性/值
/* 2.比如 1 个 sku 有颜色(黑色)和尺寸(100*300)两个销售属性
/*=====================================================*/
USE hspliving_commodity
CREATE TABLE commodity_sku_sale_attr_value
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id', 
sku_id BIGINT COMMENT 'sku_id', 
attr_id BIGINT COMMENT 'attr_id', 
attr_name VARCHAR(200) COMMENT '销售属性名', 
attr_value VARCHAR(200) COMMENT '销售属性值', 
attr_sort INT COMMENT '顺序', PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='sku 的销售属性/值表';

SELECT * FROM commodity_sku_sale_attr_value


SELECT * FROM commodity_spu_info

