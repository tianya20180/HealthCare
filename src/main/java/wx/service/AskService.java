package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import wx.mapper.AskMapper;
import wx.poj.Ask;
import java.util.*;
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
        updateWrapper.eq("user_id",userId);
        updateWrapper.eq("doctor_id",doctorId);
        updateWrapper.set("status",status);
        askMapper.update(null,updateWrapper);
    }

    public void changeOrder(Integer userId,Integer doctorId,String order){
        UpdateWrapper<Ask> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        updateWrapper.eq("doctor_id",doctorId);
        updateWrapper.set("order_id",order);
        askMapper.update(null,updateWrapper);
    }


    public Ask getAsk(Integer userId,Integer doctorId){
        QueryWrapper<Ask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("doctor_id",doctorId);
        return askMapper.selectOne(queryWrapper);
    }


    public List<Ask>getByUser(Integer userId){
        QueryWrapper<Ask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return askMapper.selectList(queryWrapper);
    }


    public List<Ask>getByDoctor(Integer doctorId){
        QueryWrapper<Ask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("doctor_id",doctorId);
        return askMapper.selectList(queryWrapper);
    }


}
