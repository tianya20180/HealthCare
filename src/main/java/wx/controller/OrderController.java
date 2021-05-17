package wx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.Ask;
import wx.poj.Order;
import wx.service.AskService;
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


    @GetMapping("/addOrder")
    public Result addOrder(Integer userId,Integer doctorId,Integer money){
        Order order=new Order();
        log.info("userId:"+userId+"doctorId:"+doctorId+"money:"+money);
        order.setStatus(0);//0 未支付 1支付
        order.setDoctorId(doctorId);
        order.setMoney(money);
        order.setUserId(userId);
        String orderId= OrderUtil.getOrderIdByUUId();
        log.info(orderId);
        order.setOrderId(orderId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        order.setCreateTime(date);

        orderService.addOrder(order);
        return new Result(order,"添加成功",0);
    }

    @GetMapping("/changeStatus")
    public Result changeStatus(Integer status,String orderId){
        if(status==null){
            return new Result(null,"status为空",1);
        }
        orderService.changeStatus(status,orderId);
        return new Result(null,"更新状态成功",0);
    }





}
