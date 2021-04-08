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

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/commit")
public class CommitController {



    @Resource
    private CommitService commitService;


    @GetMapping("/add")
    public Result addCommit(Integer userId,Integer doctorId,String content){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        Commit commit=new Commit();
        commit.setContent(content);
        commit.setDoctorId(doctorId);
        commit.setUserId(userId);
        commit.setCreateTime(date);
        commitService.addCommit(commit);
        return new Result(null,"新增成功",0);
    }



}
