package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.DoctorMapper;
import wx.poj.Doctor;
import wx.poj.User;

import java.util.*;
import javax.annotation.Resource;

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
        wrapper.eq("status",1);
        wrapper.orderByDesc("create_time");
        return doctorMapper.selectList(wrapper);
    }

    public List<Doctor>getDoctorListByCategory(Integer category){
        QueryWrapper<Doctor>wrapper=new QueryWrapper();
        wrapper.eq("category",category);
        wrapper.eq("status",1);
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
    public void updateDoctor(Integer id,Doctor doctor){
        UpdateWrapper<Doctor> wrapper=new UpdateWrapper();
        wrapper.eq("id",id);
        doctorMapper.update(doctor,wrapper);
    }
    public List<Doctor>getAllDoctor(){
        QueryWrapper<Doctor> wrapper=new QueryWrapper();
        return doctorMapper.selectList(wrapper);
    }
    public void changeDoctorStatus(Integer id,Integer status){
        UpdateWrapper<Doctor> wrapper=new UpdateWrapper();
        wrapper.eq("id",id).set("status",status);
        doctorMapper.update(null,wrapper);
    }
    public void deleteDoctor(Integer id){
        doctorMapper.deleteById(id);
    }

}
