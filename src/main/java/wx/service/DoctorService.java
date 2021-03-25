package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sun.java.browser.plugin2.DOM;
import org.springframework.stereotype.Service;
import wx.mapper.DoctorMapper;
import wx.poj.Doctor;
import wx.poj.User;
import java.util.*;
import javax.annotation.Resource;
import javax.print.Doc;

@Service
public class DoctorService {
    @Resource
    private DoctorMapper doctorMapper;

    public void addDoctor(Doctor doctor){
        doctorMapper.insert(doctor);
    }

    public Doctor checkDoctor(String phone,String password){
        QueryWrapper<Doctor>wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        wrapper.eq("password",password);
        return doctorMapper.selectOne(wrapper);
    }

    public Doctor getByPhone(String phone){
        QueryWrapper<Doctor> wrapper=new QueryWrapper();
        wrapper.eq("phone",phone);
        return doctorMapper.selectOne(wrapper);
    }

    public List<Doctor>getDoctorList(String userName){
        QueryWrapper<Doctor>wrapper=new QueryWrapper();
        wrapper.like("user_name",userName);
        return doctorMapper.selectList(wrapper);
    }

    public List<Doctor>getDoctorListByTime(){
        QueryWrapper<Doctor>wrapper=new QueryWrapper();
        wrapper.orderByDesc("create_time");
        return doctorMapper.selectList(wrapper);
    }

    public List<Doctor>getDoctorListByCategory(Integer category){
        QueryWrapper<Doctor>wrapper=new QueryWrapper();
        wrapper.eq("category",category);
        return doctorMapper.selectList(wrapper);
    }

    public void changePassword(String phone,String password){
        UpdateWrapper<Doctor> wrapper=new UpdateWrapper();
        wrapper.eq("phone",phone).set("password",password);
    }

    public Doctor getDoctorById(Integer id){
        QueryWrapper<Doctor> wrapper=new QueryWrapper();
        wrapper.eq("id",id);
        return doctorMapper.selectOne(wrapper);
    }
    public void changeAvatar(Integer id,String avatar){
        UpdateWrapper<Doctor> wrapper=new UpdateWrapper();
        wrapper.eq("id",id).set("avatar",avatar);
        doctorMapper.update(null,wrapper);
    }
    public void updateDoctor(Doctor doctor){
        UpdateWrapper<Doctor> wrapper=new UpdateWrapper();
        doctorMapper.update(doctor,wrapper);
    }


}
