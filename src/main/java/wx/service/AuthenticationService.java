package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import wx.mapper.AuthenticationMapper;
import wx.poj.Authentication;
import java.util.*;
import javax.annotation.Resource;

@Service
public class AuthenticationService {

    @Resource
    private AuthenticationMapper authenticationMapper;


    public void addAuthentication(Authentication authentication){
        authenticationMapper.insert(authentication);
    }


    public void setStatus(Integer id,Integer status){
        UpdateWrapper<Authentication> wrapper=new UpdateWrapper();
        wrapper.eq("id",id).set("status",status);
        authenticationMapper.update(null,wrapper);
    }


    public Page getAllAuthentication(Page page){
        return  authenticationMapper.selectPage(page,null);
    }

}
