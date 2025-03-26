package com.rainbowsea.rainbowsealiving.commodity.controller;



import com.rainbowsea.common.utils.PageUtils;
import com.rainbowsea.rainbowsealiving.commodity.entity.SkuInfoEntity;
import com.rainbowsea.rainbowsealiving.commodity.service.SkuInfoService;
import com.rainbowsea.rainbowsealiving.commodity.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {


    @Resource
    private SkuInfoService skuInfoService;




    // 注意：这里我们使用了一个方法的重载，这个方法多了一个 Model参数
    @RequestMapping("/list.html")
    public String searchList
            (@RequestParam Map<String, Object> params,Model model, HttpServletRequest request) {
        /**
         * 带条件分页查询
         */
        SearchResult searchResult = skuInfoService.querySearchPageByCondition(params);
//将 page 转成前端需要的数据格式[注意，不同前端页面要的结果可能不一样]
//        SearchResult searchResult = new SearchResult();
//        searchResult.setTotal((long) page.getTotalCount());
//        int totalPage = page.getTotalPage();
//        searchResult.setTotalPages(page.getTotalPage());
//        searchResult.setPageNum(page.getCurrPage());
//        searchResult.setCommodity((List<SkuInfoEntity>) page.getList());
//        List<Integer> pageNavs = new ArrayList<>();
//        for (int i = 1; i <= searchResult.getPageNum(); i++) {
//            pageNavs.add(i);
//        }
//        searchResult.setPageNavs(pageNavs);
        model.addAttribute("result", searchResult);
        System.out.println("result= " + searchResult);

        return "list";

    }

    /**
     * 家居网前台检索列表
     * 将页面提交过来的所有请求参数封装成指定的对象
     * @return
     */
    //@RequestMapping("/list.html")
    //public String searchList(@RequestParam Map<String, Object> params) {
    //    return "list";
    //}
}
