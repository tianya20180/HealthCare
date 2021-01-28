package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import wx.mapper.UserMapper;
import wx.poj.Doctor;
import wx.poj.User;

import javax.annotation.Resource;

public class UserService {
    @Resource
    private UserMapper userMapper;

    public void addUser(User user){
        userMapper.insert(user);
    }
    public User checkUser(String userName,String password){
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.eq("user_name",userName);
        wrapper.eq("password",password);
        return userMapper.selectOne(wrapper);
    }
    public User getByPhone(String phone){
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        return userMapper.selectOne(wrapper);
    }


}
