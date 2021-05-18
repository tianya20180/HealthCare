package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.AdminMapper;
import wx.mapper.UserMapper;
import wx.poj.Admin;
import wx.poj.User;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;


    public List<Admin>getAllAdmin(){
        QueryWrapper<Admin> wrapper=new QueryWrapper();
        return adminMapper.selectList(wrapper);
    }

    public void addMapper(Admin admin){
        adminMapper.insert(admin);
    }

    public Admin checkMapper(String userName,String password){
        QueryWrapper<Admin> wrapper=new QueryWrapper();
        wrapper.eq("user_name",userName);
        wrapper.eq("password",password);
        return adminMapper.selectOne(wrapper);
    }

    public int getAdminCount(){
        QueryWrapper<Admin> wrapper=new QueryWrapper();
        return adminMapper.selectCount(wrapper);
    }
    public int getAdminCountBetweenTime(String startTime,String endTime){
        QueryWrapper<Admin> wrapper=new QueryWrapper();
        wrapper.between("create_time",startTime,endTime);
        return adminMapper.selectCount(wrapper);
    }

    public void delete(Integer id){
        adminMapper.deleteById(id);
    }



}
