package wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import wx.poj.Authentication;

@Mapper
public interface AuthenticationMapper extends BaseMapper<Authentication> {
}
