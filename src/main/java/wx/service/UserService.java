package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wx.mapper.UserMapper;
import wx.poj.Doctor;
import wx.poj.User;
import wx.util.Result;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public void addUser(User user){
        userMapper.insert(user);
    }
    public User checkUser(String userName,String password){
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.eq("phone",userName);
        wrapper.eq("password",password);
        return userMapper.selectOne(wrapper);
    }
    public User getByPhone(String phone){
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        return userMapper.selectOne(wrapper);
    }
    public void changePassword(String phone,String password){
        UpdateWrapper<User> wrapper=new UpdateWrapper();
        wrapper.eq("phone",phone).set("password",password);
    }

    public User getUserById(Integer id){
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.eq("id",id);
        return userMapper.selectOne(wrapper);
    }

    public void changeAvatar(Integer id,String avatar){
        log.info("start change database");
        log.info("id:{},avatar:{}",id,avatar);
        UpdateWrapper<User> wrapper=new UpdateWrapper();
        wrapper.eq("id",id).set("avatar",avatar);
        userMapper.update(null,wrapper);
    }




}
