package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.Article;
import wx.util.Result;
import wx.poj.Category;
import wx.service.CategoryService;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/category")
public class CategoryController {


    @Resource
    private CategoryService categoryService;

    @GetMapping("/doctor/get")
    public wx.util.Result doctorGet(){
        List<Category>categoryList=categoryService.getDoctorCategory();
        return new Result(categoryList,"获取成功",0);
    }

    @GetMapping("/article/get")
    public wx.util.Result articleGet(){
        List<Category>categoryList=categoryService.getArticleCategory();
        return new Result(categoryList,"获取成功",0);
    }

    @PostMapping("/add")
    public wx.util.Result Add(@RequestBody Category category){
        log.info(category.toString());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        category.setCreateTime(date);
        categoryService.addCategory(category);

        return new Result(null,"添加成功",0);
    }

    @GetMapping("delete")
    public Result delete(Integer id){
        categoryService.delete(id);
        return new Result(null,"删除成功",0);
    }
}
