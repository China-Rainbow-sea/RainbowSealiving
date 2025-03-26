/*======================================================*/
/* 1. 保存商品 spu 的介绍图片集, 就是商品最前面的按一组图片来展示图片的集合 , 点
击可以切换图片
/*======================================================*/
USE hspliving_commodity
CREATE TABLE commodity_spu_images
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id', spu_id BIGINT COMMENT 'spu_id',
img_name VARCHAR(200) COMMENT '图片名',
img_url VARCHAR(255) COMMENT '图片地址',
img_sort INT COMMENT '顺序', default_img TINYINT COMMENT '是否默认图', PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='spu 图片集';
SELECT * FROM commodity_spu_images