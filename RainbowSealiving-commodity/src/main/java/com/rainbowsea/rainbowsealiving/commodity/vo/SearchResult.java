package com.rainbowsea.rainbowsealiving.commodity.vo;


import com.rainbowsea.rainbowsealiving.commodity.entity.SkuInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

    private String keyword;

    /**
     * 查询到的所有家居商品信息
     */
    private List<SkuInfoEntity> commodity;
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页码
     */
    private Integer totalPages;
    private List<Integer> pageNavs;
}