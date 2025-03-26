USE hspliving_commodity;


USE hspliving_commodity

CREATE TABLE `commodity_attrgroup`
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
`name` CHAR(20) COMMENT '组名',
sort INT COMMENT '排序',
description VARCHAR(255) COMMENT '说明',
icon VARCHAR(255) COMMENT '组图标',
category_id BIGINT COMMENT '所属分类 id',
PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='家居商品属性分组';

INSERT INTO `commodity_attrgroup` (id,`name`, sort,description,icon,category_id)
VALUES(1, '主体',0,'主体说明','',301)
INSERT INTO `commodity_attrgroup` (id,`name`, sort,description,icon,category_id)
VALUES(2, '规格',0,'规格说明','',301)
INSERT INTO `commodity_attrgroup` (id,`name`, sort,description,icon,category_id)
VALUES(3, '功能',0,'功能说明','',301)

SELECT * FROM `commodity_attrgroup`