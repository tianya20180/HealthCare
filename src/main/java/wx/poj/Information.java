package wx.poj;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraintvalidation.SupportedValidationTarget;

@Data
@TableName("information")
public class Information {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("order_id")
    private String orderId;
    private Integer height;
    private Integer weight;
    private String desc;
    @TableField("face_photo")
    private String facePhoto;
    @TableField("tongue_photo")
    private String tonguePhoto;
    private Integer time;
}
