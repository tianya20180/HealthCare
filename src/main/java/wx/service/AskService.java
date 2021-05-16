package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import wx.mapper.AskMapper;
import wx.poj.Ask;

import javax.annotation.Resource;

@Service
public class AskService {

    @Resource
    private AskMapper  askMapper;

    public void addAsk(Ask ask){
        askMapper.insert(ask);
    }

    public void changeStatus(Integer userId,Integer doctorId,Integer status){
        UpdateWrapper<Ask> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("userId",userId);
        updateWrapper.eq("doctorId",doctorId);
        updateWrapper.set("status",status);
        askMapper.update(null,updateWrapper);
    }

    public void changeOrder(Integer userId,Integer doctorId,String order){
        UpdateWrapper<Ask> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("userId",userId);
        updateWrapper.eq("doctorId",doctorId);
        updateWrapper.set("order",order);
        askMapper.update(null,updateWrapper);
    }


    public Ask getAsk(Integer userId,Integer doctorId){
        QueryWrapper<Ask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        queryWrapper.eq("doctorId",doctorId);
        return askMapper.selectOne(queryWrapper);
    }
}
