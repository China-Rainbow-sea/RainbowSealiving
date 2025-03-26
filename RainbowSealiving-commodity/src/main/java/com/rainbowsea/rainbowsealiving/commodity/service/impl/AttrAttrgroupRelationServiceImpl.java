package com.rainbowsea.rainbowsealiving.commodity.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.Query;

import com.rainbowsea.rainbowsealiving.commodity.dao.AttrAttrgroupRelationDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrAttrgroupRelationEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

}