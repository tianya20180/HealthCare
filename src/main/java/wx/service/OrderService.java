package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.OrderMapper;
import wx.poj.Order;
import java.util.*;
import javax.annotation.Resource;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;


    public void addOrder(Order order){
        orderMapper.insert(order);
    }

    public List<Order> getAllOrder(String startTime,String endTime){
        QueryWrapper<Order> wrapper=new QueryWrapper();
        wrapper.between("create_time",startTime,endTime);
        return orderMapper.selectList(wrapper);
    }

    public void changeStatus(Integer status,String orderId){
        UpdateWrapper<Order>wrapper=new UpdateWrapper<Order>();
        wrapper.eq("order_id",orderId).set("status",status);
        orderMapper.update(null,wrapper);
    }

    public Order getOrderById(String orderId){
        QueryWrapper<Order> wrapper=new QueryWrapper();
        wrapper.eq("order_id",orderId);
        return orderMapper.selectOne(wrapper);
    }

    public void deleteOrder(Integer id){
         orderMapper.deleteById(id);
    }


}
