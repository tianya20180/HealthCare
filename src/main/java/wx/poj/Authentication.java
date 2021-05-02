package wx.poj;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("authentication")
public class Authentication {
        @TableId(value = "id",type = IdType.AUTO)
        private Integer id;
        @TableField("create_time")
        private String createTime;
        @TableField("card_photo")
        private String cardPhoto;
        @TableField("doctor_photo")
        private String doctorPhoto;
        private Integer status;//0 暂未批准 1 已批准 2 拒绝
        @TableField("doctor_id")
        private Integer doctorId;
        private Integer category;
        @TableField("work_years")
        private Integer workYears;
        private String introduce;
        private Integer money;
        @TableField(exist = false)
        private String doctorName;
        @TableField(exist = false)
        private String categoryName;
}
