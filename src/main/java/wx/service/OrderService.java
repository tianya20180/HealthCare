package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import wx.mapper.OrderMapper;
import wx.poj.Order;
import java.util.*;
import javax.annotation.Resource;

public class OrderService {
    @Resource
    private OrderMapper orderMapper;


    public void addOrder(Order order){
        orderMapper.insert(order);
    }

    public List<Order> getAllOrder(){
        QueryWrapper<Order> wrapper=new QueryWrapper();
        return orderMapper.selectList(wrapper);
    }

    public void changeStatus(Integer status){
        UpdateWrapper<Order>wrapper=new UpdateWrapper<Order>();
        wrapper.set("status",status);
        orderMapper.update(null,wrapper);
    }

}
