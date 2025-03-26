
# 创建保存 sku 的图片信息表
/*==============================================================*/
/* 1. 保存 某一个 sku 对应的图片[1 个 sku 可能有多个图片]
/*=====================================================*/
USE hspliving_commodity
CREATE TABLE commodity_sku_images
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
sku_id BIGINT COMMENT 'sku_id',
img_url VARCHAR(255) COMMENT '图片地址',
img_sort INT COMMENT '排序', 
default_img INT COMMENT '默认图[0 - 不是默认图，1 - 是默认图]', 
PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='sku 图片';
SELECT * FROM commodity_sku_images