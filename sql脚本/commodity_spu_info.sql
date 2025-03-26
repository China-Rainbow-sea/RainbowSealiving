/*==============================================================*/
/* 1. 保存商品 spu 信息
/* 2. 1 个 spu 信息可能对于多个 sku
/* 3. 1 个 spu+1 个 sku 就是一个商品的组合关系()
*/
/*==============================================================*/
USE hspliving_commodity

CREATE TABLE commodity_spu_info
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品 id',
spu_name VARCHAR(200) COMMENT '商品名称',
spu_description VARCHAR(1000) COMMENT '商品描述',
catalog_id BIGINT COMMENT '所属分类 id',
brand_id BIGINT COMMENT '品牌 id',
weight DECIMAL(18,4),
publish_status TINYINT COMMENT '上架状态[0 - 下架，1 - 上架]',
create_time DATETIME,
update_time DATETIME,
PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='商品 spu 信息';

SELECT * FROM commodity_spu_info