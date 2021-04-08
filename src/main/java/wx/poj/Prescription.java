package wx.poj;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.util.*;

/*
* 处方单实体类
* @Author wangxi
* */

@Data
@ToString
@TableName("prescription")
public class Prescription {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_time")
    private String createTime;
    private List<Drug>drugList;
}
