/*======================================================*/
/* 1. 保存商品 spu 的介绍图片,单独创建一张表, 可能有多张图片路径(使用,隔开)，也可
以没有*/
/*=====================================================*/
USE hspliving_commodity
CREATE TABLE commodity_spu_info_desc
(
spu_id BIGINT NOT NULL COMMENT '商品 id', decript LONGTEXT COMMENT '商品介绍图片', PRIMARY KEY (spu_id)
)CHARSET=utf8mb4 COMMENT='商品 spu 信息介绍';

SELECT * FROM commodity_spu_info_desc;