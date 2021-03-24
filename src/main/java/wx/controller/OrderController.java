package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.Order;
import wx.service.OrderService;
import wx.util.OrderUtil;
import wx.util.Result;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/addOrder")
    public Result addOrder(Integer userId,Integer doctorId,Integer money){
        Order order=new Order();
        order.setStatus(0);//0 未支付 1支付
        order.setDoctorId(doctorId);
        order.setMoney(money);
        order.setUserId(userId);
        String orderId= OrderUtil.getOrderIdByUUId();
        order.setOrderId(orderId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=sdf.format(new Date()).toString();
        orderService.addOrder(order);
        return new Result(null,"添加成功",0);
    }

    @GetMapping("/changeStatus")
    public Result changeStatus(Integer status){
        if(status==null){
            return new Result(null,"status为空",1);
        }
        orderService.changeStatus(status);
        return new Result(null,"更新状态成功",0);
    }





}
