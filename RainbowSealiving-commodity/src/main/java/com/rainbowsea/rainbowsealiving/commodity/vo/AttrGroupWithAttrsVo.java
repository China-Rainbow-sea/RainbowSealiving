package com.rainbowsea.rainbowsealiving.commodity.vo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;
import lombok.Data;
import java.util.List;

/**
 * 如果返回的 json 数据的当前实体类，不能满足需求，则定义一个 VO(View Object)对象
 * 在该对象，可以根据需求组合实体类字段,或者增加，或者删除一些字段
 */

@Data
public class AttrGroupWithAttrsVo {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId
    private Long id;


    /**
     * 组名
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 说明
     */
    private String description;
    /**
     * 组图标
     */
    private String icon;


    /**
     * 所属分类 id
     */
    private Long categoryId;
    private List<AttrEntity> attrs;

}
