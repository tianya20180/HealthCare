package wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wx.poj.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
