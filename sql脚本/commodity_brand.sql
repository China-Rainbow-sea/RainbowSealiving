# 家居品牌表
USE hspliving_commodity
CREATE TABLE `commodity_brand`
(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
	`name` CHAR(50) COMMENT '品牌名',
	logo VARCHAR(1200) COMMENT 'logo',
	description LONGTEXT COMMENT '说明',
	isShow TINYINT COMMENT '显示',
	first_letter CHAR(1) COMMENT '检索首字母',
	sort INT COMMENT '排序',
	PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='家居品牌';

# 家居品牌
# sort 排序值, 有时很难确定，不管给什么 int 值,都不是很合适,这里给 null
# 家居品牌测试数据
INSERT INTO `commodity_brand` (id,`name`, logo,description,isShow,first_letter,sort)
VALUES(1, '海信','','',1,'',NULL)
SELECT * FROM `commodity_brand`