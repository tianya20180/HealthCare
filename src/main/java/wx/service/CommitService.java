package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.CommitMapper;
import wx.poj.Commit;
import java.util.List;
import javax.annotation.Resource;

@Service
public class CommitService {
    @Resource
    private CommitMapper commitMapper;


    public void addCommit(Commit commit){

        commitMapper.insert(commit);


    }


    public List<Commit> getCommits(Integer doctorId){
        QueryWrapper<Commit>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("doctor_id",doctorId);
        return commitMapper.selectList(queryWrapper);
    }





}
