package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import wx.mapper.MessageMapper;
import wx.poj.Message;
import java.util.List;
import javax.annotation.Resource;

@Service
public class MessageService {

    @Resource
    private MessageMapper messageMapper;


    public void addMessage(Message message){
        messageMapper.insert(message);
    }

    public List<Message> getOffLineMessage(String toId){
        QueryWrapper<Message>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",toId);
        queryWrapper.eq("is_read",false);
        return messageMapper.selectList(queryWrapper);
    }


    public void changeRead(String toId){
        UpdateWrapper<Message>updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("is_read",true);
        updateWrapper.eq("to_id",toId);
        messageMapper.update(null,updateWrapper);
    }

    public List<Message>getMessage(String id){
        QueryWrapper<Message>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("to_id",id);
        return messageMapper.selectList(queryWrapper);

    }

}
