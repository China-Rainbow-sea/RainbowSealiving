package com.rainbowsea.rainbowsealiving.commodity.service.impl;

import com.rainbowsea.rainbowsealiving.commodity.entity.AttrEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.ProductAttrValueEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.SkuImagesEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.SkuInfoEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.SkuSaleAttrValueEntity;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoDescEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.AttrService;
import com.rainbowsea.rainbowsealiving.commodity.service.ProductAttrValueService;
import com.rainbowsea.rainbowsealiving.commodity.service.SkuImagesService;
import com.rainbowsea.rainbowsealiving.commodity.service.SkuInfoService;
import com.rainbowsea.rainbowsealiving.commodity.service.SkuSaleAttrValueService;
import com.rainbowsea.rainbowsealiving.commodity.service.SpuImagesService;
import com.rainbowsea.rainbowsealiving.commodity.service.SpuInfoDescService;
import com.rainbowsea.rainbowsealiving.commodity.vo.Attr;
import com.rainbowsea.rainbowsealiving.commodity.vo.BaseAttrs;
import com.rainbowsea.rainbowsealiving.commodity.vo.Images;
import com.rainbowsea.rainbowsealiving.commodity.vo.Skus;
import com.rainbowsea.rainbowsealiving.commodity.vo.SpuSaveVO;
import org.apache.commons.lang.StringUtils;
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

