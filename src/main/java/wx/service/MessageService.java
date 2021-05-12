package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sun.xml.internal.bind.marshaller.MinimumEscapeHandler;
import org.springframework.stereotype.Service;
import wx.mapper.MessageMapper;
import wx.poj.InMessage;
import wx.poj.Information;
import wx.poj.Message;
import java.util.List;
import javax.annotation.Resource;

@Service
public class MessageService {

    @Resource
    private MessageMapper messageMapper;


    public Integer addMessage(InMessage message){
        int id=messageMapper.insert(message);
        return id;
    }

    public List<InMessage> getOffLineMessageByDoctorId(Integer id){
        QueryWrapper<InMessage>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",id);
        queryWrapper.eq("is_read",false);
        queryWrapper.eq("send_type",0);
        queryWrapper.orderByDesc("create_time");
        return messageMapper.selectList(queryWrapper);
    }

    public List<InMessage>getOfflineMessageByUserId(Integer id){
        QueryWrapper<InMessage>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",id);
        queryWrapper.eq("is_read",false);
        queryWrapper.eq("send_type",1);
        queryWrapper.orderByDesc("create_time");
        return messageMapper.selectList(queryWrapper);
    }


    public void changeRead(Integer toId){
        UpdateWrapper<InMessage>updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("is_read",true);
        updateWrapper.eq("to_id",toId);
        messageMapper.update(null,updateWrapper);
    }

    public List<InMessage>getMessage(Integer id){
        QueryWrapper<InMessage>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",id).orderByDesc("create_time");
        return messageMapper.selectList(queryWrapper);

    }

}
