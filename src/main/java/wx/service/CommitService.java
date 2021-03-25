package wx.service;

import org.springframework.stereotype.Service;
import wx.mapper.CommitMapper;
import wx.poj.Commit;

import javax.annotation.Resource;

@Service
public class CommitService {
    @Resource
    private CommitMapper commitMapper;


    public void addCommit(Commit commit){

        commitMapper.insert(commit);


    }




}
