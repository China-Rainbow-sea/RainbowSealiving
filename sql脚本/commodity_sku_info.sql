USE hspliving_commodity
CREATE TABLE commodity_sku_info
(
sku_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'skuId',
spu_id BIGINT COMMENT 'spuId',
sku_name VARCHAR(255) COMMENT 'sku 名称',
sku_desc VARCHAR(2000) COMMENT 'sku 介绍描述',
catalog_id BIGINT COMMENT '所属分类 id',
brand_id BIGINT COMMENT '品牌 id',
sku_default_img VARCHAR(255) COMMENT '默认图片',
sku_title VARCHAR(255) COMMENT '标题',
sku_subtitle VARCHAR(2000) COMMENT '副标题',
price DECIMAL(18,4) COMMENT '价格',
sale_count BIGINT COMMENT '销量', PRIMARY KEY (sku_id)
)CHARSET=utf8mb4 COMMENT='sku 信息';


SELECT * FROM commodity_sku_info