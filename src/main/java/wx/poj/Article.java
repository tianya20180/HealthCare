package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("article")
public class Article
{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("title")
    private String title;
    @TableField("description")
    private String description;
    @TableField("content")
    private String content;
    @TableField("article_type")
    private String articletype;
    @TableField("create_time")
    private String createdatetime;
    @TableField("doctor_id")
    private String createuserid;
    @TableField("like_count")
    private Integer likeCount;
    @TableField("view_count")
    private Integer viewCount;

}

