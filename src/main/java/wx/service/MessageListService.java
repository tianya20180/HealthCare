package wx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import wx.mapper.MessageListMapper;
import wx.poj.MessageList;
import java.util.List;
import javax.annotation.Resource;

public class MessageListService {

    @Resource
    private MessageListMapper messageListMapper;

    public void addMessageList(MessageList messageList){
        messageListMapper.insert(messageList);
    }

    public List<MessageList> getByDoctorId(Integer doctorId){
        QueryWrapper<MessageList>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("doctor_id",doctorId);
        return messageListMapper.selectList(queryWrapper);
    }

    public List<MessageList> getByUserId(Integer userId){
        QueryWrapper<MessageList>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return messageListMapper.selectList(queryWrapper);
    }

    public MessageList isExists(String msgId){
        QueryWrapper<MessageList>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("msg_id",msgId);
         return messageListMapper.selectOne(queryWrapper);
    }


    public  void updateMessage(MessageList messageList){
        UpdateWrapper<MessageList>updateWrapper=new UpdateWrapper<>();
        messageListMapper.update(messageList,updateWrapper);
    }










}
