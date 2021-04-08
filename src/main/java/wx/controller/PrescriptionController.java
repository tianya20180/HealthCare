package wx.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wx.poj.Drug;
import wx.poj.Prescription;
import wx.service.DrugService;
import wx.service.PrescriptionService;
import wx.util.Result;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

@Slf4j
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/prescription")
public class PrescriptionController {


       @Resource
       private DrugService drugService;

       @Resource
       private PrescriptionService prescriptionService;


        @PostMapping("/addDrug")
        public Result addDrug(@RequestBody Drug drug){
            if(drug==null)
                return new Result(null,"Drug为空",1);
            drugService.addDrug(drug);
            return new Result(null,"新增成功",0);
        }

        @GetMapping("/addPrescription")
        public Result addPrescription(Integer doctorId,Integer userId){
            if(doctorId==null||userId==null)
                return new Result(null,"prescription为null",1);
            Prescription prescription=new Prescription();
            prescription.setDoctorId(doctorId);
            prescription.setUserId(userId);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date= sdf.format(new Date());
            prescription.setCreateTime(date);
            log.info(prescription.toString());
            prescriptionService.addPrescription(prescription);
            return new Result(null,"新增成功",0);
        }

        @GetMapping("/getPrescription")
        public Result getPrescription(Integer userId,Integer doctorId){
            if(userId==null||doctorId==null)
                return new Result(null,"userId为null或者doctorId为null",1);
            Prescription prescription=prescriptionService.getPrescriptionByUserIdAndDoctorId(userId,doctorId);
            List<Drug>drugList=drugService.getByPreId(prescription.getId());
            prescription.setDrugList(drugList);
            return new Result(prescription,"获取成功",0);
        }

}
