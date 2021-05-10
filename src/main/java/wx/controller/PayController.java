package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wx.poj.Doctor;
import wx.poj.Order;
import wx.poj.User;
import wx.service.DoctorService;
import wx.service.OrderService;
import wx.service.UserService;
import wx.util.Result;

import javax.annotation.Resource;

@Slf4j
@CrossOrigin
@RestController
public class PayController {

        @Resource
        private OrderService orderService;
        @Resource
        private UserService userService;
        @Resource
        private DoctorService doctorService;


        @GetMapping("/pay")
        public Result pay(String orderId){
            if(orderId==null||orderId.equals("")){
                return new Result(null,"orderId为空",1);
            }
            Order order=orderService.getOrderById(orderId);
            if(order==null){
                return new Result(null,"找不到订单",1);
            }
            User user=userService.getUserById(order.getUserId());
            if(user==null){
                return new Result(null,"找不到用户",1);
            }
            Doctor doctor=doctorService.getDoctorById(order.getDoctorId());
            if(doctor==null){
                return new Result(null,"找不到医生",1);
            }
            Integer userMoney=user.getMoney();
            Integer needMoney=order.getMoney();
            log.info("userMoney:"+userMoney);
            log.info("needMoney:"+needMoney);
            if(userMoney<needMoney){
                return new Result(null,"余额不足",1);
            }
            user.setMoney(userMoney-needMoney);
            doctor.setMoney(doctor.getMoney()+needMoney);
            userService.updateMoney(user.getId(),user.getMoney());
            doctorService.updateMoney(doctor.getId(),doctor.getMoney());
            orderService.changeStatus(1);
            return new Result(null,"付款成功",0);

        }



}
