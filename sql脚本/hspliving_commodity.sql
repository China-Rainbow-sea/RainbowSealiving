CREATE DATABASE hspliving_commodity;
USE hspliving_commodity;

CREATE TABLE `commodity_category` (
`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id', `name` CHAR(50) NOT NULL   COMMENT '名称',
`parent_id` BIGINT NOT NULL COMMENT '父分类 id',
`cat_level` INT NOT NULL COMMENT '层级',
`is_show` TINYINT NOT NULL COMMENT '0 不显示，1 显示]', `sort` INT NOT NULL COMMENT '排序',
`icon` CHAR(255) NOT NULL COMMENT '图标',
`pro_unit` CHAR(50) NOT NULL COMMENT '统计单位', `pro_count` INT NOT NULL COMMENT '商品数量', PRIMARY KEY (`id`)
) CHARSET=utf8mb4 COMMENT='商品分类表';
SELECT * FROM commodity_category

# 测试数据
INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`) 
VALUES (1,'家用电器',0,1,1,0,'','',0)

INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (21,'大 家 电',1,2,1,0,'','',0)


INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (22,'厨卫大电',1,2,1,0,'','',0)

INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (201,'燃气灶',22,3,1,0,'','',0),(202,'油烟机',22,3,1,0,'','',0)


INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (301,'平板电视',21,3,1,0,'','',0)

# 第 2 组 家居家装
INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (2,'家居家装',0,1,1,0,'','',0)

INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (41,'家纺',2,2,1,0,'','',0)

INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (601,'桌布/罩件',41,3,1,0,'','',0)

INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (602,'地毯地垫',41,3,1,0,'','',0)

INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (42,'灯具',2,2,1,0,'','',0)

INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (651,'台灯',42,3,1,0,'','',0)


INSERT   INTO
`commodity_category`(`id`,`name`,`parent_id`,`cat_level`,`is_show`,`sort`,`icon`,`pro_unit`,`pro_count`)
VALUES (652,'节能灯',42,3,1,0,'','',0)



