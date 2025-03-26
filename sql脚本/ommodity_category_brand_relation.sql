/*==============================================================*/
/*1. 品牌分类关联表
/*2. 一个分类下可以对应多个品牌，比如 电视，可以对应海信、小米、夏普品牌
/*3. 一个品牌可以对应多个分类，比如 小米品牌，可以对应电视，空调, 电热水器等
/*4. 对于两种表 brand 和 category 是多对多的关系时，通常数据库的设计会搞一个中间
表，关联二者的关系
/*5. 注意，对于大表，会根据需要设计一些冗余字段来提高效率，比如这里的 brand_name
和 category_name
/*==============================================================*/
USE hspliving_commodity
CREATE TABLE commodity_category_brand_relation
(
id BIGINT NOT NULL AUTO_INCREMENT,
brand_id BIGINT COMMENT '品牌 id',
category_id BIGINT COMMENT '分类 id',
brand_name VARCHAR(255) COMMENT '品牌名称',
category_name VARCHAR(255) COMMENT '分类名称',
PRIMARY KEY (id)
)CHARSET=utf8mb4 COMMENT='品牌分类关联表';

SELECT * FROM `commodity_category_brand_relation`
