package com.rainbowsea.rainbowsealiving.commodity.service.impl;


//import com.alibaba.cloud.context.utils.StringUtils;

import com.rainbowsea.rainbowsealiving.commodity.dao.AttrAttrgroupRelationDao;
import com.rainbowsea.rainbowsealiving.commodity.dao.AttrgroupDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrAttrgroupRelationEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrgroupEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.Query;

import com.rainbowsea.rainbowsealiving.commodity.dao.AttrDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {


    @Resource
    AttrAttrgroupRelationDao relationDao;
    /**
     * 获取某个属性组可以关联的基本属性
     * 1. 如果某个基本属性已经和某个属性组关联了, 就不能再关联
     * 2. 某个属性组可以关联的基本属性，必须是同一个分类
     */

    //装配 AttrGroupDao
    @Resource
    private AttrgroupDao attrgroupDao;
    // 装配AttrAttrgroupRelationDao
    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    /**
     * 删除属性组和属性的关联关系
     */
    @Override
    public void deleteRelation(AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities) {
        relationDao.deleteBatchRelation
                (Arrays.asList(attrAttrgroupRelationEntities));
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getAllowRelationAttr(Map<String, Object> params, Long attrgroupId) {

        // 小伙伴注意： 这里就是涉及多表检索的解决方案.. 通过流式计算stream API

        //1. 通过接收的 属性组id attrgroupId, 得到对应的categoryId
        AttrgroupEntity attrgroupEntity = attrgroupDao.selectById(attrgroupId);
        Long categoryId = attrgroupEntity.getCategoryId();

        // ----- 增加业务需求，排除已经关联的基本属性------

        //(1) 先得到当前categoryId 的所有分组 - commodity_attrgroup表
        List<AttrgroupEntity> group =
                attrgroupDao.selectList(new QueryWrapper<AttrgroupEntity>().eq("category_id", categoryId));
        //收集到上面得到group的对应的属性组id->jdk8 流式计算 stream API
        List<Long> attrGroupIds = group.stream().map((item) -> {
            return item.getId();
        }).collect(Collectors.toList());


        //(2) 再到commodity_attr_attrgroup_relation中，去检索有哪些基本属性已经和上一步得到的属性组关联上
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities =
                attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));
        //收集从上面得到 attrAttrgroupRelationEntities对应attr_id,放入到集合->jdk8 流式计算
        List<Long> attrIds = attrAttrgroupRelationEntities.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2. 通过得到 categoryId, 获取到对应的基本属性
        QueryWrapper<AttrEntity> wrapper =
                new QueryWrapper<AttrEntity>().eq("category_id", categoryId).eq("attr_type", 1);

        //(3) 增加一个排除的前面已经关联过的基本属性即可.

        if (attrIds != null && attrIds.size() != 0) {
            wrapper.notIn("attr_id", attrIds);
        }

        //3. 因为还有支持条件查询，所以考虑携带的检索条件.
        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)) { //如果key有内容
            wrapper.and((obj) -> {
                obj.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        //进行分页查询
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);

    }


    /**
     * 获取 attrgroupId 关联的商品属性(基本属性)
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new
                QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
//得到所有的属性 id
        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
//如果没有关联的属性，返回 null
        if (attrIds == null || attrIds.size() == 0) {
            return null;
        }
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

    /**
     * 保存商品属性信息，同时保存商品属性和他的商品属性组关联关系
     * * @@Transactional 开启事务
     * * @param attr
     *
     * @param attr
     */
    @Override
    @Transactional
    public void saveAttrAndRelation(AttrEntity attr) {
        //1、保存基本数据
        this.save(attr);
//2、保存关联关系, 如果是基本属性，并且属性组 id 不为空
        if (attr.getAttrType() == 1 && attr.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity relationEntity = new
                    AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long categoryId) {
        QueryWrapper<AttrEntity> queryWrapper =
                new QueryWrapper<AttrEntity>().eq("attr_type", 1);
        if (categoryId != 0) {//如果为 0, 表示不将 categoryId 作为查询条件
            queryWrapper.eq("catelog_id", categoryId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isNotBlank(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params), queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }


    /**
     * 这里我们返回商品销售属性信息, 根据 categoryId+分页+查询添加
     *
     * @param params
     * @param categoryId
     * @return
     */
    @Override
    public PageUtils querySaleAttrPage(Map<String, Object> params, Long categoryId) {
        QueryWrapper<AttrEntity> queryWrapper = new
                QueryWrapper<AttrEntity>().eq("attr_type", 0);
        if (categoryId != 0) {//如果为 0, 表示不将 categoryId 作为查询条件
            queryWrapper.eq("category_id", categoryId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }

}