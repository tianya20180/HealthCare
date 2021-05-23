package wx.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.PrescriptionMapper;
import wx.poj.Prescription;
import java.util.*;
import javax.annotation.Resource;

@Service
public class PrescriptionService {

    @Resource
    private PrescriptionMapper prescriptionMapper;

    public int addPrescription(Prescription prescription) {
       return prescriptionMapper.insert(prescription);
    }

    public List<Prescription> getPrescriptionByUserId(Integer userId) {
        QueryWrapper<Prescription> queryWrapper = new QueryWrapper<Prescription>();
        queryWrapper.eq("user_id", userId);
        return prescriptionMapper.selectList(queryWrapper);
    }
    public Prescription getPrescriptionByOrderId(String orderId) {
        QueryWrapper<Prescription> queryWrapper = new QueryWrapper<Prescription>();
        queryWrapper.eq("order_id", orderId);
        return prescriptionMapper.selectOne(queryWrapper);
    }


    public List<Prescription> getPrescriptionByUserId(String userId) {
        QueryWrapper<Prescription> queryWrapper = new QueryWrapper<Prescription>();
        queryWrapper.eq("user_id", userId);
        return prescriptionMapper.selectList(queryWrapper);
    }
}