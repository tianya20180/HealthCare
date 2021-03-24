package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.mapper.DrugMapper;
import wx.poj.Drug;
import java.util.*;
import javax.annotation.Resource;

@Service
public class DrugService {

    @Resource
    private DrugMapper drugMapper;

    public void addDrug(Drug drug){
            drugMapper.insert(drug);
    }

    public List<Drug>getByPreId(Integer id){
        QueryWrapper<Drug>queryWrapper=new QueryWrapper<Drug>();
        queryWrapper.eq("prescription_id",id);
        return drugMapper.selectList(queryWrapper);
    }


}
