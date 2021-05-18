package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wx.poj.Article;
import wx.poj.ArticleLike;
import wx.service.ArticleLikeService;
import wx.service.ArticleService;
import wx.util.RedisUtil;
import wx.util.Result;
import wx.util.SensitiveFilterUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController
{
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/addarticle")
    public Object addarticle(@Valid Article vo)
    {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date= sdf.format(new Date());
            vo.setLikeCount(0);
            vo.setViewCount(0);
            vo.setCreatedatetime(date);
            if(SensitiveFilterUtil.checkTxt(vo.getContent()).size()>0){
                return new Result(null,"文章发表失败，包含敏感词",1);
            }
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
        log.info(articleId.toString());
        Article article=articleService.getArticleById(articleId);
       // log.info(article.toString());
        return new Result(article,"获取成功",0);
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

    @GetMapping("/getHot")
    public Result getHotArticle(){
        List<Article> articleList=articleService.getHotArticle();
        return new Result(articleList,"获取成功",0);
    }

    @GetMapping("/view")
    public Result viewArticle(String host,Integer articleId){
        String key = "view-"+host+articleId;
        boolean hasKey = redisUtil.hasKey(key);
        int count;
        if(hasKey){
            count= (int) redisUtil.get(key);
            redisUtil.set(key,count+1);
        }else{
            Article article=articleService.getArticleById(articleId);
            int likeCount=article.getLikeCount()+1;
            redisUtil.set(key,likeCount);
        }
        count= (int) redisUtil.get(key);
        return new Result(count,"浏览成功",0);

    }

    @GetMapping("/like")
    public Result likeArticle(Integer articleId,Integer userId){
        String articleLikeKey ="like-"+articleId;
        int count=0;
        if(redisUtil.hasKey(articleLikeKey)){
            if(!redisUtil.sHasKey(articleLikeKey,userId)){
                redisUtil.sSet(articleLikeKey,userId);
            }
            count=redisUtil.sGet(articleLikeKey).size();
        }else{
            List<ArticleLike>articleLikeList=articleLikeService.getUserIdByArticleId(articleId);
            for(ArticleLike articleLike:articleLikeList){
                redisUtil.sSet(articleLikeKey,articleLike.getUserId());
            }
            redisUtil.sSet(articleLikeKey,userId);
            count=redisUtil.sGet(articleLikeKey).size();
        }

        return new Result(count,"点赞成功",0);

    }

    @GetMapping("/unlike")
    public Result unlikeArticle(Integer articleId,Integer userId){
        String articleLikeKey ="like-"+articleId;
        if(redisUtil.sHasKey(articleLikeKey,userId)){
            redisUtil.sSet(articleLikeKey,userId);
        }else{
            return new Result(null,"未点赞无法取消",0);
        }
        int count=redisUtil.sGet(articleLikeKey).size();
        return new Result(count,"取消点赞成功",0);
    }


}
