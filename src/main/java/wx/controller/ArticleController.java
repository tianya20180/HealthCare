package wx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wx.poj.Article;
import wx.service.ArticleService;
import wx.util.RedisUtil;
import wx.util.Result;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController
{
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/addarticle")
    public Object addarticle(@Valid Article vo)
    {
            vo.setLikeCount(0);
            vo.setViewCount(0);
            articleService.insert(vo);
            return new Result(null,"插入成功",0);
    }

    /*
    @PostMapping("/loadPage")
    public Object loadPage(@Valid Article_Condit vo)
    {
        try
        {
            PageInfo<Article> loadPage = articleService.loadPage(vo);
            return Result.success(loadPage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }*/

    @GetMapping("/getOne")
    public Result getOneArticle(Integer articleId){
        Article article=articleService.getArticleById(articleId);
        return new Result(null,"获取成功",0);
    }

    @GetMapping("/getByDoctorId")
    public Result getByDoctorId(Integer doctorId){
        List<Article> articleList=articleService.getByDoctorId(doctorId);
        return new Result(articleList,"获取成功",0);
    }
    @GetMapping("/getByCategory")
    public Result getByCategory(Integer category){
        List<Article> articleList=articleService.getByDoctorId(category);
        return new Result(articleList,"获取成功",0);
    }


    @GetMapping("/view")
    public Result viewArticle(String host,Integer articleId){
        String key = "view-"+host+articleId;
        boolean hasKey = redisUtil.hasKey(key);
        if(hasKey){
            int count= (int) redisUtil.get(key);
            redisUtil.set(key,count+1);
        }else{
            Article article=articleService.getArticleById(articleId);
            int likeCount=article.getLikeCount()+1;
            redisUtil.set(key,likeCount);
        }
        return new Result(null,"浏览成功",0);

    }

    @GetMapping("/like")
    public Result viewArticle(Integer articleId){
        String key ="like-"+articleId;
        boolean hasKey = redisUtil.hasKey(key);
        if(hasKey){
            int count= (int) redisUtil.get(key);
            redisUtil.set(key,count+1);
        }else{
            Article article=articleService.getArticleById(articleId);
            int likeCount=article.getLikeCount()+1;
            redisUtil.set(key,likeCount);
        }
        return new Result(null,"点赞成功",0);

    }

    @GetMapping("/unlike")
    public Result unlikeArticle(Integer articleId){
        String key ="like-"+articleId;
        boolean hasKey = redisUtil.hasKey(key);
        if(hasKey){
            int count= (int) redisUtil.get(key);
            if(count>0)
                redisUtil.set(key,count-1);
        }else{
            Article article=articleService.getArticleById(articleId);
            int likeCount=article.getLikeCount()-1;
            if(likeCount>=0)
                 redisUtil.set(key,likeCount);
        }
        return new Result(null,"取消点赞成功",0);

    }


}
