package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.DoctorMapper;
import wx.poj.Doctor;

import javax.annotation.Resource;
import javax.print.Doc;

@Service
public class DoctorService {
    @Resource
    private DoctorMapper doctorMapper;

    public void addDoctor(Doctor doctor){
        doctorMapper.insert(doctor);
    }

    public Doctor checkDoctor(String name,String password){
        QueryWrapper<Doctor>wrapper=new QueryWrapper();
        wrapper.eq("user_name",name);
        wrapper.eq("password",password);
        return doctorMapper.selectOne(wrapper);
    }


}
