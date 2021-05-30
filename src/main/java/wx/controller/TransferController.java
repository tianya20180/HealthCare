package wx.controller;
import java.util.*;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipayUtils;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.config.AlipayConfig;
import wx.poj.Doctor;
import wx.poj.TransferParams;
import wx.poj.User;
import wx.service.DoctorService;
import wx.service.UserService;
import wx.util.AliPayUtils;
import wx.util.OrderUtil;
import wx.util.Result;

import javax.annotation.Resource;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/transfer")
public class TransferController {

    @Resource
    private UserService userService;
    @Resource
    private DoctorService doctorService;

    @RequestMapping("/money")
    public Result transfer(String money,Integer id,Integer identity) throws Exception {
        TransferParams transferParams=new TransferParams();
        transferParams.setAmount(money);
        transferParams.setAppId(Long.valueOf(AlipayConfig.APP_ID));
        transferParams.setOutBizNo(OrderUtil.getOrderIdByUUId());
        transferParams.setCreatedBy(1l);
        transferParams.setRemark("转账");
        transferParams.setPayerShowName("谷芽在线问诊平台");
        transferParams.setPayeeType("ALIPAY_LOGON_ID");
        AlipayFundTransUniTransferResponse res;
        if(identity==0){
            User user=userService.getUserById(id);
            log.info(user.toString());
            log.info(money);
            if(user.getMoney()<Float.valueOf(money))
                return new Result(null,"提现失败，余额不足",1);
            transferParams.setPayeeAccount("xnskoo8064@sandbox.com");
            transferParams.setPayerShowName(user.getUserName());
            transferParams.setPayeeRealName(user.getUserName());
            res=AliPayUtils.doTransferNew(transferParams);

            userService.updateMoney(user.getId(),user.getMoney()-Integer.valueOf(money));

        }else{
            Doctor doctor=doctorService.getDoctorById(id);
            if(doctor.getMoney()<Float.valueOf(money))
                return new Result(null,"提现失败，余额不足",1);
            transferParams.setPayeeAccount("xnskoo8064@sandbox.com");
            transferParams.setPayerShowName(doctor.getUserName());
            transferParams.setPayeeRealName(doctor.getUserName());
            res=AliPayUtils.doTransferNew(transferParams);

            doctorService.updateMoney(doctor.getId(),doctor.getMoney()-Integer.valueOf(money));
        }
        String temp=res.getCode()+res.getMsg();
        return new Result(res,"提现成功",0);
    }




}
