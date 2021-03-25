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
}
