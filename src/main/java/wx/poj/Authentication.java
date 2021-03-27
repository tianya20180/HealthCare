package wx.poj;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("authentication")
public class Authentication {
        private Integer id;
        @TableField("create_time")
        private String createTime;
        @TableField("card_path")
        private String cardPath;
        @TableField("hospital_path")
        private String hospitalPath;
        @TableField("doctor_path")
        private String doctorPath;
        private Integer status;//0 暂未批准 1 已批准 2 拒绝
        private Integer doctorId;
}
