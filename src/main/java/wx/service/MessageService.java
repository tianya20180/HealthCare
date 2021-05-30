package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sun.xml.internal.bind.marshaller.MinimumEscapeHandler;
import org.springframework.stereotype.Service;
import wx.mapper.MessageMapper;
import wx.poj.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

@Service
public class MessageService {

    @Resource
    private MessageMapper messageMapper;


    public Integer addMessage(InMessage message){
        message.setMsgId(message.getFromId()+"-"+message.getToId());
        int id=messageMapper.insert(message);
        return message.getId();
    }

    public List<InMessage> getOffLineMessageByDoctorId(Integer toId,Integer fromId){
        QueryWrapper<InMessage>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",toId);
        queryWrapper.eq("from_id",fromId);
        queryWrapper.eq("is_read",0);
        queryWrapper.eq("send_type",0);
        queryWrapper.orderByDesc("create_time");
        return messageMapper.selectList(queryWrapper);
    }

    public List<InMessage>getOfflineMessageByUserId(Integer fromId,Integer toId){
        QueryWrapper<InMessage>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",toId);
        queryWrapper.eq("from_id",fromId);
        queryWrapper.eq("is_read",0);
        queryWrapper.eq("send_type",1);
        queryWrapper.orderByDesc("create_time");
        return messageMapper.selectList(queryWrapper);
    }


    public void changeRead(Integer id){
        UpdateWrapper<InMessage>updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("is_read",1);
        messageMapper.update(null,updateWrapper);
    }

    public List<InMessage>getMessage(Integer id){
        QueryWrapper<InMessage>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",id).orderByDesc("create_time");
        return messageMapper.selectList(queryWrapper);

    }

    public List<InMessage>getAllMessage(){
        QueryWrapper<InMessage>queryWrapper=new QueryWrapper<>();
        return messageMapper.selectList(queryWrapper);

    }
    public void deleteMessageById(Integer id){
        messageMapper.deleteById(id);

    }

    public void deleteMessageByMap(Integer userId,Integer doctorId){
        Map<String,Object> map1=new HashMap<>();
        map1.put("from_id",userId);
        map1.put("to_id",doctorId);
        messageMapper.deleteByMap(map1);
        Map<String,Object> map2=new HashMap<>();
        map1.put("from_id",doctorId);
        map1.put("to_id",userId);
        messageMapper.deleteByMap(map2);
    }


}
