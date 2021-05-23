package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.xmlbeans.impl.xb.xsdschema.UnionDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wx.mapper.DrugMapper;
import wx.mapper.UnionMapper;
import wx.poj.Drug;
import wx.poj.Information;
import wx.poj.Union;

import java.util.*;
import javax.annotation.Resource;

@Service
public class DrugService {

    @Resource
    private DrugMapper drugMapper;
    @Resource
    private UnionMapper unionMapper;

    public int addDrug(Drug drug){
        return drugMapper.insert(drug);
    }

    public List<Drug>getByPreId(Integer id){
        QueryWrapper<Drug>queryWrapper=new QueryWrapper<Drug>();
        QueryWrapper<Union>queryUnion=new QueryWrapper<Union>();
        queryUnion.eq("prescription_id",id);
        List<Union>unionList=unionMapper.selectList(queryUnion);
        List<Drug>drugList=new ArrayList<>();
        for(Union union:unionList){
            Drug drug=drugMapper.selectById(union.getDrugId());
            drugList.add(drug);
        }
        return drugList;
    }

    public List<Drug>search(String name){
        QueryWrapper<Drug>queryWrapper=new QueryWrapper<Drug>();
        queryWrapper.like("drug_name",name);
        return drugMapper.selectList(queryWrapper);
    }

    public List<Drug>getAllDrug(){
        QueryWrapper<Drug>queryWrapper=new QueryWrapper<Drug>();
        return drugMapper.selectList(queryWrapper);
    }


}
