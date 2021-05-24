package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.ArticleLikeMapper;
import wx.poj.Article;
import wx.poj.ArticleLike;
import wx.poj.Information;
import wx.poj.User;
import java.util.List;
import javax.annotation.Resource;
@Service
public class ArticleLikeService {
    @Resource
    private ArticleLikeMapper articleLikeMapper;

    public List<ArticleLike>getUserIdByArticleId(int articleId){
        QueryWrapper<ArticleLike>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);
        return articleLikeMapper.selectList(queryWrapper);
    }

    public void insert(ArticleLike articleLike){
        articleLikeMapper.insert(articleLike);
    }



}