import com.rainbowsea.rainbowsealiving.commodity.dao.SpuInfoDao;
import com.rainbowsea.rainbowsealiving.commodity.entity.SpuInfoEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.SpuInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    /**
     * 商品上架, 目前先直接上架到数据表
     * @param spuId
     */
    @Override
    public void up(Long spuId) {
        this.baseMapper.updaSpuStatus(spuId, 1);
    }
    /**
     * 商品下架, 目前先直接上架到数据表
     * * @param spuId
     * */
    @Override
    public void down(Long spuId) {
     this.baseMapper.updaSpuStatus(spuId, 2);
     }


    @Resource
    AttrService attrService;

    @Resource
    ProductAttrValueService productAttrValueService;

    @Resource
    SkuInfoService skuInfoService;


    @Resource
    SkuImagesService skuImagesService;
    @Resource
    SpuImagesService imagesService;

    @Resource
    SpuInfoDescService spuInfoDescService;


    @Resource
    SkuSaleAttrValueService skuSaleAttrValueService;






    /**
     * 带检索条件来查询 spu 信息
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
//按检索条件-spu 名字
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> { //注意这里的 and 会把后面两个条件括起来
                w.eq("id", key).or().like("spu_name", key);
            });
        }
//加入状态
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("publish_status", status);
        }

        //加入品牌 id
        String brandId = (String) params.get("brandId");
        if(!StringUtils.isEmpty(brandId)&&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }


        //加入分类 id
        String catelogId = (String) params.get("catelogId");
        if(!StringUtils.isEmpty(catelogId)&&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }
//分页查询
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params), wrapper
        );
        return new PageUtils(page);
    }


    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
        //1、保存 spu 基本信息 , 对应的表 commodity_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVO, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
//SpuInfoEntity 信息保存到 commodity_spu_info
        this.saveBaseSpuInfo(spuInfoEntity);
        //2、保存 Spu 的描述图片路径 commodity_spu_info_desc
        List<String> decript = spuSaveVO.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
//获取到刚刚保存的 spu 基本信息对应的 id
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
//注意：这里有可能没有图片, 可以设置一个默认图片
        if (decript.size() == 0) {
            spuInfoDescEntity.setDecript("default.jpg");
        } else {
            spuInfoDescEntity.setDecript(String.join(",", decript));
        }
//保存到 commodity_spu_info_desc, 这个方法时我们完成
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
//3、保存 spu 的图片集 commodity_spu_images
        List<String> images = spuSaveVO.getImages();
//获取到刚刚保存的 spu 基本信息对应的 id
        imagesService.saveImages(spuInfoEntity.getId(), images);
//4 、 保 存 spu 的 基 本 属 性 ( 一 个 spu 可 以 有 多 个 基 本 属 性 / 规 格 参 数 ):commodity_product_attr_value
        List<BaseAttrs> baseAttrs = spuSaveVO.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            AttrEntity id = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(id.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoEntity.getId());
            return valueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(collect);
        //5、保存当前 spu 对应的所有 sku 信息；一个 spu 可以 对应多个 sku, 组成一个可以销售的商品信息

        List<Skus> skus = spuSaveVO.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                String defaultImg = "default.jpg";
                //json 会提交很多图片，如果是当前这个 sku 的默认图片就先保存下 url
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
//保存 sku 的基本信息；到 commodity_sku_info start
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
//把 item 中的信息拷贝给 skuInfoEntity
                BeanUtils.copyProperties(item, skuInfoEntity);
//item 中没有信息，但是 skuInfoEntity 需要的信息，可以从 spuInfoEntity 中获取
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);//初始化销量为 0
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoService.saveSkuInfo(skuInfoEntity);
//保存 sku 的基本信息；到 commodity_sku_info end
                //保存 sku 的图片信息；到 commodity_sku_images start
//一个 sku 可以有多张图片
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img ->
                {
//取出当前 item (就是一个 spk)的信息，组装成 一个 SkuImagesEntity, 进行保存
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
//如果 image 为 empty ,就不过滤掉, 返回 true 就是需要，false 就是剔除
                    return !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);
//保存 sku 的图片信息；到 commodity_sku_images end
//保存 sku 的销售属性-值到 commodity_sku_sale_attr_value start
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities =
                        attr.stream().map(a -> {
                            SkuSaleAttrValueEntity attrValueEntity = new
                                    SkuSaleAttrValueEntity();
                            BeanUtils.copyProperties(a, attrValueEntity);
                            attrValueEntity.setSkuId(skuId);
                            return attrValueEntity;
                        }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);
//保存 sku 的销售属性-值到 commodity_sku_sale_attr_value end
            });

        }
    }

//    @Transactional
//    @Override
//    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
//        //1、保存 spu 基本信息 , 对应的表 commodity_spu_info
//        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
//        BeanUtils.copyProperties(spuSaveVO, spuInfoEntity);
//        spuInfoEntity.setCreateTime(new Date());
//        spuInfoEntity.setUpdateTime(new Date());
////SpuInfoEntity 信息保存到 commodity_spu_info
//        this.saveBaseSpuInfo(spuInfoEntity);
////2、保存 Spu 的描述图片路径 commodity_spu_info_desc
//        List<String> decript = spuSaveVO.getDecript();
//        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
////获取到刚刚保存的 spu 基本信息对应的 id
//        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
////注意：这里有可能没有图片, 可以设置一个默认图片
//        if (decript.size() == 0) {
//            spuInfoDescEntity.setDecript("default.jpg");
//        } else {
//            spuInfoDescEntity.setDecript(String.join(",", decript));
//        }
////保存到 commodity_spu_info_desc, 这个方法时我们完成
//        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
//        //3、保存 spu 的图片集 commodity_spu_images
//        List<String> images = spuSaveVO.getImages();
////获取到刚刚保存的 spu 基本信息对应的 id
//        imagesService.saveImages(spuInfoEntity.getId(), images);
////4 、 保 存 spu 的 基 本 属 性 ( 一 个 spu 可 以 有 多 个 基 本 属 性 / 规 格 参 数 ):commodity_product_attr_value
//        List<BaseAttrs> baseAttrs = spuSaveVO.getBaseAttrs();
//        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
//            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
//            valueEntity.setAttrId(attr.getAttrId());
//            AttrEntity id = attrService.getById(attr.getAttrId());
//            valueEntity.setAttrName(id.getAttrName());
//            valueEntity.setAttrValue(attr.getAttrValues());
//            valueEntity.setQuickShow(attr.getShowDesc());
//            valueEntity.setSpuId(spuInfoEntity.getId());
//            return valueEntity;
//        }).collect(Collectors.toList());
//        productAttrValueService.saveProductAttr(collect);
//
//        //5、保存当前 spu 对应的所有 sku 信息；一个 spu 可以 对应多个 sku, 组成一个可以销售的商品信息.
//        List<Skus> skus = spuSaveVO.getSkus();
//        if (skus != null && skus.size() > 0) {
//            skus.forEach(item -> {
//                String defaultImg = "default.jpg";
////json 会提交很多图片，如果是当前这个 sku 的默认图片就先保存下 url
//                for (Images image : item.getImages()) {
//                    if (image.getDefaultImg() == 1) {
//                        defaultImg = image.getImgUrl();
//                    }
//                }
////保存 sku 的基本信息；到 commodity_sku_info start
//                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
////把 item 中的信息拷贝给 skuInfoEntity
//                BeanUtils.copyProperties(item, skuInfoEntity);
////item 中没有信息，但是 skuInfoEntity 需要的信息，可以从 spuInfoEntity 中获取
//                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
//                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
//                skuInfoEntity.setSaleCount(0L);//初始化销量为 0
//                skuInfoEntity.setSpuId(spuInfoEntity.getId());
//                skuInfoEntity.setSkuDefaultImg(defaultImg);
//                skuInfoService.saveSkuInfo(skuInfoEntity);
////保存 sku 的基本信息；到 commodity_sku_info end
////保存 sku 的图片信息；到 commodity_sku_images start
////一个 sku 可以有多张图片
//                Long skuId = skuInfoEntity.getSkuId();
//                List<SkuImagesEntity> imagesEntities =
//                        item.getImages().stream().map(img -> {
////取出当前 item (就是一个 spk)的信息，组装成 一个 SkuImagesEntity, 进行保存
//
//                            SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
//                            skuImagesEntity.setSkuId(skuId);
//                            skuImagesEntity.setImgUrl(img.getImgUrl());
//                            skuImagesEntity.setDefaultImg(img.getDefaultImg());
//                            return skuImagesEntity;
//                        }).filter(entity -> {
////如果 image 为 empty ,就不过滤掉, 返回 true 就是需要，false 就是剔除
//                            return !StringUtils.isEmpty(entity.getImgUrl());
//                        }).collect(Collectors.toList());
//                skuImagesService.saveBatch(imagesEntities);
////保存 sku 的图片信息；到 commodity_sku_images end
//            });
//        }
//    }
//


//    @Transactional
//    @Override
//    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
//        //1、保存 spu 基本信息 , 对应的表 commodity_spu_info
//        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
//        BeanUtils.copyProperties(spuSaveVO, spuInfoEntity);
//        spuInfoEntity.setCreateTime(new Date());
//        spuInfoEntity.setUpdateTime(new Date());
////SpuInfoEntity 信息保存到 commodity_spu_info
//        this.saveBaseSpuInfo(spuInfoEntity);
////2、保存 Spu 的描述图片路径 commodity_spu_info_desc
//        List<String> decript = spuSaveVO.getDecript();
//        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
////获取到刚刚保存的 spu 基本信息对应的 id
//        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
////注意：这里有可能没有图片, 可以设置一个默认图片
//        if (decript.size() == 0) {
//            spuInfoDescEntity.setDecript("default.jpg");
//        } else {
//            spuInfoDescEntity.setDecript(String.join(",", decript));
//        }
////保存到 commodity_spu_info_desc, 这个方法时我们完成
//        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
//
//        //3、保存 spu 的图片集 commodity_spu_images
//        List<String> images = spuSaveVO.getImages();
////获取到刚刚保存的 spu 基本信息对应的 id
//        imagesService.saveImages(spuInfoEntity.getId(), images);
////4、保存 spu 的基本属性(一个 spu 可以有多个基本属性/规格参数):
//// commodity_product_attr_value
//        List<BaseAttrs> baseAttrs = spuSaveVO.getBaseAttrs();
//        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
//            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
//            valueEntity.setAttrId(attr.getAttrId());
//            AttrEntity id = attrService.getById(attr.getAttrId());
//            valueEntity.setAttrName(id.getAttrName());
//            valueEntity.setAttrValue(attr.getAttrValues());
//            valueEntity.setQuickShow(attr.getShowDesc());
//            valueEntity.setSpuId(spuInfoEntity.getId());
//            return valueEntity;
//        }).collect(Collectors.toList());
//        productAttrValueService.saveProductAttr(collect);
//
//        //5、保存当前 spu 对应的所有 sku 信息；一个 spu 可以 对应多个 sku,
//// 组成一个可以销售的商品信息
////注意 Images 类在 vo 包下, 确保有@Data 修饰
//        List<Skus> skus = spuSaveVO.getSkus();
//        if(skus!=null && skus.size()>0){
//            skus.forEach(item->{
//                String defaultImg = "default.jpg";
////json 会提交很多图片，如果是当前这个 sku 的默认图片就先保存下 url
//                for (Images image : item.getImages()) {
//                    if(image.getDefaultImg() == 1){
//                        defaultImg = image.getImgUrl();
//                    }
//                }
//                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
////把 item 中的信息拷贝给 skuInfoEntity
//                BeanUtils.copyProperties(item,skuInfoEntity);
////item 中没有信息，但是 skuInfoEntity 需要的信息，
////可以从 spuInfoEntity 中获取
//                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
//                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
//                skuInfoEntity.setSaleCount(0L);//初始化销量为 0
//                skuInfoEntity.setSpuId(spuInfoEntity.getId());
//                skuInfoEntity.setSkuDefaultImg(defaultImg);
////1)、保存 sku 的基本信息；到 commodity_sku_info
//                skuInfoService.saveSkuInfo(skuInfoEntity);
//            });
//        }
//    }


//    @Transactional
//    @Override
//    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
//        //1、保存 spu 基本信息 , 对应的表 commodity_spu_info
//        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
//        BeanUtils.copyProperties(spuSaveVO, spuInfoEntity);
//        spuInfoEntity.setCreateTime(new Date());
//        spuInfoEntity.setUpdateTime(new Date());
////SpuInfoEntity 信息保存到 commodity_spu_info
//        this.saveBaseSpuInfo(spuInfoEntity);
////2、保存 Spu 的描述图片路径 commodity_spu_info_desc
//        List<String> decript = spuSaveVO.getDecript();
//        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
////获取到刚刚保存的 spu 基本信息对应的 id
//        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
////注意：这里有可能没有图片, 可以设置一个默认图片
//        if (decript.size() == 0) {
//            spuInfoDescEntity.setDecript("default.jpg");
//        } else {
//            spuInfoDescEntity.setDecript(String.join(",", decript));
//        }
////保存到 commodity_spu_info_desc, 这个方法时我们完成
//        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
//        //3、保存 spu 的图片集 commodity_spu_images
//        List<String> images = spuSaveVO.getImages();
////获取到刚刚保存的 spu 基本信息对应的 id
//        imagesService.saveImages(spuInfoEntity.getId(), images);
////4、保存 spu 的基本属性(一个 spu 可以有多个基本属性/规格参数):
//// commodity_product_attr_value
//        List<BaseAttrs> baseAttrs = spuSaveVO.getBaseAttrs();
//        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
//            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
//            valueEntity.setAttrId(attr.getAttrId());
//            AttrEntity id = attrService.getById(attr.getAttrId());
//            valueEntity.setAttrName(id.getAttrName());
//            valueEntity.setAttrValue(attr.getAttrValues());
//            valueEntity.setQuickShow(attr.getShowDesc());
//            valueEntity.setSpuId(spuInfoEntity.getId());
//            return valueEntity;
//        }).collect(Collectors.toList());
//        productAttrValueService.saveProductAttr(collect);
//    }

        //因为有多个添加操作，使用事务控制
//    @Transactional
//    @Override
//    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
////1、保存 spu 基本信息 , 对应的表 commodity_spu_info
//        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
//        BeanUtils.copyProperties(spuSaveVO,spuInfoEntity);
//        spuInfoEntity.setCreateTime(new Date());
//        spuInfoEntity.setUpdateTime(new Date());
////SpuInfoEntity 信息保存到 commodity_spu_info
//        this.saveBaseSpuInfo(spuInfoEntity);
////2、保存 Spu 的描述图片路径 commodity_spu_info_desc
//        List<String> decript = spuSaveVO.getDecript();
//        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
////获取到刚刚保存的 spu 基本信息对应的 id
//        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
////注意：这里有可能没有图片, 可以设置一个默认图片
//        if(decript.size() == 0) {
//            spuInfoDescEntity.setDecript("default.jpg");
//        }else {
//            spuInfoDescEntity.setDecript(String.join(",", decript));
//        }
////保存到 commodity_spu_info_desc, 这个方法时我们完成
//        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
//
//        //3、保存 spu 的图片集 commodity_spu_images
//        List<String> images = spuSaveVO.getImages();
////获取到刚刚保存的 spu 基本信息对应的 id
//        imagesService.saveImages(spuInfoEntity.getId(), images);
//    }

        @Override
        public PageUtils queryPage (Map < String, Object > params){
            IPage<SpuInfoEntity> page = this.page(
                    new Query<SpuInfoEntity>().getPage(params),
                    new QueryWrapper<SpuInfoEntity>()
            );

            return new PageUtils(page);
        }


        /**
         * 保存 spu 的基本信息到 commodity_spu_info
         *
         * @param spuInfoEntity
         */
        @Override
        public void saveBaseSpuInfo (SpuInfoEntity spuInfoEntity){
            this.baseMapper.insert(spuInfoEntity);
        }

//    @Override
//    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
////1、保存 spu 基本信息 , 对应的表 commodity_spu_info
//        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
//        BeanUtils.copyProperties(spuSaveVO,spuInfoEntity);
//        spuInfoEntity.setCreateTime(new Date());
//        spuInfoEntity.setUpdateTime(new Date());
////2. 将 SpuInfoEntity 信息保存到 commodity_spu_info
//        this.saveBaseSpuInfo(spuInfoEntity);
//    }

    }