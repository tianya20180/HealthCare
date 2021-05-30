package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import wx.mapper.CategoryMapper;
import wx.poj.Category;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public List<Category>getDoctorCategory(){
        QueryWrapper<Category>categoryQueryWrapper=new QueryWrapper<>();
        categoryQueryWrapper.eq("type",0);
        return  categoryMapper.selectList(categoryQueryWrapper);
    }
    public List<Category>getArticleCategory(){
        QueryWrapper<Category>categoryQueryWrapper=new QueryWrapper<>();
        categoryQueryWrapper.eq("type",1);
        return  categoryMapper.selectList(categoryQueryWrapper);
    }
    public Page getAll(Page page){
        return  categoryMapper.selectPage(page,null);
    }

    public void delete(Integer id){
        categoryMapper.deleteById(id);
    }

    public void addCategory(Category category){
        categoryMapper.insert(category);
    }

}
