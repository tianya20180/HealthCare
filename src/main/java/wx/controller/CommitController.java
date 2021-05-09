package wx.controller;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.poj.Commit;
import wx.service.CommitService;
import wx.util.Result;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/commit")
public class CommitController {



    @Resource
    private CommitService commitService;


    @GetMapping("/add")
    public Result addCommit(String userName,Integer doctorId,String content){
        if(userName==null||doctorId==null||content==null){
            return new Result(null,"内容为空",0);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        Commit commit=new Commit();
        commit.setContent(content);
        commit.setDoctorId(doctorId);
        commit.setUserName(userName);
        commit.setCreateTime(date);
        commitService.addCommit(commit);
        return new Result(null,"新增成功",0);
    }

    @GetMapping("/get")
    public Result getCommits(Integer doctorId){
        if(doctorId==null||doctorId<0){
            return new Result(null,"doctorId错误",1);
        }
        List<Commit>commitList=commitService.getCommits(doctorId);
        return new Result(commitList,"获取评论成功",0);
    }



}
