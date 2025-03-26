package com.rainbowsea.rainbowsealiving.commodity.controller;


import com.rainbowsea.rainbowsealiving.commodity.entity.CategoryEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.CategoryService;
import com.rainbowsea.rainbowsealiving.commodity.vo.Catalog2Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
 * @author
 * @version 1.0
 */
@Controller
public class IndexController {



    @Resource
    private CategoryService categoryService;

    //返回 json 数据
    @GetMapping(value = "/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        Map<String, List<Catalog2Vo>> catalogJson =
                categoryService.getCatalogJson();
        return catalogJson;
    }


    @GetMapping(value = {"/","index.html"})
    private String indexPage(Model model) {

        //1、查出所有的一级分类
        List<CategoryEntity> categoryEntities =
                categoryService.getLevel1Categorys();
        model.addAttribute("categories",categoryEntities);
     //默认找的是 "classpath\templates\"+"index"+".html"
        // 注意：这里它默认找的是我们自己配置上的路径显示:"classpath\templates\+index+.html"
        // classpath 就是我们当前模块的 resources 根路径下
        return "index";
    }
}