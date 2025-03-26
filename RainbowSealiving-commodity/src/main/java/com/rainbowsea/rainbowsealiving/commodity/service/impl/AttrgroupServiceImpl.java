package com.rainbowsea.rainbowsealiving.commodity.service.impl;

import com.alibaba.cloud.context.utils.StringUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrService;
import com.rainbowsea.rainbowsealiving.commodity.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.Query;

import com.rainbowsea.rainbowsealiving.commodity.dao.AttrgroupDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.AttrgroupEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrgroupService;

import javax.annotation.Resource;


@Service("attrgroupService")
public class AttrgroupServiceImpl extends ServiceImpl<AttrgroupDao, AttrgroupEntity> implements AttrgroupService {


    @Resource
    AttrService attrService;
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId)
    {
//1、查询分组信息
        List<AttrgroupEntity> attrGroupEntities = this.list(new
                QueryWrapper<AttrgroupEntity>().eq("category_id", catelogId));
//2、查询所有属性
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group,attrsVo);
            List<AttrEntity> attrs = attrService.getRelationAttr(attrsVo.getId());
            attrsVo.setAttrs(attrs);
            return attrsVo;
        }).collect(Collectors.toList());
        return collect;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrgroupEntity> page = this.page(
                new Query<AttrgroupEntity>().getPage(params),
                new QueryWrapper<AttrgroupEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 增加根据家居分类(第三级的)+查询条件+分页 API接口
     * 可以通过 debug来查看后台的 SQL语句，一目了然
     *
     * @param params
     * @param categoryId
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        // 获取用户进行查询时的关键字
        String key = (String) params.get("key");
        //  QueryWrapper是 renren相关提供的，是用于封装查询条件 /参数
        QueryWrapper<AttrgroupEntity> wrapper =
                new QueryWrapper<AttrgroupEntity>();
        //如果是带条件查询,将条件分组到 wrapper,
        //这里的 id和 name是指的 commodity_attrgroup表的字段
        // 判断 key 是否携带的有查询条件
        //if (!StringUtils.isEmpty(key)) {
        //    wrapper.and((obj) -> {
        //        obj.eq("id", key).or().like("name", key);
        //    });
        //}

        // 判断 key 是否携带的有查询添加-希望他是一组独立检索条件。
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("id", key).or().like("name", key);
            });
        }
        // IPage是 renren提供的工具类,用于分页查询
        //categoryId为 0表示,查询分类属性组时,不加入 categoryId
        //(是我设置的业务逻辑,目前调用,传的 categoryId都不为 0)
        // 否则就加入 And categoryId = xxx
        if (categoryId != 0) {
            wrapper.eq("category_id", categoryId);
        }

        IPage<AttrgroupEntity> page = this.page(
                new Query<AttrgroupEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);

    }
}