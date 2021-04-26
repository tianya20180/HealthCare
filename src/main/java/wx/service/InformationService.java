package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.InformationMapper;
import wx.poj.Information;

import javax.annotation.Resource;

@Service
public class InformationService {


    @Resource
    private InformationMapper informationMapper;

    public void addInformation(Information information){
        informationMapper.insert(information);
    }

    public Information getInformation(String orderId){
        QueryWrapper<Information>queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        return informationMapper.selectOne(queryWrapper);

    }




}
