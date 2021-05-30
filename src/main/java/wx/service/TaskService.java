package wx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wx.poj.*;
import wx.util.RedisUtil;
import wx.util.TimeUtil;
import java.util.Date;
import java.text.ParseException;
import java.util.List;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Set;

@Slf4j
@Service
public class TaskService {
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private AskService askService;
    @Resource
    private OrderService orderService;
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleLikeService articleLikeService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MessageService messageService;


    @Scheduled(cron = "0 0 12 * * ?")
    public void checkAskStatusTask() throws ParseException {
        log.info("------------------Ask状态修改定时任务开始----------------");
        List<Ask>askList=askService.getAllAsk();
        for(Ask ask:askList){
            String now=sdf.format(new Date());
            int dayDifferences= TimeUtil.calculateDayDifference(ask.getCreateTime(),now);
            if(dayDifferences>3){
                log.info("修改的ask id:");
                askService.changeStatus(ask.getUserId(),ask.getDoctorId(),0);
            }
        }
        log.info("------------------Ask状态修改定时任务结束----------------");
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void articleLikeTask(){
        log.info("------------------点赞刷回数据库定时任务开始----------------");
        Set<String> keys=redisUtil.keys("*");
        for(String key:keys){
            if(key.startsWith("like")){
                String articleId=key.split("-")[0];
                Integer likeCount= (Integer) redisUtil.get(key);
                Set<Object> userIds=redisUtil.sGet(key);
                for(Object userId:userIds){
                    ArticleLike articleLike=new ArticleLike((Integer) userId,Integer.valueOf(articleId));
                    articleLikeService.insert(articleLike);
                }
                articleService.changeLikeCount(likeCount,Integer.valueOf(articleId));
            }
        }
        log.info("------------------点赞刷回数据库定时任务结束----------------");
    }


    @Scheduled(cron = "0 0 12 * * ?")
    public void articleViewTask(){
        log.info("------------------浏览量刷回数据库定时任务开始----------------");
        Set<String> keys=redisUtil.keys("*");
        for(String key:keys){
            if(key.startsWith("view")){
                String articleId=key.split("-")[0];
                Integer viewCount= (Integer) redisUtil.get(key);
                articleService.changeViewCount(viewCount,Integer.valueOf(articleId));
            }
        }
        log.info("------------------浏览量刷回数据库定时任务结束----------------");

    }



    @Scheduled(cron = "0 0 12 * * ?")
    public void MessageTask() throws ParseException {
        log.info("------------------删除过期消息定时任务开始----------------");
        List<InMessage>messageList=messageService.getAllMessage();
        for(InMessage message:messageList){
            String now=sdf.format(new Date());
            int dayDifferences= TimeUtil.calculateDayDifference(message.getCreateTime(),now);
            if(dayDifferences>30&&message.getIsRead()==0){
                log.info("删除的消息id:");
                messageService.deleteMessageById(message.getId());
            }
        }
        log.info("------------------删除过期消息定时任务结束----------------");
    }




}
