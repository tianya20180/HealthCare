package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.DiagnosisMapper;
import wx.poj.Category;
import wx.poj.Diagnosis;
import wx.poj.Information;
import java.util.*;

import javax.annotation.Resource;

@Service
public class DiagnosisService {
    @Resource
    private DiagnosisMapper diagnosisMapper;

    public void addDiagnosis(Diagnosis diagnosis){
        diagnosisMapper.insert(diagnosis);
    }

    public List<Diagnosis>getByUser(Integer userId){
        QueryWrapper<Diagnosis> diagnosisQueryWrapper=new QueryWrapper<>();
        diagnosisQueryWrapper.eq("user_id",userId);
        return diagnosisMapper.selectList(diagnosisQueryWrapper);
    }



}
