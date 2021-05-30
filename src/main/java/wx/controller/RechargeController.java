package wx.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import wx.config.AlipayConfig;
import wx.poj.Ask;
import wx.poj.Doctor;
import wx.poj.Order;
import wx.poj.User;
import wx.service.AskService;
import wx.service.DoctorService;
import wx.service.OrderService;
import wx.service.UserService;
import wx.util.AliPayUtils;
import wx.util.OrderUtil;
import wx.util.Result;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/recharge")
public class RechargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliPayController.class);




    @Autowired
    private OrderService ordersService;
    @Autowired
    private UserService userService;


    /**
     * 对应官方例子   alipay.trade.page.pay.jsp
     *
     * @Title: AlipayController.java
     * @Description: 前往支付宝第三方网关进行支付
     * Copyright: Copyright (c) 2019
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY`
     * @Classname AlipayController
     * @Description notify_url 和 return_url 需要外网可以访问，建议natapp 内网穿透
     *
     *
     */
    @GetMapping("/goAlipay")
    public Result goAlipay(String money,Integer userId, String password,HttpServletRequest request, HttpServletResponse response) throws Exception {

        Order order=new Order();
        order.setStatus(0);//0 未支付 1支付
        order.setMoney(Integer.valueOf(money));
        order.setUserId(userId);
        String orderId= OrderUtil.getOrderIdByUUId();
        log.info(orderId);
        order.setOrderId(orderId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= sdf.format(new Date());
        order.setCreateTime(date);
        User user=userService.getUserById(userId);
        String pass=DigestUtils.md5DigestAsHex(password.getBytes());
        log.info("old:"+user.getPassword());
        log.info("new:"+pass);
        if(!pass.equals(user.getPassword())){
            return new Result(null,"密码错误",1);
        }
        ordersService.addOrder(order);
        //获得初始化的AlipayClient

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://192.144.236.155:8080/recharge/alipayReturnNotice");
        alipayRequest.setNotifyUrl("http://192.144.236.155:8080/recharge/alipayNotifyNotice");

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderId;
        //付款金额，必填
        String total_amount = order.getMoney().toString();
        //订单名称，必填
        String subject = "谷芽在线问诊平台";
        //商品描述，可空
        String body = "用户订购商品个数：" + 1;

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "10m";

        //例子去官方api找
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String result = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        return new Result(result,"ok",0);
    }


    /*
     * 功能：支付宝服务器同步通知页面  return_url.jsp  return_url必须放在公网上
     * 日期：2017-03-30
     * 说明：
     * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
     * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
     */

    /**
     * @Title: AlipayController.     对应官方例子return_url.jsp    return_url必须放在公网上  回跳页面
     * @Description: 支付宝同步通知页面
     * Copyright: Copyright (c) 2019
     * @Classname AlipayController
     * @Description TODO
     * @Date 2019/3/22 01:31
     * @Created by 爆裂无球
     */
    @GetMapping("/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response, Map map) throws Exception {

        LOGGER.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.sign_type); //调用SDK验证签名
        User user=null;
        String out_trade_no="";
        String userId="";
        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水  这里放在 异步 业务 处理
//            ordersService.updateOrderStatus(out_trade_no, trade_no, total_amount);
            //页面  展示
            Order order = ordersService.getOrderById(out_trade_no);
            user = userService.getUserById(order.getUserId());
            userId=String.valueOf(order.getUserId());
            ordersService.changeStatus(1,out_trade_no);
            userService.updateMoney(user.getId(),user.getMoney()+Float.valueOf(total_amount));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date= sdf.format(new Date());

            LOGGER.info("********************** 支付成功(支付宝同步通知) **********************");
            LOGGER.info("* 订单号: {}", out_trade_no);
            LOGGER.info("* 支付宝交易号: {}", trade_no);
            LOGGER.info("* 实付金额: {}", total_amount);
          LOGGER.info("***************************************************************");

            map.put("out_trade_no", out_trade_no);
            map.put("trade_no", trade_no);
            map.put("total_amount", total_amount);


        } else {
            LOGGER.info("支付, 验签失败...");
        }

        //前后分离形式  直接返回页面 记得加上注解@Response  http://login.calidray.com你要返回的网址，再页面初始化时候让前端调用你其他接口，返回信息
        /*String result = "<form action=\"http://localhost:8000/#/information/?to="+doctor.getId()+"&orderId="+out_trade_no+"&userId="+userId+"\"  method=\"get\" name=\"form1\">\n" +
                "</form>\n" +
                "<script>document.forms[0].submit();</script>";*/
        return "充值成功";
        //前后不分离的形式，直接返回jsp页面


    }


    /* *
     * 功能：支付宝服务器异步通知页面   对应官方例子 notify_url.jsp     notify_url必须放入公网
     * 日期：2017-03-30
     * 说明：
     * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
     * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
     *************************页面功能说明*************************  制作业务处理
     * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
     * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
     * 如果没有收到该页面返回的 success
     * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
     */
    /**
     * @Title: AlipayController.java
     * @Description: 支付宝异步 通知  制作业务处理
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     * @Description TODO
     * @Date 2019/3/22 01:45
     * @Created by 爆裂无球
     */
    @GetMapping(value = "/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        LOGGER.info("支付成功, 进入异步通知接口...");
        System.out.println("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水

                //这里不用 查  只是为了 看日志 查的方法应该卸载 同步回调 页面 中
                Order order = ordersService.getOrderById(out_trade_no);
                ordersService.changeStatus(1, out_trade_no);

                LOGGER.info("********************** 支付成功(支付宝异步通知)查询 只是为了 看日志  **********************");
                LOGGER.info("* 订单号: {0}", out_trade_no);
                LOGGER.info("* 支付宝交易号: {}", trade_no);
                LOGGER.info("* 实付金额: {}", total_amount);
                LOGGER.info("***************************************************************");

            }
            LOGGER.info("支付成功...");

        } else {//验证失败
            LOGGER.info("支付, 验签失败...");
        }

        return "success";
    }


}
