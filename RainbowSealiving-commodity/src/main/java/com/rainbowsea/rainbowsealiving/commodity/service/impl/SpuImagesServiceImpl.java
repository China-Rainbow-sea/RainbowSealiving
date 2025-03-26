package com.rainbowsea.rainbowsealiving.commodity.service.impl;

import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.ProductAttrValueEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoDescEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrService;
import com.rainbowsea.rainbowsealiving.commodity.service.ProductAttrValueService;
import com.rainbowsea.rainbowsealiving.commodity.vo.BaseAttrs;
import com.rainbowsea.rainbowsealiving.commodity.vo.SpuSaveVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.common.utils.Query;

import com.rainbowsea.rainbowsealiving.commodity.dao.SpuImagesDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuImagesEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.SpuImagesService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {





    @Resource
    SpuImagesService imagesService;
    /**
     * 保存某个 spu 的图片集，就是商品最前面的按一组图片来展示图片的集合
     * @param id
     * @param images
     */
    @Override
    public void saveImages(Long id, List<String> images) {
        System.out.println("saveImage...");
        if(images == null || images.size() == 0){//设置默认图片集
            SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
            spuImagesEntity.setSpuId(id);
            spuImagesEntity.setImgUrl("default1.jpg");
            spuImagesEntity.setDefaultImg(1);
            this.save(spuImagesEntity);
        } else { //如果有，就遍历，批量添加即可
            List<SpuImagesEntity> collect = images.stream().map(img -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(id);
                spuImagesEntity.setImgUrl(img);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }





    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

}