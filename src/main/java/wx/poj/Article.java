package wx.poj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("article")
public class Article
{
    private Long id;
    private String title;
    private String description;
    private String content;
    private String articletype;
    private String createdatetime;
    private String createuserid;

}

